package com.wr.remote.estate.financial.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 按月统计支出
 */
@Data
public class MonthStatisticDTO {

    private Integer month;

    private BigDecimal amount;

}
