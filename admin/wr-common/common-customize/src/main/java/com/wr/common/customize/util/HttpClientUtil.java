package com.wr.common.customize.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * @Author: RainCity
 * @Date: 2021-10-15 11:10:00
 * @Desc: 工具类
 */
public class HttpClientUtil extends BaseUtils {

    private static final int SOCKET_TIMEOUT = 1000;
    private static final int CONNECT_TIMEOUT = 1000;
    private static final String UTF_8 = "UTF-8";

    /**
     * 无参 get
     * @param url 请求地址
     * @return java.lang.String
     */
    public static String get(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return get(url, httpclient);
    }

    /**
     * 有参 get
     * @param url 请求地址
     * @param params 请求参数
     * @return java.lang.String
     */
    public static String get(String url, Map<String, Object> params) throws Exception {
        return get(url + "?" + buildParams(params));
    }

    /**
     * https请求 get
     * @param url 请求地址
     * @return java.lang.String
     */
    public static String getHttps(String url) throws Exception {
        CloseableHttpClient httpclient = null;
        if (StringUtils.startsWith(url, "https")) {
            httpclient = createSslClientDefault();
        } else {
            httpclient = HttpClients.createDefault();
        }
        return get(url, httpclient);
    }

    private static String get(String url, CloseableHttpClient httpclient) throws IOException {
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, UTF_8);
        httpclient.close();
        return html;
    }

    private static String post(String url, String params, String contentType, CloseableHttpClient httpclient) throws IOException {
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(params, UTF_8);
        entity.setContentType(contentType);
        entity.setContentEncoding(UTF_8);
        httppost.setEntity(entity);

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity httpEntity = response.getEntity();
        String html = EntityUtils.toString(httpEntity, UTF_8);
        httpclient.close();
        return html;
    }

    private static String post(String url, Map<String, Object> params, CloseableHttpClient httpclient) throws IOException {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            formparams.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity httpEntity = response.getEntity();
        String html = EntityUtils.toString(httpEntity, UTF_8);
        httpclient.close();
        return html;
    }

    /**
     * 有参 post
     * @param url 请求地址
     * @param params 请求参数（string）
     * @param contentType 参数类型  application/json   application/x-www-form-urlencoded
     * @return java.lang.String
     */
    public static String post(String url, String params, String contentType) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return post(url, params, contentType, httpclient);
    }

    /**
     * 有参 post
     * @param url 请求地址
     * @param params 请求参数（map）
     * @return java.lang.String
     */
    public static String post(String url, Map<String, Object> params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return post(url, params, httpclient);
    }

   /**
    * postHttps
    * @param url 请求地址
    * @param params 请求参数(map)
    * @return java.lang.String
    */
    public static String postHttps(String url, Map<String, Object> params) throws Exception {
        CloseableHttpClient httpclient = null;
        if (StringUtils.startsWith(url, "https")) {
            httpclient = createSslClientDefault();
        } else {
            httpclient = HttpClients.createDefault();
        }
        return post(url, params, httpclient);
    }

    /**
     * postJsonHttps
     * @param url 请求地址
     * @param params 请求参数（json string）
     * @return java.lang.String
     */
    public static String postJsonHttps(String url, String params) throws Exception {
        CloseableHttpClient httpclient = null;
        if (StringUtils.startsWith(url, "https")) {
            httpclient = createSslClientDefault();
        } else {
            httpclient = HttpClients.createDefault();
        }
        return post(url, params, "application/json", httpclient);
    }

    /**
     * postJsonHttps
     * @param keyStorePath 密钥库路径
     * @param keyStorepass 密钥库密码
     * @param url 请求地址
     * @param params 请求参数(json string)
     * @return java.lang.String
     */
    public static String postJsonHttps(String keyStorePath, String keyStorepass, String url, String params) throws Exception {
        CloseableHttpClient httpclient = custom(keyStorePath, keyStorepass);
        return post(url, params, "application/json", httpclient);
    }

    /**
     * postHttps
     * @param keyStorePath 密钥库路径
     * @param keyStorepass 密钥库密码
     * @param url 请求地址
     * @param params 请求参数(map)
     * @return java.lang.String
     */
    public static String postHttps(String keyStorePath, String keyStorepass, String url, Map<String, Object> params) throws Exception {
        CloseableHttpClient httpclient = custom(keyStorePath, keyStorepass);
        return post(url, params, httpclient);
    }

    /**
     *
     * 模拟请求
     * @param url 请求地址
     * @param map 请求参数（map）
     * @param encoding 编码
     * @return java.lang.String
     */
    public static String postHttps(String keyStorePath, String keyStorepass, String url, Map<String, String> map, String encoding) throws IOException {
        String body = "";
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = custom(keyStorePath, keyStorepass).execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();
        return body;
    }

    /**
     * put请求
     * @param url 请求地址
     * @param params 请求参数(map)
     * @return
     * @throws Exception
     */
    public static String put(String url, Map<String, Object> params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = null;
        if(params != null){
            httpPut = new HttpPut(url+"?" + buildParams(params));
        }else{
            httpPut = new HttpPut(url);
        }
        HttpResponse response = httpclient.execute(httpPut);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, UTF_8);
        httpclient.close();
        return html;
    }

    /**
     * 用来解析https
     * @return org.apache.http.impl.client.CloseableHttpClient
     */
    public static CloseableHttpClient createSslClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 设置信任自签名证书
     * @param keyStorePath 密钥库路径
     * @param keyStorepass 密钥库密码 如果密码为空，则用"nopassword"代替
     * @return org.apache.http.impl.client.CloseableHttpClient
     */
    public static CloseableHttpClient custom(String keyStorePath, String keyStorepass) {
        SSLContext sc = null;
        CloseableHttpClient client = null;
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(keyStorePath);
            trustStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();

            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sc))
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            HttpClients.custom().setConnectionManager(connManager);

            // 创建自定义的httpclient对象
            client = HttpClients.custom().setConnectionManager(connManager).build();

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != instream){
                    instream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return client;
    }

    /**
     * 访问服务
     *
     * @param wsdl   wsdl地址
     * @param ns     命名空间
     * @param method 方法名
     * @param params   参数
     * @return
     * @throws Exception
     */
    public static String webService(String wsdl, String ns, String soapAction, String method, Map<String, String> params) throws Exception {
        // 拼接SOAP
        StringBuilder soapRequestData = new StringBuilder("");
        StringBuilder bu = new StringBuilder("");
        if (params != null) {
            for (Entry<String, String> m : params.entrySet()) {
                String name = m.getKey();
                String content = m.getValue();
                bu.append("<tem:").append(name).append(">").append(content).append("</tem:").append(name).append(">");
            }
        }
        // 拼接SOAP
        if(null == ns){
            soapRequestData.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">");
            soapRequestData.append("<soap:Header/>");
            soapRequestData.append("<soap:Body>");
            soapRequestData.append("<tem:").append(method).append(">");
            soapRequestData.append(bu.toString());
            soapRequestData.append("</tem:").append(method).append(">");
            soapRequestData.append("</soap:Body>");
            soapRequestData.append("</soap:Envelope>");
            return doPostSoap(wsdl, soapRequestData.toString(), "text/xml;charset=UTF-8", soapAction);
        }
        soapRequestData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"").append(ns).append("\">");
        soapRequestData.append("<soapenv:Header/>");
        soapRequestData.append("<soapenv:Body>");
        soapRequestData.append("<tem:").append(method).append(">");
        soapRequestData.append(bu.toString());
        soapRequestData.append("</tem:").append(method).append(">");
        soapRequestData.append("</soapenv:Body>");
        soapRequestData.append("</soapenv:Envelope>");
        return doPostSoap(wsdl, soapRequestData.toString(), "application/soap+xml;charset=UTF-8", "");
    }

    public static String doPostSoap(String postUrl, String soapXml, String contentType, String soapAction) throws IOException {
        // HttpClient
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(postUrl);
        StringEntity entity = new StringEntity(soapXml, UTF_8);
        entity.setContentType(contentType);
        entity.setContentEncoding(UTF_8);
        httppost.setEntity(entity);
        httppost.setHeader("SOAPAction", soapAction);
        httppost.setConfig(requestConfig);

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity1 = response.getEntity();
        String html = EntityUtils.toString(entity1, UTF_8);
        httpclient.close();

        return html;
    }



    /**
     * 构建请求参数
     * @param params 请求参数
     * @return java.lang.String
     */
    public static String buildParams(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Entry<String, Object> entry : sortParams.entrySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()),"UTF-8"));
        }
        return sb.toString();
    }

    /**
     * 获取 HttpServletRequest body
     * @param request HttpServletRequest
     * @return java.lang.String
     */
    public static String getRequestBody(HttpServletRequest request){
        ServletInputStream stream = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            stream = request.getInputStream();
            // 获取响应
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }catch (IOException e){
            log.error("读取返回支付接口数据流出现异常");
            return null;
        }finally {
            try {
                if(null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
