package com.wr.common.customize.constant;

/**
 * @Author: RainCity
 * @Date: 2021-07-19 11:19:25
 * @Desc: 安康码相关常量
 */
public class HealthyConstants {
    /**地市编码*/
    public static final String CITY_CODE = "341800000000";
    /**核验站点ID*/
    public static final String SITE_ID = "C8B0F311A5554EB19A72D067B2E88E19";
    /**核验点检查人员姓名或核验点设备名称*/
    public static final String CHECK_USR_NAME = "宣城测试点";
    /**安康码模式*/
    public static final Integer MODE_HEALTHY = 1;
    /**人脸识别模式*/
    public static final Integer MODE_FACE = 2;
    /**健康码未申领*/
    public static final String HEALTH_MSG_NOT_CLAIMED = "健康码未申领";
    /**绿码提示语*/
    public static final String HEALTH_MSG_GREEN = "绿码请通行";
    /**黄码提示语*/
    public static final String HEALTH_MSG_YELLOW = "黄码请复核";
    /**红码提示语*/
    public static final String HEALTH_MSG_RED = "红码禁止通行";
    /**灰码提示语*/
    public static final String HEALTH_MSG_GRAY = "灰码请复核";
    /**温度异常提示语*/
    public static final String HEALTH_MSG_TEMP = "温度异常";
    /**安康码url前缀*/
    public static final String HEALTH_CODE_URL = "https://akm.ahzwfw.gov.cn/akm-sj-mgr/index.html#/myAkm";

}
