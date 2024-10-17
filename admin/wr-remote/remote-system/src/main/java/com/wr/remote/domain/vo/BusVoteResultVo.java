package com.wr.remote.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/2/24 13:45
 */
@Data
public class BusVoteResultVo {

    private String name;
    private Integer number;
    private BigDecimal percentage;
    private String ownerStatus;

}
