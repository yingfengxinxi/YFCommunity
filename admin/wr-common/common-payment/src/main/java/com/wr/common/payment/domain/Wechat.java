package com.wr.common.payment.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-11 15:11:11
 * @Desc: 微信
 */
@Data
public class Wechat implements Serializable {
    private static final long serialVersionUID = -1231031879443581483L;

    /**应用ID*/
    private String appId;
    /**appSecret*/
    private String appSecret;

}
