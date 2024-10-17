package com.wr.remote.govern.home;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: lusu
 * @Date: 2022-12-03 13:41:42
 * @Desc: 党费缴费记录
 */
@Data
public class HomePartyPay implements Serializable {


    private static final long serialVersionUID = 1586157571793538888L;

    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 正常缴费
     */
    private BigDecimal normalAmount;

    /**
     * 预缴费
     */
    private BigDecimal aheadAmount;

    /**
     * 补缴费
     */
    private BigDecimal backAmount;

    /**
     * 线下缴费
     */
    private BigDecimal offlineAmount;
}
