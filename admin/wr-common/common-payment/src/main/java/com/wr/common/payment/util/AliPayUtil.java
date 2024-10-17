package com.wr.common.payment.util;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.wr.common.payment.constants.AliConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.domain.dto.AliPayDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 15:17:50
 * @Desc: 支付宝支付工具类
 */
public class AliPayUtil {
    private static final Logger log = LoggerFactory.getLogger(AliPayUtil.class);

    private AliPayUtil() {
    }

    /**
     * 扫码支付
     * @param aliPay 请求参数
     * @see AliPay#toString()
     * @return 收款二维码
     * @throws AlipayApiException 调用异常
     */
    public static AlipayTradePrecreateResponse tradePreCreate(AliPay aliPay) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPay.getAppId(), aliPay.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPay.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        //设置回调地址
        request.setNotifyUrl(aliPay.getNotifyUrl());

        //请求参数的集合
        JSONObject bizContent = new JSONObject();
        //商户订单号
        bizContent.put(AliConstants.OUT_TRADE_NO,aliPay.getOutTradeNo());
        //订单总金额，单位为元，精确到小数点后两位
        bizContent.put(AliConstants.TOTAL_AMOUNT,aliPay.getTotalAmount());
        //订单标题
        bizContent.put(AliConstants.SUBJECT,aliPay.getSubject());
        // 公用回传参数，按需传入
        bizContent.put(AliConstants.PASS_BACK_PARAMS, aliPay.getAttach());

        request.setBizContent(bizContent.toString());
        AlipayTradePrecreateResponse response = alipayClient.execute (request);
        log.info("response:{}",response);
        return response;
    }

    /**
     * 付款码支付接口
     * @param aliPay 请求参数
     * @see AliPay#toString()
     * @return 支付结果
     * @throws AlipayApiException
     */
    public static AlipayTradePayResponse tradePayBarCode(AliPay aliPay) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPay.getAppId(), aliPay.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPay.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        //设置回调地址
        request.setNotifyUrl(aliPay.getNotifyUrl());

        JSONObject bizContent = new JSONObject();
        //商户订单号
        bizContent.put(AliConstants.OUT_TRADE_NO,aliPay.getOutTradeNo());
        //订单总金额，单位为元，精确到小数点后两位
        bizContent.put(AliConstants.TOTAL_AMOUNT,aliPay.getTotalAmount());
        //订单标题
        bizContent.put(AliConstants.SUBJECT,aliPay.getSubject());
        //支付场景 bar_code：当面付条码支付场景; security_code：当面付刷脸支付场景，对应的auth_code为fp开头的刷脸标识串；
        bizContent.put("scene", "bar_code");
        //支付授权码 当面付场景传买家的付款码
        bizContent.put("auth_code", aliPay.getAuthCode());
        // 公用回传参数，按需传入
        bizContent.put(AliConstants.PASS_BACK_PARAMS, aliPay.getAttach());

        request.setBizContent(bizContent.toString());
        AlipayTradePayResponse response = alipayClient.execute(request);
        log.info("TradePayResponse:{}",response);
        return  response;
    }

    /**
     * APP支付
     * @param aliPay 请求参数
     * @see AliPay#toString()
     * @return 表单格式，可嵌入页面，具体以返回的结果为准
     * @throws AlipayApiException 调用异常
     */
    public static AlipayTradeAppPayResponse tradeAppPay(AliPay aliPay) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPay.getAppId(), aliPay.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPay.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //设置回调地址
        request.setNotifyUrl(aliPay.getNotifyUrl());

        JSONObject bizContent = new JSONObject();
        //商户网站唯一订单号
        bizContent.put(AliConstants.OUT_TRADE_NO, aliPay.getOutTradeNo());
        //订单总金额，单位为元
        bizContent.put(AliConstants.TOTAL_AMOUNT,aliPay.getTotalAmount());
        //订单标题
        bizContent.put(AliConstants.SUBJECT,aliPay.getSubject());
        //销售产品码，商家和支付宝签约的产品码
        bizContent.put(AliConstants.PRODUCT_CODE, "QUICK_MSECURITY_PAY");
        //绝对超时时间，格式为yyyy-MM-dd HH:mm:ss
        if(StringUtils.isNotEmpty(aliPay.getTimeExpire())){
            bizContent.put(AliConstants.TIME_EXPIRE, aliPay.getTimeExpire());
        }
        // 公用回传参数，按需传入
        bizContent.put(AliConstants.PASS_BACK_PARAMS, aliPay.getAttach());

        request.setBizContent(bizContent.toString());
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        log.info("TradeAppPayResponse:{}",response);
        return  response;
    }

    /**
     * 手机网站支付
     * @param aliPay 请求参数
     * @see AliPay#toString()
     * @return 表单格式，可嵌入页面，具体以返回的结果为准
     * @throws AlipayApiException 调用异常
     */
    public static AlipayTradeWapPayResponse tradeWapPay(AliPay aliPay) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPay.getAppId(), aliPay.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPay.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //设置回调地址
        request.setNotifyUrl(aliPay.getNotifyUrl());
        //网页重定向通知 HTTP/HTTPS开头字符串
        request.setReturnUrl(aliPay.getReturnUrl());

        JSONObject bizContent = new JSONObject();
        //商户网站唯一订单号
        bizContent.put(AliConstants.OUT_TRADE_NO, aliPay.getOutTradeNo());
        //订单总金额，单位为元
        bizContent.put(AliConstants.TOTAL_AMOUNT,aliPay.getTotalAmount());
        //订单标题
        bizContent.put(AliConstants.SUBJECT,aliPay.getSubject());
        //销售产品码，商家和支付宝签约的产品码
        bizContent.put(AliConstants.PRODUCT_CODE, "QUICK_WAP_WAY");
        //绝对超时时间，格式为yyyy-MM-dd HH:mm:ss
        if(StringUtils.isNotEmpty(aliPay.getTimeExpire())){
            bizContent.put(AliConstants.TIME_EXPIRE, aliPay.getTimeExpire());
        }
        // 公用回传参数，按需传入
        bizContent.put(AliConstants.PASS_BACK_PARAMS, aliPay.getAttach());
        //用户付款中途退出返回商户网站的地址
        bizContent.put("quit_url", aliPay.getQuitUrl());

        request.setBizContent(bizContent.toString());
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
        log.info("TradeWapPayResponse:{}",response);
        return response;
    }

    /**
     * 电脑网站支付，PC场景下单并支付
     * @param aliPay 请求参数
     * @see AliPay#toString()
     * @return 表单格式，可嵌入页面，具体以返回的结果为准
     * @throws AlipayApiException 调用异常
     */
    public static AlipayTradePagePayResponse tradePagePay(AliPay aliPay) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPay.getAppId(), aliPay.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPay.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //设置回调地址
        request.setNotifyUrl(aliPay.getNotifyUrl());
        //网页重定向通知 HTTP/HTTPS开头字符串
        request.setReturnUrl(aliPay.getReturnUrl());

        JSONObject bizContent = new JSONObject();
        //商户网站唯一订单号
        bizContent.put(AliConstants.OUT_TRADE_NO, aliPay.getOutTradeNo());
        //订单总金额，单位为元
        bizContent.put(AliConstants.TOTAL_AMOUNT,aliPay.getTotalAmount());
        //订单标题
        bizContent.put(AliConstants.SUBJECT,aliPay.getSubject());
        //销售产品码，与支付宝签约的产品码名称
        bizContent.put(AliConstants.PRODUCT_CODE, "FAST_INSTANT_TRADE_PAY");
        //PC扫码支付的方式
        bizContent.put("qr_pay_mode", "2");
        //绝对超时时间，格式为yyyy-MM-dd HH:mm:ss
        if(StringUtils.isNotEmpty(aliPay.getTimeExpire())){
            bizContent.put(AliConstants.TIME_EXPIRE, aliPay.getTimeExpire());
        }
        // 公用回传参数，按需传入
        bizContent.put(AliConstants.PASS_BACK_PARAMS, aliPay.getAttach());

        request.setBizContent(bizContent.toString());

        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        log.info("TradePagePayResponse:{}",response);
        return response;
    }

    /**
     * 交易查询
     * @param aliPayDto 请求参数
     * @see  AliPayDTO#toString()
     * @return 查询状态码
     */
    public static String tradeQuery(AliPayDTO aliPayDto){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPayDto.getAppId(), aliPayDto.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPayDto.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        JSONObject bizContent = new JSONObject();
        //商户网站唯一订单号
        bizContent.put(AliConstants.OUT_TRADE_NO, aliPayDto.getOutTradeNo());

        request.setBizContent(bizContent.toString());

        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            log.info("TradeQueryResponse:{}",response);
            if(response.isSuccess()){
                return response.getTradeStatus();
            }
            return null;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("订单查询失败：errCode:{},errMsg:{}",e.getErrCode(),e.getErrMsg());
            return null;
        }
    }

    /**
     * 交易撤销
     * @param aliPayDto 请求参数
     * @see  AliPayDTO#toString()
     * @return retryFlag=是否需要重试(Y/N)
     * action=本次撤销触发的交易动作,接口调用成功且交易存在时返回
     *          可能的返回值：
     *          close：交易未支付，触发关闭交易动作，无退款；
     *          refund：交易已支付，触发交易退款动作；
     *          未返回：未查询到交易，或接口调用失败；
     */
    public static AlipayTradeCancelResponse tradeCancel(AliPayDTO aliPayDto){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPayDto.getAppId(), aliPayDto.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPayDto.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();

        JSONObject bizContent = new JSONObject();
        //商户网站唯一订单号
        bizContent.put(AliConstants.OUT_TRADE_NO, aliPayDto.getOutTradeNo());
        request.setBizContent(bizContent.toString());
        try {
            AlipayTradeCancelResponse response = alipayClient.execute(request);
            log.info("TradeCancelResponse:{}",response);
            return response;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("订单撤销失败：errCode:{},errMsg:{}",e.getErrCode(),e.getErrMsg());
            return null;
        }
    }

    /**
     * 交易关闭
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭
     * @param aliPayDto 请求参数
     * @see  AliPayDTO#toString()
     * @return 状态码
     */
    public static AlipayTradeCloseResponse tradeClose(AliPayDTO aliPayDto){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliConstants.GATEWAY_URL, aliPayDto.getAppId(), aliPayDto.getPrivateKey(),
                PayConstants.FORMAT_JSON, PayConstants.UTF_8, aliPayDto.getPublicKey(), AliConstants.SIGN_TYPE);
        //创建API对应的request类
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();

        JSONObject bizContent = new JSONObject();
        //商户网站唯一订单号
        bizContent.put(AliConstants.OUT_TRADE_NO, aliPayDto.getOutTradeNo());
        request.setBizContent(bizContent.toString());
        try {
            AlipayTradeCloseResponse response = alipayClient.execute(request);
            log.info("TradeCloseResponse:{}",response);
            return response;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("订单关闭失败：errCode:{},errMsg:{}",e.getErrCode(),e.getErrMsg());
            return null;
        }
    }

    /**
     * 获取支付宝POST过来反馈信息
     * @param request
     * @return
     */
    public static Map<String,String> getPostParams(HttpServletRequest request){
        Map<String,String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }
}
