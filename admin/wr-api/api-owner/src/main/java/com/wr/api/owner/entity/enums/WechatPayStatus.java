package com.wr.api.owner.entity.enums;

/**
 * @ClassName WechatPayStatus
 * @Description 微信支付状态码
 * @Author SUNk
 * @Date 2024/4/9 16:03
 * @ModifyDate 2024/4/9 16:03
 * @Version 1.0
 */
public enum WechatPayStatus {
    /**
     * 未支付状态，对应原代码中的"502"
     */
    NOT_PAID("502"),

    /**
     * 已支付状态，对应原代码中的"200"
     */
    PAID("200"),

    /**
     * 错误状态，用于处理其他非预期的响应状态码
     */
    ERROR("ERROR");

    private final String code;

    WechatPayStatus(String code) {
        this.code = code;
    }

    public static WechatPayStatus fromCode(String code) {
        for (WechatPayStatus status : values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        return ERROR; // 如果传入的code不在枚举值中，返回默认的ERROR状态
    }
}
