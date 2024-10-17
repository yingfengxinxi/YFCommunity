package com.wr.common.payment.constants;

/**
 * @Author RainCity
 * @Date 2022-11-11 12:30:16
 * @Desc 支付宝常量
 */
public class AliConstants {
    /**
     * 支付宝网关
     * 沙箱：https://openapi.alipaydev.com/gateway.do
     * 正式：https://openapi.alipay.com/gateway.do
     */
    public static final String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

    /**RSA2*/
    public static final String SIGN_TYPE = "RSA2";

    /**订单号*/
    public static final String OUT_TRADE_NO = "out_trade_no";
    /**订单总金额*/
    public static final String TOTAL_AMOUNT = "total_amount";
    /**订单标题*/
    public static final String SUBJECT = "subject";
    /**销售产品码，商家和支付宝签约的产品码*/
    public static final String PRODUCT_CODE = "product_code";
    /**绝对超时时间，格式为yyyy-MM-dd HH:mm:ss*/
    public static final String TIME_EXPIRE = "time_expire";
    /**公用回传参数*/
    public static final String PASS_BACK_PARAMS = "passback_params";

    /**
     * 交易创建，等待买家付款
     */
    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 交易支付成功
     */
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    /**
     * 交易结束，不可退款
     */
    public static final String TRADE_FINISHED = "TRADE_FINISHED";
}
