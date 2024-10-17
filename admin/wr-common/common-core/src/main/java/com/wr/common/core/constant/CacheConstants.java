package com.wr.common.core.constant;

/**
 * 缓存的key 常量
 *
 * @author wr
 */
public class CacheConstants {

    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 720;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public final static long REFRESH_TIME = 120;

    /**
     * 密码最大错误次数
     */
    public final static int PASSWORD_MAX_RETRY_COUNT = 5;

    /**
     * 密码锁定时间，默认10（分钟）
     */
    public final static long PASSWORD_LOCK_TIME = 10;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
    /**
     * 巡查工单
     */
    public static final String XC_WORK_ORDER = "xc_work_order:";

    /**
     * 巡查工单到期
     */
    public static final String XC_WORK_ORDER_EXP = "xc_work_order_exp:";

    /**
     * 巡更工单
     */
    public static final String XG_WORK_ORDER = "xg_work_order:";

    /**
     * 巡更工单到期
     */
    public static final String XG_WORK_ORDER_EXP = "xg_work_order_exp:";

    /**
     * 巡检工单
     */
    public static final String XJ_WORK_ORDER = "xj_work_order:";
    /**
     * 巡检工单到期
     */
    public static final String XJ_WORK_ORDER_EXP = "xj_work_order_exp:";


    /**
     * 清洁工单
     */
    public static final String QJ_WORK_ORDER = "qj_work_order:";

    /**
     * 清洁工单到期
     */
    public static final String QJ_WORK_ORDER_EXP = "qj_work_order_exp:";

    /**
     * 抄表工单
     */
    public static final String CB_WORK_ORDER = "cb_work_order:";

    /**
     * 抄表工单到期
     */
    public static final String CB_WORK_ORDER_EXP = "cb_work_order_exp:";

    /**
     * 车位缴费管理
     */
    public static final String PARK_ORDER = "park_order:";
    /**
     * 业主端车位缴费管理
     */
    public static final String OWNER_ORDER = "owner_order:";

    /**
     * 调查问卷开始时间
     */
    public static final String QUEST_IONNAIRE = "questionnaire:";


    /**
     * 调查问卷结束时间
     */
    public static final String QUEST_IONNAIRE_EXP = "questionnaire_exp:";

    /**
     * 敏感词
     */
    public static final String SENSITIVE = "sensitive";

}
