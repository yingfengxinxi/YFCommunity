package com.wr.remote.govern.home;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: lusu
 * @Date: 2022-12-03 11:36:29
 * @Desc: bus_committee
 */
@Data
public class Committee implements Serializable {
    private static final long serialVersionUID = -1140647287542792251L;


    /**
     * 社区名称
     */
    private String CommunityName;

    /**
     *委员会数量
     */
    private BigDecimal committeeNum;



}
