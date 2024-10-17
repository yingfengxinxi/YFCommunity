package com.wr.common.payment.constants;

/**
 * @Author: RainCity
 * @Date: 2021-11-02 11:14:11
 * @Desc: 微信支付常量类
 */
public class WechatConstants {
    /** 公众号获取code */
    public static final String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=zhxq#wechat_redirect";
    /** 公众号获取 access_token/openId */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /** 小程序获取openId */
    public static final String JS_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    /** 获取token */
    public static final String PHONE_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /** 获取手机号 */
    public static final String PHONE_NUMBER_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";
    /**微信获取平台证书列表地址*/
    public static final String CERTIFICATES_URL = "https://api.mch.weixin.qq.com/v3/certificates";
    public static final String CERT_ABSOLUTE_URL = "/v3/certificates";
    /** SHA256withRSA 签名算法*/
    public static final String SHA256_WITH_RSA = "SHA256withRSA";

    public static final String SCHEMA = "WECHATPAY2-SHA256-RSA2048";

    /** JSAPI下单 请求路径*/
    public static final String JSAPI_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    /** APP下单 请求路径*/
    public static final String APP_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";
    /** H5下单 请求路径*/
    public static final String H5_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/h5";
    /** Native下单 请求路径*/
    public static final String NATIVE_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";
    /** 查询订单 请求路径*/
    public static final String QUERY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/OUTTRADENO?mchid=MERCHANTID";

    public static final String DOMAIN_API = "https://api.mch.weixin.qq.com/v3";

    //微信支付订单号查询
    public static final String SELECT_PAY_TRANSACTIONS_JSAPI = "/pay/transactions/out-trade-no/{}";
    //关闭订单
    public static final String PAY_TRANSACTIONS_OUT_TRADE_NO = "/pay/transactions/out-trade-no/{}/close";
    /** 关闭订单 请求路径*/
    public static final String CLOSE_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/OUTTRADENO/close";

    /**绝对超时时间，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE*/
    public static final String TIME_EXPIRE = "time_expire";
}
