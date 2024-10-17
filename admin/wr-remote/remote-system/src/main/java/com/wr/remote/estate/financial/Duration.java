package com.wr.remote.estate.financial;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: RainCity
 * @Date: 2022-11-08 10:31:54
 * @Desc: 停车缴费时长
 */
@Data
public class Duration implements Serializable {
    private static final long serialVersionUID = 5370649603166118185L;

    private Long durationId;

    private Long communityId;

    private Integer minHour;

    private Integer maxHour;

    private BigDecimal hourFee;
}
