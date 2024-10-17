package com.wr.common.customize.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: RainCity
 * @Date: 2020-09-04 13:48:03
 * @Desc: 华为云短信发送
 */
public class SendMsgUtils extends BaseUtils {
    /**
     * 无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
     */
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    /**
     * 无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
     */
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    /**
     * APP接入地址+接口访问URI
     */
    private static final String URL = "https://rtcsms.cn-north-1.myhuaweicloud.com:10743/sms/batchSendSms/v1";
    /**
     * APP_Key
     */
    private static final String APP_KEY = "J1F9hB3e5lq32Z8isuciDCm3mRmx";
    /**
     * APP_Secret
     */
    private static final String APP_SECRET = "p7Gd01JYWu40fl2j970FQle7j30w";
    /**
     * 国内短信签名通道号或国际/港澳台短信通道号
     */
    private static final String SENDER = "8821100915017";
    /**
     * 模板ID
     */
    private static final String TEMPLATE_ID = "e8d8835e651a47168a8ee7fd162f8d6b";
    /**
     *  签名名称
     * 条件必填,国内短信关注,当templateId指定的模板类型为通用模板时生效且必填,必须是已审核通过的,与模板类型一致的签名名称
     * 国际/港澳台短信不用关注该参数
     */
    private static final String SIGNATURE = "水利生态";
    /**
     * 成功状态码
     */
    private static final String SUCCESS_CODE = "000000";


    public static String sendSmsCode(String receiver,String code) throws Exception{
        //模板变量
        String templateParas = "[\""+code+"\"]";

        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(receiver, TEMPLATE_ID, templateParas, SIGNATURE);
        if (StringUtils.isEmpty(body)) {
            log.error("短信发送失败-->body is null");
            return null;
        }
        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader();
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            log.error("短信发送失败-->wsse header is null.");
            return null;
        }

        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                        (x509CertChain, authType) -> true).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        //请求方法POST
        HttpResponse response = client.execute(RequestBuilder.create("POST")
                .setUri(URL)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setEntity(new StringEntity(body)).build());
        String result = EntityUtils.toString(response.getEntity());
        //打印响应消息实体
        log.info(result);
        JSONObject jsonObject = JSON.parseObject(result);
        String status = jsonObject.getString("code");
        log.info("code:{}",jsonObject.getString("code"));
        if(SUCCESS_CODE.equals(status)){
            log.info("短信发送成功-->验证码:{}",code);
            return code;
        }
        return null;
    }

    /**
     * 构造请求Body体
     * @param receiver 接收者
     * @param templateId 模板id
     * @param templateParas  模板变量
     * @param signature | 签名名称,使用国内短信通用模板时填写
     * @return 请求Body体
     */
    private static String buildRequestBody(String receiver, String templateId, String templateParas, String signature) {
        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", SENDER));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        if (null != templateParas && !templateParas.isEmpty()) {
            keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        }

        //keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));

        if (null != signature && !signature.isEmpty()) {
            keyValues.add(new BasicNameValuePair("signature", signature));
        }

        return URLEncodedUtils.format(keyValues, StandardCharsets.UTF_8);
    }

    /**
     * 构造X-WSSE参数值
     * @return 请求Headers中的X-WSSE参数值
     */
    private static String buildWsseHeader() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //Created
        String time = sdf.format(new Date());
        //Nonce
        String nonce = UUID.randomUUID().toString().replace("-", "");

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + APP_SECRET);
        String hexDigest = Hex.encodeHexString(passwordDigest);

        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
        //PasswordDigest
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes());

        return String.format(WSSE_HEADER_FORMAT, APP_KEY, passwordDigestBase64Str, nonce, time);
    }


    /**
     * 生成随机的6为验证码
     * @return
     */
    public static String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }

}
