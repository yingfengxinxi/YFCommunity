package com.wr.api.payment.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-11-22 16:50:58
 * @Desc:
 */
@Data
public class QueryDTO implements Serializable {
    private static final long serialVersionUID = 8252863313710857689L;

    /** 表 xccj_community.bus_wechat*/
    private Long wechatId;
    private Long communityId;
    private String outTradeNo;
}
