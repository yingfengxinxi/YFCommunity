package com.wr.common.payment.pay.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.text.StrFormatter;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.WechatConstants;
import com.wr.common.payment.domain.WechatOpen;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
public class WechatPayUtils {
    /**
     * 获取私钥
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String filename) throws IOException {
        System.out.println("filename:" + filename);
        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    public static String getToken(String fileName, String mchId, String mchSerialNo, String method, HttpUrl url, String body) throws Exception {
        String nonceStr = getNonceStr();
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(fileName, message.getBytes("utf-8"));
        return WechatConstants.SCHEMA + " mchid=\"" + mchId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + mchSerialNo + "\","
                + "signature=\"" + signature + "\"";
    }


    /**
     * 生成签名
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String sign(String fileName, byte[] message) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        //sign.initSign(getPrivateKey(WxV3PayConfig.fileName));
        sign.initSign(getPrivateKey(fileName));
        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 生成签名串
     *
     * @param method
     * @param url
     * @param timestamp
     * @param nonceStr
     * @param body
     * @return
     */
    public static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }
        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static String getNonceStr() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "")
                .substring(0, 32);
    }

//

    /**
     * 验证签名
     *
     * @param certificate
     * @param message
     * @param signature
     * @return
     */
    public static boolean verify(X509Certificate certificate, byte[] message, String signature) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(certificate);
            sign.update(message);
            return sign.verify(Base64.getDecoder().decode(signature));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名验证过程发生了错误", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }


    /**
     * 拼接参数
     *
     * @return
     */

    private static String buildMessageTwo(String appId, long timestamp, String nonceStr, String packag) {
        return appId + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + packag + "\n";
    }

    /**
     * @param wechatOpen
     * @return
     */
    public static Map<String, Object> payment(WechatOpen wechatOpen) {
        try {
            log.info("微信支付调用:{}", wechatOpen);

            log.info("********** 微信支付==> *************");
            log.info("请求参数:{}", wechatOpen.toString());
            Map<String, Object> map = new HashMap<>();

            Map<String, Object> mapPay = weChatDoUnifiedOrder(wechatOpen);
            if (mapPay != null) {
                mapPay.entrySet().forEach(x -> {
                    map.put(x.getKey(), x.getValue());
                });
            }

            //前端拉起支付使用
            Map<String, Object> payResultMap = getWxPayResultMap(wechatOpen, String.valueOf(map.get("prepay_id")));
            if (payResultMap != null) {
                payResultMap.entrySet().forEach(x -> {
                    map.put(x.getKey(), x.getValue());
                });
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建签名
     *
     * @param prepayId
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getWxPayResultMap(WechatOpen wechatOpen, String prepayId) throws Exception {
        String timestamp = String.valueOf(new Date().getTime());
        //随机字符串
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

        StringBuilder sb = new StringBuilder();
        // 应用id
        sb.append(wechatOpen.getAppId()).append("\n");
        // 支付签名时间戳
        sb.append(timestamp).append("\n");
        // 随机字符串
        sb.append(nonceStr).append("\n");
        // 预支付交易会话ID
        sb.append("prepay_id=").append(prepayId).append("\n");
        // 签名
        Signature sign = Signature.getInstance("SHA256withRSA");
        // 获取商户私钥并进行签名
        PrivateKey privateKey = getPrivateKey(wechatOpen.getPrivateKeyUrl());
        sign.initSign(privateKey);
        sign.update(sb.toString().getBytes("utf-8"));
        String paySign = Base64.getEncoder().encodeToString(sign.sign());

        // 将签名时数据和签名一起返回前端用于前端吊起支付

        Map<String, Object> map = new HashMap<>();
        map.put("appId", wechatOpen.getAppId()); // 小程序id
        map.put("timeStamp", timestamp); // 时间戳
        map.put("nonceStr", nonceStr); // 随机字符串
        map.put("package", "prepay_id=" + prepayId); // 预支付交易会话ID
        map.put("signType", "RSA"); // 签名方式
        map.put("paySign", paySign); // 签名
        return map;
    }

    /*****************************支付接口*******************************/
    /**
     * V3微信支付统一下单
     *
     * @param totalMoney  总金额
     * @param outTradeNo  订单号
     * @param description 描述
     * @param openId      支付者用户的Openid
     * @return
     */
    public static Map<String, Object> weChatDoUnifiedOrder(WechatOpen wechatOpen) {
        //微信支付返回

        if (wechatOpen.getTotalAmount() == null) {
            throw new ServiceException("支付金额不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getAppId())) {
            throw new ServiceException("APPID不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getMchId())) {
            throw new ServiceException("商户号不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getDescription())) {
            throw new ServiceException("商品描述不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getOutTradeNo())) {
            throw new ServiceException("商品订单号不能为空");
        }
        if (StringUtils.isEmpty(wechatOpen.getNotifyUrl())) {
            throw new ServiceException("请配置支付回调地址");
        }
        if (StringUtils.isEmpty(wechatOpen.getOpenId())) {
            throw new ServiceException("微信个人唯一标识不能为空");
        }
        log.info("=======================START支付订单号【{}】正在支付=======================", wechatOpen.getOutTradeNo());
        //支付总金额
        BigDecimal totalPrice = BigDecimal.ZERO;
        totalPrice = totalPrice.add(wechatOpen.getTotalAmount());
        //转换金额保留两位小数点
        Integer money = new BigDecimal(String.valueOf(totalPrice)).movePointRight(2).intValue();
        Map<String, Object> map = new HashMap();
        // 支付的产品（小程序或者公众号，主要需要和微信支付绑定哦）
        map.put("appid", wechatOpen.getAppId());
        // 支付的商户号
        map.put("mchid", wechatOpen.getMchId());
        //临时写死配置
        map.put("description", wechatOpen.getDescription());
        map.put("out_trade_no", wechatOpen.getOutTradeNo());
        map.put("notify_url", wechatOpen.getNotifyUrl());
        Map<String, Object> amount = new HashMap();
        //订单金额 单位分
        amount.put("total", money);
        amount.put("currency", "CNY");
        map.put("amount", amount);
        // 设置小程序所需的opendi
        Map<String, Object> payermap = new HashMap();
        payermap.put("openid", wechatOpen.getOpenId());
        map.put("payer", payermap);

        ObjectMapper objectMapper = new ObjectMapper();
        String body;

        try {
            body = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String requestUrl = WechatConstants.JSAPI_URL;
        wechatOpen.setUrl(requestUrl);
        wechatOpen.setBody(body);
        Map<String, Object> stringObjectMap = HttpUtils.doPostWexin(wechatOpen);
        stringObjectMap.put("orderNo", wechatOpen.getOutTradeNo());
        // 记录信息
        String responseBody = JSONObject.toJSONString(stringObjectMap);
        log.info("支付响应内容：{}", responseBody);
        log.info("=======================END支付订单号【{}】正在支付=======================", wechatOpen.getOutTradeNo());
        return stringObjectMap;
    }

    /**
     * 查询支付订单结果
     *
     * @param outTradeNo 订单号
     * @return
     */

    public static Map<String, String> getPayOrderResult(WechatOpen wechatOpen) {
        log.info("=======================START正在查询支付订单号【{}】支付结果=======================", wechatOpen.getOutTradeNo());
        Map<String, String> map = new HashMap<>();
        try {
            //验证证书
            WXPaySignatureCertificateUtil.checkSign(wechatOpen.getPrivateKeyUrl(), wechatOpen.getMchId(), wechatOpen.getApiKey(), wechatOpen.getSerialNumber());
            //查询订单结果
            String url = StrFormatter.format(WechatConstants.DOMAIN_API + WechatConstants.SELECT_PAY_TRANSACTIONS_JSAPI, wechatOpen.getOutTradeNo());
            url = url + "?mchid=" + wechatOpen.getMchId();
            wechatOpen.setUrl(url);
            Map<String, Object> resultMap = HttpUtils.doGet(wechatOpen);

            log.info("查询支付订单响应内容：{}", resultMap);

            String tradeState = String.valueOf(resultMap.get("trade_state"));
            String tradeStateDesc = String.valueOf(resultMap.get("trade_state_desc"));

            if (tradeState.equals("SUCCESS")) {
                map.put("code", "200");
                String successTime = String.valueOf(resultMap.get("success_time"));
                successTime = getTime(successTime);
                map.put("successTime", successTime);
            } else if (tradeState.equals("NOTPAY")) {
                //订单未支付
                map.put("code", "502");
                String payOrderNo = String.valueOf(resultMap.get("out_trade_no"));
                map.put("payOrderNo", payOrderNo);
            } else if (tradeState.equals("ORDER_NOT_EXIST")) {
                //订单不存在
                map.put("code", "501");
            } else {
                map.put("code", "500");
            }
            map.put("msg", tradeStateDesc);


        } catch (Exception e) {
            e.printStackTrace();
            //  log.error("查询失败:" + outTradeNo + e);
            map.put("code", "500");
            map.put("msg", e.getMessage());
        }
        log.info("=======================END正在查询支付订单号【{}】支付结果=======================", wechatOpen.getOutTradeNo());
        return map;
    }


    /**
     * 格式化时间
     *
     * @param successTime
     * @return
     * @throws ParseException
     */
    private static String getTime(String successTime) throws ParseException {
        SimpleDateFormat DF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date s = sdf.parse(successTime);
        return DF1.format(s);
    }

    /**
     * 关闭订单
     *
     * @param wechatOpen 订单号
     * @return
     */
    public static Map<String, Object> closeOrder(WechatOpen wechatOpen) {
        log.info("=======================START正在关闭支付订单号【{}】的订单=======================", wechatOpen.getOutTradeNo());
        Map<String, Object> map = new HashMap<>();
        try {
            //验证证书
            WXPaySignatureCertificateUtil.checkSign(wechatOpen.getPrivateKeyUrl(), wechatOpen.getMchId(), wechatOpen.getApiKey(), wechatOpen.getSerialNumber());

            //关闭订单
            String requestUrl = StrFormatter.format(WechatConstants.DOMAIN_API + WechatConstants.PAY_TRANSACTIONS_OUT_TRADE_NO, wechatOpen.getOutTradeNo());


            //2.添加商户id
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("mchid", wechatOpen.getMchId());
            String body = objectMapper.writeValueAsString(rootNode);
            wechatOpen.setUrl(requestUrl);
            wechatOpen.setBody(body);
            //3.调起微信关单接口
            Map<String, Object> stringObjectMap = HttpUtils.doPostWexin(wechatOpen);
            String responseBody = JSONObject.toJSONString(stringObjectMap);
            log.info("关闭订单响应内容：{}", responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("关单失败:" + wechatOpen.getOutTradeNo() + e);
            map.put("code", "500");
            map.put("message", "关单失败:" + wechatOpen.getOutTradeNo() + e);
        }
        log.info("=======================END正在关闭支付订单号【{}】的订单=======================", wechatOpen.getOutTradeNo());
        return map;
    }


    /**
     * 获取openid
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    public static Map<String, String> getOpenId(String appId, String appSecret, String code) {
        log.info("********** 获取微信openId==> *************");

        String url = WechatConstants.JS_CODE_URL
                .replace("APPID", appId)
                .replace("SECRET", appSecret)
                .replace("JSCODE", code);
        Map<String, String> map = new HashMap<>();
        try {
            String result = HttpClientUtil.get(url);
            JSONObject jsonObject = JsonUtil.parseObject(result);
            log.info("获取微信openId返回结果:{}", jsonObject);
            if (null == jsonObject) {
                map.put("code", "500");
                map.put("msg", "openId获取失败");
            }
            String openId = jsonObject.getString("openid");
            if (StringUtils.isNotEmpty(openId)) {
                String unionId = jsonObject.getString("unionid");
                map.put("code", "200");
                map.put("msg", "获取openid成功");
                map.put("openId", openId);
                map.put("unionId", unionId);
                return map;
            }
            String errmsg = jsonObject.getString("errmsg");
            map.put("code", "500");
            map.put("msg", errmsg);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求失败：{}", e.getMessage());
            map.put("code", "500");
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
    * @Author SUNk
    * @Description 获取手机号
    * @Date 10:48 2024/4/8
    * @Param [appId, appSecret, code]
    * @return java.util.Map<java.lang.String,java.lang.String>
    **/
    public static String getPhoneNumber(String appId, String appSecret, String code) {
        log.info("********** getPhoneNumber===START==> *************");

        JSONObject phone;
        // 获取token
        String token_url = String.format(WechatConstants.PHONE_TOKEN_URL,
                appId, appSecret);
        try {
            JSONObject token = JSON.parseObject(HttpClientSslUtils.doGet(token_url));
            if (token == null) {
                log.info("获取token失败");
                return null;
            }
            String accessToken = token.getString("access_token");
            if (StringUtils.isEmpty(accessToken)) {
                log.info("获取token失败");
                return null;
            }
            log.info("token : {}", accessToken);
            //获取phone
            String url = WechatConstants.PHONE_NUMBER_URL
                    + "?access_token=" + accessToken;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            String reqJsonStr = JSONObject.toJSONString(jsonObject);
            phone = JSON.parseObject(HttpClientSslUtils.doPost(url, reqJsonStr));

            if (phone == null) {
                log.info("获取手机号失败");
                return null;
            }else {
                if(StringUtils.equals( phone.getString( "errmsg" ),"ok" )){
                    JSONObject phone_info = phone.getJSONObject( "phone_info" );
                    String phoneNumber = phone_info.getString( "phoneNumber" );
                    return phoneNumber;
                }else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
