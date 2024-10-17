package com.wr.api.estate.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 14:58:20
 * @Desc:
 */
@Data
public class PayDTO {

    private Long communityId;

    private Long wechatId;

    private Long alipayId;


    private BigDecimal totalAmount;
     /**
     * 场馆预约 id
     */
    private Long appointId;
}
