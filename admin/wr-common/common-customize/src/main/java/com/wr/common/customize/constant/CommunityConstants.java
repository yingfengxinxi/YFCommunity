package com.wr.common.customize.constant;

/**
 * 小区 Url 常量
 *
 * @Author RainCity
 * @Date 2022-11-17 11:16:24
 */
public class CommunityConstants {

    /**
     * 网关地址
     * 测试地址：172.16.205.34
     * 正式服：172.16.205.111
     */
    public static final String GATEWAY_URL = "http://172.16.205.32:9100";

    /**
     * 停车缴费 页面服务
     * 正式服IP：172.16.205.117
     * 正式服域名：
     */
    public static final String PARK_FEE_SERVER = "http://172.16.205.117";

    /**
     * 业主移动端--H5 页面服务
     */
    public static final String OWNER_FEE_SERVER = "http://172.16.205.116";

    /**
     * 物业移动端--小程序 页面服务
     */
    //public static final String ESTATE_SERVER_JSAPI = "http://propertypmd.szcjzhxq.com";

    /**
     * 物业移动端 -- APP 页面服务
     */
    //public static final String ESTATE_SERVER_APP = "http://estate.app.community.szcjzhxq.com/api/estate";

    /**
     * 物联网预警回调地址
     */
    //public static final String AIIOT_SERVER_APP = "http://estate.community.szcjzhxq.com/ai_iot";
    // public static final String AIIOT_SERVER_APP = "http://172.16.205.35:8085/prod-api/ai_iot";

    /**
     * 业主端 -- APP 页面服务
     */
    //public static final String OWNER_SERVER_APP = "http://owner.app.community.szcjzhxq.com/api/owner";

    /**
     * 物业办API接口
     * 宣城测试服：172.16.205.39
     * 宣城正式服：172.16.205.39
     */
//    public static final String OFFICE_SERVER_URL = "http://172.16.205.119:9726/api/v1";
    public static final String OFFICE_SERVER_URL = "http://127.0.0.1:9726/api/v1";
}
