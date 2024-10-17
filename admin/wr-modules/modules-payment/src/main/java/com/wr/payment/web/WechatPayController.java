package com.wr.payment.web;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.constants.WechatConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.domain.dto.OpenDTO;
import com.wr.common.payment.domain.dto.WechatDTO;
import com.wr.common.payment.pay.util.HttpUtils;
import com.wr.common.payment.util.CertificateUtil;
import com.wr.common.payment.util.SignUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.payment.domain.CommunityWechat;
import com.wr.payment.service.CommunityService;
import com.wr.payment.service.WechatPayService;
import com.wr.payment.service.WechatTradeAfterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.*;

import static com.wr.common.payment.pay.util.WechatPayUtils.getPrivateKey;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 14:04:37
 * @Desc: 微信支付调用
 */
@RestController
@RequestMapping("/wechat")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WechatPayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WechatPayController.class);

    private final CommunityService communityService;
    private final List<WechatPayService> wechatPayServices;
    private final List<WechatTradeAfterService> wechatTradeAfterServices;



    @PostMapping("/getOpenId")
    public AjaxResult getOpenId(@RequestBody OpenDTO openDto) {
        log.info("********** 获取微信openId==> *************");
        CommunityWechat communityWechat = communityService.selectWechatById(null, openDto.getCommunityId());
        if (null == communityWechat) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        String url = WechatConstants.JS_CODE_URL
                .replace("APPID", communityWechat.getAppId())
                .replace("SECRET", communityWechat.getApiKey())
                .replace("JSCODE", openDto.getCode());
        try {
            String result = HttpClientUtil.get(url);
            JSONObject jsonObject = JsonUtil.parseObject(result);
            if (null == jsonObject) {
                return AjaxResult.error("openId获取失败");
            }
            String openId = jsonObject.getString("openid");
            String unionId = jsonObject.getString("unionid");
            AjaxResult ajaxResult = AjaxResult.success();
            ajaxResult.put("openId", openId);
            ajaxResult.put("unionId", unionId);
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求失败：{}", e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

    }

//    @PostMapping("/payment")
//    public AjaxResult wechatPay(@RequestBody WechatOpen wechatOpen) {
//
//        //WechatOpen wechatOpen = redisService.getCacheObject(redisKey);
//        logger.info("微信支付调用:{}", wechatOpen);
//        CommunityWechat communityWechat = communityService.selectWechatById(wechatOpen.getWechatId());
//        if (null == communityWechat) {
//            log.error("未查询到账号信息");
//            return AjaxResult.error("未查询到账号信息");
//        }
//        wechatOpen.setAppId(communityWechat.getAppId());
//        wechatOpen.setMchId(communityWechat.getMchId());
//        wechatOpen.setSerialNumber(communityWechat.getSerialNumber());
//        wechatOpen.setApiKey(communityWechat.getApiKey());
//        wechatOpen.setPrivateKeyUrl(communityWechat.getPrivateUrl());
//        log.info("********** 微信支付==> *************");
//        log.info("请求参数:{}", wechatOpen.toString());
//
//        WechatPayService wechatPayService = wechatPayServices.stream()
//                .filter(service -> service.support(wechatOpen.getMethod()))
//                .findAny()
//                .orElseThrow(() -> new RuntimeException("支付模式：" + wechatOpen.getMethod() + "不支持"));
//        //redisService.deleteObject(redisKey);
//        return wechatPayService.execute(wechatOpen);
//    }

    @PostMapping("payment")
    public AjaxResult payment(@RequestBody WechatOpen wechatOpen) {
        try {
            logger.info("微信支付调用:{}", wechatOpen);
            CommunityWechat communityWechat = communityService.selectWechatById(wechatOpen.getWechatId(),null);
            if (null == communityWechat) {
                log.error("未查询到账号信息");
                return AjaxResult.error("未查询到账号信息");
            }
            wechatOpen.setAppId(communityWechat.getAppId());
            wechatOpen.setMchId(communityWechat.getMchId());
            wechatOpen.setSerialNumber(communityWechat.getSerialNumber());
            wechatOpen.setApiKey(communityWechat.getApiKey());
            //String fileName="C:\\Users\\Administrator\\Desktop\\微信支付证书\\apiclient_key.pem";
            //String notifyUrl = CommunityConstants.ESTATE_SERVER_APP + "/v2/parkDuration/wechat_notify";
            wechatOpen.setPrivateKeyUrl(communityWechat.getPrivateUrl());
            log.info("********** 微信支付==> *************");
            log.info("请求参数:{}", wechatOpen.toString());
            Map<String, Object> map = new HashMap<>();
            //微信支付返回
            if (StringUtils.isEmpty(wechatOpen.getNotifyUrl())) {
                return AjaxResult.error("请配置支付回调地址");
            }
            if (wechatOpen.getTotalAmount() == null) {
                return AjaxResult.error("支付金额不能为空");
            }
            if (StringUtils.isEmpty(wechatOpen.getDescription())) {
                return AjaxResult.error("商品描述不能为空");
            }
            if (StringUtils.isEmpty(wechatOpen.getOpenId())) {
                return AjaxResult.error("微信个人唯一标识不能为空");
            }
            if (StringUtils.isEmpty(wechatOpen.getOutTradeNo())) {
                return AjaxResult.error("商品订单号不能为空");
            }
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
            return AjaxResult.success(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> weChatDoUnifiedOrder(WechatOpen wechatOpen) {
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
        System.out.println("body>>" + body);
        String requestUrl = WechatConstants.JSAPI_URL;
        wechatOpen.setUrl(requestUrl);
        wechatOpen.setBody(body);
        Map<String, Object> stringObjectMap = HttpUtils.doPostWexin(wechatOpen);
        // 记录信息
        String responseBody = JSONObject.toJSONString(stringObjectMap);
        log.info("支付响应内容：{}", responseBody);
        log.info("=======================END支付订单号【{}】正在支付=======================", wechatOpen.getOutTradeNo());
        return stringObjectMap;
    }

    /**
     * 构建签名
     *
     * @param prepayId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getWxPayResultMap(WechatOpen wechatOpen, String prepayId) throws Exception {
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

    @PostMapping("/after")
    public AjaxResult wechatAfter(@RequestBody WechatDTO wechatDto) {
        // 查询所选微信配置
        CommunityWechat wechat = communityService.selectWechatById(wechatDto.getWechatId(),null);
        if (null == wechat) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        wechatDto.setMchId(wechat.getMchId());
        wechatDto.setSerialNumber(wechat.getSerialNumber());
        wechatDto.setPrivateKeyUrl(wechat.getPrivateUrl());
        wechatDto.setApiKey(wechat.getApiKey());
        log.info("********** 微信支付后其他操作==> *************");
        log.info("请求参数:{}", wechatDto.toString());
        WechatTradeAfterService tradeAfterService = wechatTradeAfterServices.stream()
                .filter(service -> service.support(wechatDto.getMethod()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("模式：" + wechatDto.getMethod() + "不支持"));
        return tradeAfterService.execute(wechatDto);
    }

    /**
     * 示例代码
     *
     * @param request
     * @return {@link String}
     * @ver v1.0.0
     */
    @PostMapping("/wechat_notify")
    public String wechatNotify(HttpServletRequest request) {
        log.info("********** 微信支付回调执行 *************");
        //微信返回的请求体
        String body = HttpClientUtil.getRequestBody(request);
        if (null == body) {
            return PayConstants.WECHAT_FAILED_RETURN;
        }
        //商户证书序列号
        String serialNo = request.getHeader(PayConstants.WECHAT_PAY_SERIAL);
        // TODO: 2021/11/3 示例：通过证书序列号查询
        WechatOpen wechatOpen = new WechatOpen();
        if (!SignUtil.verifiedSign(request, body, wechatOpen.getMchId(), wechatOpen.getSerialNumber(),
                wechatOpen.getPrivateKeyUrl(), wechatOpen.getApiKey())) {
            return PayConstants.WECHAT_FAILED_RETURN;
        }
        JSONObject bodyJo = JsonUtil.parseObject(body);
        String resourceType = bodyJo.getString("resource_type");
        String eventType = bodyJo.getString("event_type");
        if (!PayConstants.RESOURCE_TYPE.equals(resourceType) || !PayConstants.EVENT_TYPE.equals(eventType)) {
            return PayConstants.WECHAT_FAILED_RETURN;
        }
        JSONObject encrypt = bodyJo.getJSONObject("resource");
        String algorithm = encrypt.getString("algorithm");
        String ciphertext = encrypt.getString("ciphertext");
        String associatedData = encrypt.getString("associated_data");
        String originalType = encrypt.getString("original_type");
        String nonce = encrypt.getString("nonce");
        String decrypt = CertificateUtil.decryptResBody(wechatOpen.getApiKey(), associatedData, nonce, ciphertext);
        JSONObject resource = JsonUtil.parseObject(decrypt);
        //业务逻辑
        int num = 0;
        // TODO: 2021/11/4 向数据库中加入支付记录
        return num > 0 ? PayConstants.WECHAT_SUCCESS_RETURN : PayConstants.WECHAT_FAILED_RETURN;
    }

}
