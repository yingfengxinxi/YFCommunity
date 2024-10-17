package com.wr.remote.govern.home;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: lusu
 * @Date: 2022-12-03 10:37:10
 * @Desc: 线索反馈实体表
 */
@Data
public class Clue implements Serializable {

    private static final long serialVersionUID = 1861361398711119790L;
    /**
     * 小区名称
     */
    private String communityName;


    /**
     * 反馈次数
     */
    private BigDecimal clueNum;

    /**
     * 反馈总次数
     */
    private BigDecimal totalNum;

}
