package com.wr.common.payment.util;

import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.constants.WechatConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.wr.common.payment.pay.util.WechatPayUtils.getPrivateKey;

/**
 * @Author: RainCity
 * @Date: 2021-11-03 16:00:17
 * @Desc: 微信支付签名相关
 */
public class SignUtil {
    private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

    private SignUtil() {
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        Random random = new SecureRandom();
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = PayConstants.SYMBOLS.charAt(random.nextInt(PayConstants.SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String sign(byte[] message, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance(WechatConstants.SHA256_WITH_RSA);
        sign.initSign(privateKey);
        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    public static Boolean verifiedSign(HttpServletRequest request, String body, String mchId,
                                       String serialNumber, String privateKeyUrl, String apiKey) {
        log.info("商户证书序列号：{}", serialNumber);
        //平台证书序列号
        String serialNo = request.getHeader(PayConstants.WECHAT_PAY_SERIAL);
        log.info("平台证书序列号：{}", serialNo);
        //应答时间戳
        String timestamp = request.getHeader(PayConstants.WECHAT_PAY_TIMESTAMP);
        log.info("应答时间戳：{}", timestamp);
        //应答随机串
        String nonceStr = request.getHeader(PayConstants.WECHAT_PAY_NONCE);
        log.info("应答随机串：{}", nonceStr);
        //应答签名
        String wechatSign = request.getHeader(PayConstants.WECHAT_PAY_SIGNATURE);
        log.info("应答签名：{}", wechatSign);
        //组装签名字符串
        String signStr = Stream.of(timestamp, nonceStr, body)
                .collect(Collectors.joining("\n", "", "\n"));
        log.info("组装签名字符串：{}", signStr);
        try {
            //String nonceStr = SignUtil.generateNonceStr();
            //long timestamp = System.currentTimeMillis() / 1000;
            String message = Stream.of("GET", WechatConstants.CERT_ABSOLUTE_URL, String.valueOf(timestamp), nonceStr, "")
                    .collect(Collectors.joining("\n", "", "\n"));
            log.info("获取平台证书请求签名串：{}", message);
            //读取商户私钥
            PrivateKey privateKey = getPrivateKey(privateKeyUrl);
            //String sign = SignUtil.sign(message.getBytes(StandardCharsets.UTF_8), privateKey);
            // 签名
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(message.toString().getBytes("utf-8"));
            String sign = Base64.getEncoder().encodeToString(signature.sign());
            log.info("获取平台证书签名值：{}", sign);
            String token = WechatConstants.SCHEMA + " mchid=\"" + mchId + "\","
                    + "nonce_str=\"" + nonceStr + "\","
                    + "timestamp=\"" + timestamp + "\","
                    + "serial_no=\"" + serialNumber + "\","
                    + "signature=\"" + sign + "\"";
            log.info("获取平台证书token：{}", token);
            //根据序列号获取平台证书
            X509Certificate certificate = CertificateUtil.getWechatCertificate(token, serialNo, apiKey);
            //获取失败 验证失败
            if (certificate == null) {
                return false;
            }
            //返回验签结果
            return verify(WechatConstants.SHA256_WITH_RSA, certificate, signStr, wechatSign);
        } catch (URISyntaxException | CertificateException | IOException e) {
            log.error("证书获取失败====>");
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            log.error("{}算法不匹配====>", WechatConstants.SHA256_WITH_RSA);
            e.printStackTrace();
            return false;
        } catch (SignatureException | InvalidKeyException e) {
            log.error("签名错误====>");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据地址获取商户私钥
     *
     * @param keyUrl
     * @return
     * @throws FileNotFoundException
     */
    public static PrivateKey getPrivateKey(String keyUrl) throws IOException {
        //读取商户私钥
        URL url = new URL(keyUrl);
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        return PemUtil.loadPrivateKey(in);
    }

    /**
     * 签名
     *
     * @param algorithm  签名算法
     * @param privateKey 私钥
     * @param data       带签数据
     * @return
     */
    public static String sign(String algorithm, PrivateKey privateKey, String data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * 验签
     *
     * @param algorithm   签名算法
     * @param certificate 证书
     * @param data        待验数据
     * @return
     */
    public static Boolean verify(String algorithm, X509Certificate certificate, String signStr, String data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(certificate);
        signature.update(signStr.getBytes());
        //返回验签结果
        return signature.verify(Base64.getDecoder().decode(data));
    }

    public static String sha1Sign(String str) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
