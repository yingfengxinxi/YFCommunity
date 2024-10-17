package com.wr.common.customize.constant;

/**
 * @Author: RainCity
 * @Date: 2021-09-24 15:16:56
 * @Desc: 云+社保卡开门 常量
 */
public class CloudConstants {

    /** 社保卡成功返回*/
    public static final String RESPONSE_SUCCESS = "DATA={\"AcsRes\":\"1\",\"ActIndex\":\"0\",\"Time\":\"1\",\"Voice\":\"%1$s\",\"Note\":\"%1$s\"}";
    /** 社保卡失败返回*/
    public static final String RESPONSE_FAILED = "DATA={\"AcsRes\":\"0\",\"ActIndex\":\"0\",\"Time\":\"1\",\"Voice\":\"%1$s\",\"Note\":\"%1$s\"}";

    /** 数字字母*/
    public static final String HEX_STR = "0123456789ABCDEF";
    /** 社保卡卡数据--身份证标识位*/
    public static final String ID_CARD = "0812";
    /** 社保卡卡数据--身姓名标识位*/
    public static final String USER_NAME = "0932";
    /** 社保卡*/
    public static final String SOCIAL_CARD = "02";
    /** 社保卡电子卡*/
    public static final String SOCIAL_ELC_CARD = "04";
    /** 电子社保卡请求地址*/
    public static final String ELC_CARD_URL = "http://220.179.232.162:8081/mixcode/";
}
