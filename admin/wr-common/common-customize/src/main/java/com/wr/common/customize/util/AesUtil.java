package com.wr.common.customize.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @Author: RainCity
 * @Date: 2022-09-02 16:03:46
 * @Desc: AES/CBC/PKCS7Padding 加密解密
 */
public class AesUtil extends BaseUtils{
    /** 加密算法 */
    public static final String ALGORITHM = "AES";

    /** 数据填充模式 */
    public static final String AES_CBC_PKCS_7_PADDING = "AES/CBC/PKCS7Padding";
    /** 密钥 key 16位 */
    public static final String SECRET_KEY = "1A73FE05BF6800D3";

    /**
     * 加密
     * @param plaintext 需要加密的参数
     * @return java.lang.String
     */
    public static String encrypt(String plaintext) {
        if(StringUtils.isEmpty(plaintext)){
            log.warn("加密-->原数据为空！！！");
            return null;
        }
        try {
            //让java支持PKCS7Padding
            Security.addProvider(new BouncyCastleProvider());
            // 获取Cipher
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_7_PADDING);
            // 生成密钥
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            // 指定模式(加密)和密钥
            // 创建初始化向量
            IvParameterSpec iv = new IvParameterSpec(SECRET_KEY.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            // 加密
            byte[] bytes = cipher.doFinal(plaintext.getBytes());

            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("加密失败：{}",e.getMessage());
            return null;
        }

    }

    /**
     * 解密
     * @param ciphertext 需要解密的参数
     * @return java.lang.String
     */
    public static String decrypt(String ciphertext) {
        if(StringUtils.isEmpty(ciphertext)){
            log.warn("解密-->原数据为空！！！");
            return null;
        }
        try {
            //让java支持PKCS7Padding
            Security.addProvider(new BouncyCastleProvider());
            // 获取Cipher
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_7_PADDING);
            // 生成密钥
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            // 指定模式(解密)和密钥
            // 创建初始化向量
            IvParameterSpec iv = new IvParameterSpec(SECRET_KEY.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            // 解密
            byte[] bytes = cipher.doFinal(Base64.decodeBase64(ciphertext));

            return new String(bytes);
        } catch (Exception e) {
            log.error("解密失败：{}",e.getMessage());
            return null;
        }
    }
}
