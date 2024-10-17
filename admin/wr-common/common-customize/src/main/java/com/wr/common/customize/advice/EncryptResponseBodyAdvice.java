package com.wr.common.customize.advice;

import com.alibaba.fastjson2.JSON;
import com.wr.common.core.business.annotation.EncryptResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author: RainCity
 * @Date: 2022-08-29 13:44:18
 * @Desc: 返回参数加密
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    private static ThreadLocal<Boolean> encryptLocal = new ThreadLocal<>();

    @Override
    public boolean supports(MethodParameter returnParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnParameter.getMethod().isAnnotationPresent(EncryptResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Boolean status = encryptLocal.get();
        if (null != status && !status) {
            encryptLocal.remove();
            return null;
        }
        try {
            String content = JSON.toJSONString(body);
            return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Encrypted data exception", e);
            return null;
        }
        
    }
}
