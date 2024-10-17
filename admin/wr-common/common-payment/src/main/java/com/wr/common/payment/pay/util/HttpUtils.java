package com.wr.common.payment.pay.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.payment.domain.WechatOpen;
import okhttp3.HttpUrl;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class HttpUtils {
    private static final ObjectMapper JSON = new ObjectMapper();

    /**
     * 封装get请求
     *
     * @param wechatOpen
     * @return
     */
    public static Map<String, Object> doGet(WechatOpen wechatOpen) {
        if (StringUtils.isEmpty(wechatOpen.getPrivateKeyUrl())) {
            throw new ServiceException("证书不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getUrl())) {
            throw new ServiceException("请求地址不能为空");
        }

        if (StringUtils.isEmpty(wechatOpen.getMchId())) {
            throw new ServiceException("商户号不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getSerialNumber())) {
            throw new ServiceException("证书序列号不能为空");
        }
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(wechatOpen.getUrl());
        httpget.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpget.addHeader("Accept", "application/json");
        try {
            HttpUrl httpurl = HttpUrl.parse(wechatOpen.getUrl());
            String token = WechatPayUtils.getToken(wechatOpen.getPrivateKeyUrl(), wechatOpen.getMchId(), wechatOpen.getSerialNumber(), "GET", httpurl, "");
            httpget.addHeader("Authorization", token);
            CloseableHttpResponse httpResponse = httpClient.execute(httpget);
            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonResult = EntityUtils.toString(httpEntity);
            return JSON.readValue(jsonResult, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 封装post请求
     *
     * @return
     */
    public static Map<String, Object> doPostWexin(WechatOpen wechatOpen) {
        if (StringUtils.isEmpty(wechatOpen.getPrivateKeyUrl())) {
            throw new ServiceException("证书不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getUrl())) {
            throw new ServiceException("请求地址不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getBody())) {
            throw new ServiceException("请求体不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getMchId())) {
            throw new ServiceException("商户号不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getSerialNumber())) {
            throw new ServiceException("证书序列号不能为空");
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(wechatOpen.getUrl());
        httpPost.addHeader("Content-Type", "application/json;chartset=utf-8");
        httpPost.addHeader("Accept", "application/json");
        try {
            HttpUrl httpurl = HttpUrl.parse(wechatOpen.getUrl());
            String body = wechatOpen.getBody();
            String token = WechatPayUtils.getToken(wechatOpen.getPrivateKeyUrl(), wechatOpen.getMchId(), wechatOpen.getSerialNumber(), "POST", httpurl, body);
            httpPost.addHeader("Authorization", token);

            if (body == null) {
                throw new IllegalArgumentException("data参数不能为空");
            }
            StringEntity stringEntity = new StringEntity(body, "utf-8");
            httpPost.setEntity(stringEntity);

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String jsonResult = EntityUtils.toString(httpEntity);
                return JSON.readValue(jsonResult, HashMap.class);
            } else {
                String string = EntityUtils.toString(httpEntity);
                System.err.println("错误信息" + string);
                return JSON.readValue(string, HashMap.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
