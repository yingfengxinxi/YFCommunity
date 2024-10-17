package com.wr.common.payment.pay.util;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wr.common.payment.domain.WechatOpen;
import lombok.SneakyThrows;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述：
 *
 * @author JSYue
 * @date 2023/6/13 22:13
 */
public class WXPaySignatureCertificateUtil {
    /**
     * 证书验证
     * 自动更新的签名验证器
     */
    public static CloseableHttpClient checkSign(String fileName, String mchId, String appKey, String mchSerialNo) throws IOException {
        //验签
        CloseableHttpClient httpClient = null;
        PrivateKey merchantPrivateKey = getPrivateKey(fileName);
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(WXPaySignatureCertificateUtil.getVerifier(fileName, mchId, appKey, mchSerialNo)))
                .build();

        return httpClient;
    }


    /**
     * 保存微信平台证书
     */
    private static final ConcurrentHashMap<String, AutoUpdateCertificatesVerifier> verifierMap = new ConcurrentHashMap<>();

    /**
     * 功能描述:获取平台证书，自动更新
     * 注意：这个方法内置了平台证书的获取和返回值解密
     */
    public static AutoUpdateCertificatesVerifier getVerifier(String fileName, String mchId, String appKey, String mchSerialNo) {
        AutoUpdateCertificatesVerifier verifier = null;
        if (verifierMap.isEmpty() || !verifierMap.containsKey(mchSerialNo)) {
            verifierMap.clear();
            try {
                //传入证书
                PrivateKey privateKey = getPrivateKey(fileName);
                //刷新
                PrivateKeySigner signer = new PrivateKeySigner(mchSerialNo, privateKey);
                WechatPay2Credentials credentials = new WechatPay2Credentials(mchId, signer);
                verifier = new AutoUpdateCertificatesVerifier(credentials
                        , appKey.getBytes("utf-8"));
                verifierMap.put(verifier.getValidCertificate().getSerialNumber() + "", verifier);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            verifier = verifierMap.get(mchSerialNo);
        }
        return verifier;
    }


    /**
     * 生成带签名支付信息
     *
     * @param timestamp 时间戳
     * @param nonceStr  随机数
     * @param prepayId  预付单
     * @return 支付信息
     * @throws Exception
     */
    public static String appPaySign(String fileName, String appId, String timestamp, String nonceStr, String prepayId) throws Exception {
        //上传私钥
        PrivateKey privateKey = getPrivateKey(fileName);
        String signatureStr = Stream.of(appId, timestamp, nonceStr, prepayId)
                .collect(Collectors.joining("\n", "", "\n"));
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 生成带签名支付信息
     *
     * @param timestamp 时间戳
     * @param nonceStr  随机数
     * @param prepayId  预付单
     * @return 支付信息
     * @throws Exception
     */
    public static String jsApiPaySign(String fileName, String timestamp, String nonceStr, String prepayId) throws Exception {
        //上传私钥
        PrivateKey privateKey = getPrivateKey(fileName);
        String signatureStr = Stream.of("", timestamp, nonceStr, "prepay_id=" + prepayId)
                .collect(Collectors.joining("\n", "", "\n"));
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sign.sign());
    }


    /**
     * 获取私钥。
     * 证书路径 本地使用如： D:\\微信平台证书工具\\7.9\\apiclient_key.pem
     * 证书路径 线上使用如： /usr/apiclient_key.pem
     * String filename 私钥文件路径  (required)
     *
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String fileName) throws IOException {


        String content = new String(Files.readAllBytes(Paths.get(fileName)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }


    /**
     * 功能描述: 验证签名
     * 注意：使用微信支付平台公钥验签
     * Wechatpay-Signature 微信返签名
     * Wechatpay-Serial 微信平台证书序列号
     *
     * @return java.lang.String
     * @author 影子
     */
    @SneakyThrows
    public static boolean verifySign(String fileName, String mchId, String appKey, HttpServletRequest request, String body) {
        boolean verify = false;
        try {
            String wechatPaySignature = request.getHeader("Wechatpay-Signature");
            String wechatPayTimestamp = request.getHeader("Wechatpay-Timestamp");
            String wechatPayNonce = request.getHeader("Wechatpay-Nonce");
            String wechatPaySerial = request.getHeader("Wechatpay-Serial");
            //组装签名串
            String signStr = Stream.of(wechatPayTimestamp, wechatPayNonce, body)
                    .collect(Collectors.joining("\n", "", "\n"));
            //获取平台证书
            AutoUpdateCertificatesVerifier verifier = getVerifier(fileName, mchId, appKey, wechatPaySerial);
            //获取失败 验证失败
            if (verifier != null) {
                Signature signature = Signature.getInstance("SHA256withRSA");
                signature.initVerify(verifier.getValidCertificate());
                //放入签名串
                signature.update(signStr.getBytes(StandardCharsets.UTF_8));
                verify = signature.verify(Base64.getDecoder().decode(wechatPaySignature.getBytes()));
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return verify;
    }
}
