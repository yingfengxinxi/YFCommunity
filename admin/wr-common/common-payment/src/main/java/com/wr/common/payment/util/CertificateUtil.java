package com.wr.common.payment.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.WechatConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @Author: RainCity
 * @Date: 2021-11-03 17:28:28
 * @Desc: 证书相关工具类
 */
public class CertificateUtil {
    private static final Logger log = LoggerFactory.getLogger(CertificateUtil.class);

    private CertificateUtil() {
    }

    /**
     * 获取证书
     * @param token Authorization
     * @param apiKey api v3key
     * @return 证书
     * @throws URISyntaxException
     * @throws IOException
     * @throws CertificateException
     */
    public static X509Certificate getWechatCertificate(String token, String serialNo, String apiKey)
            throws URISyntaxException, IOException, CertificateException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(WechatConstants.CERTIFICATES_URL);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Authorization", token);
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("User-Agent", "https://zh.wikipedia.org/wiki/User_agent");

        CloseableHttpResponse response = httpclient.execute(httpGet);

        String bodyAsString = EntityUtils.toString(response.getEntity());
        log.info("证书串：{}",bodyAsString);
        JSONObject jsonObject = JsonUtil.parseObject(bodyAsString);
        if(null == jsonObject){
            return null;
        }
        JSONArray data = jsonObject.getJSONArray("data");

        JSONObject encrypt = null;
        for (int i = 0; i < data.size(); i++) {
            String serialNumber = data.getJSONObject(i).getString("serial_no");
            if(null != serialNumber && serialNumber.equals(serialNo)){
                encrypt = data.getJSONObject(i).getJSONObject("encrypt_certificate");
                break;
            }
        }
        if(null == encrypt){
            return null;
        }
        String algorithm = encrypt.getString("algorithm");
        String nonce = encrypt.getString("nonce");
        String associatedData = encrypt.getString("associated_data");
        String ciphertext = encrypt.getString("ciphertext");
        String publicKey = decryptResBody(apiKey,associatedData,nonce,ciphertext);
        if(StringUtils.isEmpty(publicKey)){
            return null;
        }
        CertificateFactory cf = CertificateFactory.getInstance("X509");
        //获取证书
        ByteArrayInputStream inputStream = new ByteArrayInputStream(publicKey.getBytes(StandardCharsets.UTF_8));
        return  (X509Certificate) cf.generateCertificate(inputStream);
    }

    /**
     * 解析数据
     * @param wechatV3Key 微信Api V3key
     * @param associatedData 相关数据
     * @param nonce 随机字符串
     * @param ciphertext 密文
     * @return
     */
    public static String decryptResBody(String wechatV3Key, String associatedData, String nonce, String ciphertext){
        AesUtil aesUtil = new AesUtil(wechatV3Key.getBytes(StandardCharsets.UTF_8));
        try {
            return aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
        } catch (GeneralSecurityException e) {
            log.error("数据解析失败");
            e.printStackTrace();
            return null;
        }
    }
}
