package com.wr.common.customize.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: RainCity
 * @Date: 2022-09-02 15:08:15
 * @Desc: RSA加密解密
 */
public class RsaUtil extends BaseUtils{

    private static final String ALGORITHM = "RSA";

    private static final Integer MAX_LENGTH = 117;

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLvJLXX9HswS9Qzs/Mqo3TeCPg\n" +
            "aHdM/dPjFzOidaZD/87dnHp1reH3s0uTfzm6avlc6nKbZz3gmzgC7kvVGnu5vSSl\n" +
            "Ic4+gPe70reD3WfvIHCH3nVB+p1HoUTVli4QwlMAIAeRoqKYgnwt2/KokgE+fghF\n" +
            "xJ1HpBe9USS4664tfQIDAQAB";

    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMu8ktdf0ezBL1DO\n" +
            "z8yqjdN4I+Bod0z90+MXM6J1pkP/zt2cenWt4fezS5N/Obpq+VzqcptnPeCbOALu\n" +
            "S9Uae7m9JKUhzj6A97vSt4PdZ+8gcIfedUH6nUehRNWWLhDCUwAgB5GiopiCfC3b\n" +
            "8qiSAT5+CEXEnUekF71RJLjrri19AgMBAAECgYEAmP4szus3uzB/FZGYXjsdcdXg\n" +
            "Kx6HqBtcSEYeEjuC9cFdWPGuzDwdWmMbt9FsSl8Wjv3HvodS4h3te9XywD649TMD\n" +
            "3hH6iVoxI347rM2G9K0eSi1sR2luBT3lyEdep5WNagbShLidx3Af0W479CM4YyDS\n" +
            "FyvQRTXm8Gs9scletoECQQD9fpIvN+zVdogqSFiSgXQldBPjayPiaYquOaftrrn6\n" +
            "ZkVi0pZV3G1xNUAP9eHOyuKXvd2VnpogdrIOBKL9G0EtAkEAzcAZGM6Aa2/m/1w1\n" +
            "XgtJfYkCMtTThgEJwlmJc/xidosC2u/82sJRkk5BW5njiFtU1EvtCY4tz029u+uY\n" +
            "LcMvkQJBAIFVO4Zl1SrP8AcKpE4TSEv3nlIQjt1slbnwZrJIzFBqL4qNAPDLpvb0\n" +
            "gTvjapMURmm/EDfPFLntdUjbqsDST/0CQC/vsqkx63+cicAzFof7rzqdQjLfnQya\n" +
            "j1CFld29Dp+50Yd65Hl/Tov8yYNy/i0VwlTX/7j7NDcZULxIrqLy19ECQBvezpyR\n" +
            "JU1LXji7J58LP7qJpouZevyp5ILjOds3dSVzHEhCdHdHUZndbHe4cTJSOW7J2vEM\n" +
            "Li/RnYVtlCesOZM=";

    /**
     * RSA公钥加密
     * @param plaintext 明文
     * @return java.lang.String
     */
    public static String encrypt(String plaintext) {
        if(StringUtils.isEmpty(plaintext)){
            log.warn("加密-->原数据为空！！！");
            return null;
        }
        if(plaintext.length() > MAX_LENGTH){
            log.error("加密失败：Data must not be longer than 117 bytes");
            return null;
        }
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            return Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("加密失败：{}",e.getMessage());
            return null;
        }
    }

    /**
     * RSA私钥解密
     * @param ciphertext 密文
     * @return java.lang.String
     */
    public static String decrypt(String ciphertext) {
        if(StringUtils.isEmpty(ciphertext)){
            log.warn("解密-->原数据为空！！！");
            return null;
        }
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(ciphertext.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);

            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            log.error("解密失败：{}",e.getMessage());
            return null;
        }

    }
}
