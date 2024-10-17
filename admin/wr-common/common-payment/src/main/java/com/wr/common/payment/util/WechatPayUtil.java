package com.wr.common.payment.util;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.constants.WechatConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.domain.dto.WechatDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

/**
 * @Author: RainCity
 * @Date: 2021-11-02 11:06:10
 * @Desc: 微信支付相关工具类
 */
public class WechatPayUtil {
    private static final Logger log = LoggerFactory.getLogger(WechatPayUtil.class);

    private static CloseableHttpClient httpClient;

    private static HttpPost httpPost;

    private WechatPayUtil() {
    }

    /**
     * 初始化http
     * @param wechatOpen
     * @see WechatOpen#toString()
     * @param url 请求地址
     * @throws FileNotFoundException
     */
    public static void initHttp(WechatOpen wechatOpen, String url) throws IOException, GeneralSecurityException, NotFoundException, HttpCodeException {
        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
        httpClient = initHttpClient(wechatOpen.getMchId(), wechatOpen.getSerialNumber(),
                wechatOpen.getPrivateKeyUrl(), wechatOpen.getApiKey());

        httpPost = initHttpPost(url);
    }

    /**
     * 参数封装
     * @param objectMapper
     * @param wechatOpen
     * @see WechatOpen#toString()
     * @return ObjectNode
     */
    public static ObjectNode encapsulation(ObjectMapper objectMapper, WechatOpen wechatOpen){
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode
                //应用ID
                .put("appid", wechatOpen.getAppId())
                //直连商户号
                .put("mchid", wechatOpen.getMchId())
                //商品描述
                .put("description", wechatOpen.getDescription())
                //通知地址
                .put("notify_url", wechatOpen.getNotifyUrl())
                //商户订单号
                .put("out_trade_no", wechatOpen.getOutTradeNo());
        //附加数据
        if(StringUtils.isNotEmpty(wechatOpen.getAttach())){
            rootNode.put("attach", wechatOpen.getAttach());
        }
        //订单失效时间,格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE,建议：最短失效时间间隔大于1分钟
        if(StringUtils.isNotEmpty(wechatOpen.getTimeExpire())){
            rootNode.put(WechatConstants.TIME_EXPIRE, wechatOpen.getTimeExpire());
        }

        //订单金额
        BigDecimal bigDecimal = wechatOpen.getTotalAmount();
        int totalAmount = bigDecimal.multiply(new BigDecimal(100)).intValue();
        rootNode.putObject("amount")
                //总金额
                .put("total", totalAmount)
                //货币类型  默认CNY
                .put("currency","CNY");
        return rootNode;
    }

    /**
     * JSAPI、小程序下单
     * @param wechatOpen 请求参数
     * @see WechatOpen#toString()
     * @return 预支付交易会话标识。用于后续接口调用中使用,该值有效期为2小时
     * @throws IOException 调用异常
     */
    public static String transactionsJsApi(WechatOpen wechatOpen) throws IOException, GeneralSecurityException, NotFoundException, HttpCodeException {
        initHttp(wechatOpen, WechatConstants.JSAPI_URL);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = encapsulation(objectMapper, wechatOpen);
        //支付者
        rootNode.putObject("payer")
                //用户标识
                .put("openid", wechatOpen.getOpenId());
        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString(PayConstants.UTF_8), PayConstants.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        String bodyAsString = EntityUtils.toString(response.getEntity());
        payLog(PayConstants.JSAPI, bodyAsString);

        httpClient.close();
        JSONObject result = JsonUtil.parseObject(bodyAsString);
        return null != result  ? result.getString("prepay_id") : null;
    }

    /**
     * H5下单
     * @param wechatOpen 请求参数
     * @see WechatOpen#toString()
     * @return 支付跳转链接。h5_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付，h5_url的有效期为5分钟
     * @throws IOException 调用异常
     */
    public static String transactionsH5(WechatOpen wechatOpen) throws IOException, GeneralSecurityException, NotFoundException, HttpCodeException {
        initHttp(wechatOpen, WechatConstants.H5_URL);
        //请求参数封装
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = encapsulation(objectMapper, wechatOpen);
        //场景信息
        rootNode.putObject("scene_info")
                //用户终端IP
                .put("payer_client_ip", wechatOpen.getClientIp())
                //H5场景信息
                .putObject("h5_info")
                //场景类型
                .put("type", "Wap");

        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString(PayConstants.UTF_8), PayConstants.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String bodyAsString = EntityUtils.toString(response.getEntity());
        payLog(PayConstants.H5, bodyAsString);

        httpClient.close();
        JSONObject result = JsonUtil.parseObject(bodyAsString);
        return null != result  ? result.getString("h5_url") : null;
    }


    /**
     * APP下单
     * @param wechatOpen 请求参数
     * @see WechatOpen#toString()
     * @return 预支付交易会话标识。用于后续接口调用中使用,该值有效期为2小时
     * @throws IOException 调用异常
     */
    public static String transactionsApp(WechatOpen wechatOpen) throws IOException, GeneralSecurityException, NotFoundException, HttpCodeException {
        initHttp(wechatOpen, WechatConstants.APP_URL);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = encapsulation(objectMapper, wechatOpen);
        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString(PayConstants.UTF_8), PayConstants.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        String bodyAsString = EntityUtils.toString(response.getEntity());
        payLog(PayConstants.APP, bodyAsString);

        httpClient.close();
        JSONObject result = JsonUtil.parseObject(bodyAsString);
        return null != result  ? result.getString("prepay_id") : null;
    }


    /**
     * Native下单
     * @param wechatOpen 请求参数
     * @see WechatOpen#toString()
     * @return 二维码链接。code_url并非固定值，使用时按照URL格式转成二维码即可
     * @throws IOException 调用异常
     */
    public static String transactionsNative(WechatOpen wechatOpen) throws IOException, GeneralSecurityException, NotFoundException, HttpCodeException {
        initHttp(wechatOpen, WechatConstants.NATIVE_URL);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = encapsulation(objectMapper, wechatOpen);
        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString(PayConstants.UTF_8), PayConstants.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        String bodyAsString = EntityUtils.toString(response.getEntity());
        payLog(PayConstants.NATIVE, bodyAsString);

        httpClient.close();
        JSONObject result = JsonUtil.parseObject(bodyAsString);
        return null != result  ? result.getString("code_url") : null;
    }

    /**
     * 订单查询
     * @param wechatDto 请求参数
     * @see WechatDTO#toString()
     * @return 订单结果
     */
    public static JSONObject transactionsQuery(WechatDTO wechatDto) throws URISyntaxException, IOException, GeneralSecurityException, NotFoundException, HttpCodeException {
        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
        CloseableHttpClient httpClient = initHttpClient(wechatDto.getMchId(), wechatDto.getSerialNumber(),
                wechatDto.getPrivateKeyUrl(), wechatDto.getApiKey());

        String url = WechatConstants.QUERY_URL.replace("OUTTRADENO",wechatDto.getOutTradeNo())
                .replace("MERCHANTID",wechatDto.getMchId());
        URIBuilder uriBuilder = new URIBuilder(url);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpGet);
        String bodyAsString = EntityUtils.toString(response.getEntity());
        log.info("微信订单查询:{}",bodyAsString);
        return JsonUtil.parseObject(bodyAsString);
    }

    /**
     * 微信订单关闭
     * @param wechatDto 请求参数
     * @see WechatDTO#toString()
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    public static AjaxResult transactionsClose(WechatDTO wechatDto) throws IOException, GeneralSecurityException, NotFoundException, HttpCodeException {
        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
        CloseableHttpClient httpClient = initHttpClient(wechatDto.getMchId(), wechatDto.getSerialNumber(),
                wechatDto.getPrivateKeyUrl(), wechatDto.getApiKey());

        String url = WechatConstants.CLOSE_URL.replace("OUTTRADENO",wechatDto.getOutTradeNo());
        HttpPost httpPost = initHttpPost(url);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid",wechatDto.getMchId());

        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString(PayConstants.UTF_8), PayConstants.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        log.info("微信订单关闭==response==getStatusCode：{}",response.getStatusLine().getStatusCode());
        String bodyAsString = EntityUtils.toString(response.getEntity());
        log.info("微信订单关闭：{}",bodyAsString);
        if(204 == response.getStatusLine().getStatusCode()){
            return AjaxResult.success("订单关闭成功");
        }
        return AjaxResult.error("订单关闭失败");
    }

    /**
     * 初始化 HttpClient
     * @param mchId 商户号
     * @param serialNumber 商户序列号
     * @param privateKeyUrl 私钥地址
     * @param apiKey Api v3Key
     * @return org.apache.http.impl.client.CloseableHttpClient
     */
    public static CloseableHttpClient initHttpClient(String mchId, String serialNumber, String privateKeyUrl, String apiKey) throws IOException, GeneralSecurityException, HttpCodeException, NotFoundException {
        //读取商户私钥
        log.info("证书地址:{}",privateKeyUrl);
        PrivateKey privateKey = SignUtil.getPrivateKey(privateKeyUrl);
        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager
                // ... 若有多个商户号，可继续调用putMerchant添加商户信息
                .putMerchant(mchId,
                        new WechatPay2Credentials(mchId, new PrivateKeySigner(serialNumber, privateKey)),
                        apiKey.getBytes(StandardCharsets.UTF_8));
        // 从证书管理器中获取verifier
        Verifier verifier = certificatesManager.getVerifier(mchId);

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, serialNumber, privateKey)
                .withValidator(new WechatPay2Validator(verifier));

        //通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
        return builder.build();
    }

    /**
     * 初始化 HttpPost
     * @param uri 请求地址
     * @return org.apache.http.client.methods.HttpPost
     */
    public static HttpPost initHttpPost(String uri){
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        return httpPost;
    }

    public static JSONObject decodeWechatData(JSONObject encrypt,String apiKey){
        String algorithm = encrypt.getString("algorithm");
        String ciphertext = encrypt.getString("ciphertext");
        String associatedData = encrypt.getString("associated_data");
        String originalType = encrypt.getString("original_type");
        String nonce = encrypt.getString("nonce");
        String decrypt = CertificateUtil.decryptResBody(apiKey, associatedData, nonce, ciphertext);
        return JsonUtil.parseObject(decrypt);
    }

    private static void payLog(String payWay, String result){
        log.info("支付方式：{}，统一下单接口:{}",payWay,result);
    }

}
