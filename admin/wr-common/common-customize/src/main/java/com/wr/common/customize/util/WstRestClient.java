package com.wr.common.customize.util;

import com.iflytek.wst.gateway.sdk.client.ApacheHttpClient;
import com.iflytek.wst.gateway.sdk.constant.SdkConstant;
import com.iflytek.wst.gateway.sdk.enums.HttpMethod;
import com.iflytek.wst.gateway.sdk.enums.ParamPosition;
import com.iflytek.wst.gateway.sdk.enums.Scheme;
import com.iflytek.wst.gateway.sdk.model.ApiRequest;
import com.iflytek.wst.gateway.sdk.model.ApiResponse;
import com.iflytek.wst.gateway.sdk.model.HttpClientBuilderParams;
import com.wr.common.customize.constant.WstConstants;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * @Author: RainCity
 * @Date: 2022-03-12 13:35:54
 * @Desc:
 */
public class WstRestClient extends ApacheHttpClient {
    /** 单例模式 */
    private static WstRestClient instance = new WstRestClient();

    public static WstRestClient getInstance() {
        return instance;
    }

    private Scheme scheme = Scheme.HTTPS;


    private WstRestClient() {
        // HTTP Client init
        HttpClientBuilderParams httpClientBuilderParams = new HttpClientBuilderParams();
        httpClientBuilderParams.setAppKey(WstConstants.APP_KEY);
        httpClientBuilderParams.setAppSecret(WstConstants.APP_SECRET);
        httpClientBuilderParams.setScheme(scheme);
        httpClientBuilderParams.setHost(WstConstants.HOST);
        httpClientBuilderParams.setContextPath(WstConstants.CONTEXT_PATH);

        // HTTPS客户端需要单独设置，禁用证书校验
        if (scheme == Scheme.HTTPS) {
            //HTTPS Client init
            /**
             * HTTPS request use DO_NOT_VERIFY mode only for demo
             * Suggest verify for security
             */
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {

                    return new X509Certificate[0];
                }
            };

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                throw new RuntimeException(e);
            }
            HostnameVerifier dONOTVERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            httpClientBuilderParams.setSslSocketFactory(sslContext.getSocketFactory());
            httpClientBuilderParams.setX509TrustManager(xtm);
            httpClientBuilderParams.setHostnameVerifier(dONOTVERIFY);
        }

        super.init(httpClientBuilderParams);
    }



    public ApiResponse getUserInfoByToken(String token ){
        ApiRequest request = new ApiRequest(HttpMethod.GET, WstConstants.PATH);
        request.addParam("token",token , ParamPosition.QUERY, true);
        request.addParam("serviceId", WstConstants.SERVICE_ID, ParamPosition.QUERY, false);
        request.addParam("roleCode", "", ParamPosition.QUERY, false);
        return sendSyncRequest(request);
    }

    public String getResultString(ApiResponse response) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("Response from backend server").append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
        result.append("ResultCode:").append(SdkConstant.CLOUDAPI_LF).append(response.getCode()).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
        if (response.getCode() != 200) {
            result.append("Error description:").append(response.getHeaders().get("X-Ca-Error-Message")).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
        }
        result.append("ResultBody:").append(SdkConstant.CLOUDAPI_LF).append(response.getBody() == null ? "null" : new String(response.getBody(), SdkConstant.CLOUDAPI_ENCODING));

        return result.toString();
    }
}
