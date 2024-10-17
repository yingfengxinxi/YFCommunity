package com.wr.common.customize.util;

import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.exception.base.BaseException;
import com.wr.common.core.utils.reflect.ReflectUtils;
import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * @Author: RainCity
 * @Date: 2022-08-29 09:09:56
 * @Desc: 对象解密工具
 * 通过反射，对参数对象中包含指定注解的字段进行解密。
 */
public class DecryptUtil extends BaseUtils{

    private static final String LANG_STRING = "String";

    private static final String JSON_OBJECT = "JSONObject";

    /**
     * base64解密
     * @param ciphertext 密文
     * @param clazz 解密成的对象
     * @return T
     */
    public static <T> T decryptBase64(String ciphertext, Class<T> clazz) {
        String plaintext = new String(Base64.decodeBase64(ciphertext), StandardCharsets.UTF_8);
        return decrypt(plaintext, clazz);
    }

    /**
     * RSA解密
     * @param ciphertext 密文
     * @param clazz 解密成的对象
     * @return T
     */
    public static <T> T decryptRsa(String ciphertext, Class<T> clazz) {
        String plaintext = RsaUtil.decrypt(ciphertext);
        return decrypt(plaintext, clazz);
    }

    /**
     * AES/CBC/PKCS7Padding 解密
     * @param ciphertext 密文
     * @param clazz 解密成的对象
     * @return T
     */
    public static <T> T decryptAes(String ciphertext, Class<T> clazz) {
        String plaintext = AesUtil.decrypt(ciphertext);
        return decrypt(plaintext, clazz);
    }

    /**
     * 解密为对象 clazz
     * @param plaintext 明文
     * @param clazz 对象
     * @return T 解密后的对象
     */
    private static <T> T decrypt(String plaintext, Class<T> clazz) {
        if(LANG_STRING.equals(clazz.getSimpleName())){
            return clazz.cast(plaintext);
        }
        JSONObject jsonObject = JsonUtil.parseObject(plaintext);
        if(null == jsonObject){
            throw new BaseException("系统错误，请重试！！");
        }
        if(JSON_OBJECT.equals(clazz.getSimpleName())){
            return clazz.cast(jsonObject);
        }
        try {
            T t = clazz.newInstance();
            invokeSetter(t, jsonObject, clazz);
            return t;
        }  catch (InstantiationException | IllegalAccessException e) {
            log.error("实例化{}失败：{}",clazz.getSimpleName(),e.getMessage());
            throw new BaseException("系统错误，请重试！！");
        }
    }

    /**
     * 通过映射，给 t 赋值
     * @param t 赋值对象
     * @param jsonObject 源数据
     * @param clazz class
     */
    private static <T> void invokeSetter(T t, JSONObject jsonObject, Class<?> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String propertyName = field.getName();
            Object fieldValue = jsonObject.get(propertyName);
            if("serialVersionUID".equals(propertyName)){
                continue;
            }
            ReflectUtils.invokeSetter(t, propertyName, fieldValue);
        }
        // 映射父类中属性值
        Class<?> supper = clazz.getSuperclass();
        if(supper != Object.class){
            invokeSetter(t, jsonObject, supper);
        }
    }

}
