package com.wr.common.customize.constant;

/**
 * @Author: RainCity
 * @Date: 2022-06-01 14:54:57
 * @Desc: api 相关常量
 */
public class ApiConstants {
    public static final String LOGIN_KEY = "API_LOGIN:";

    /** 每天可获取短信的次数 */
    public static final Integer MAX_SMS_COUNT = 15;
    /** 当天已经获取短信的次数 key */
    public static final String TIMES_KEY = "times:";
    /** redis 短信 key */
    public static final String SMS_KEY = "sms:";

    /** 验证码失效时间(分钟) */
    public static final Long EXPIRATION =5L;

    public static final String CHARSET = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";

}
