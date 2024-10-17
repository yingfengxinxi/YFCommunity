package com.wr.remote.estate.assess;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 晁盖
 * @Date: 2022-11-14 14:31:34
 * @Desc: 考核通用方法-考核项目关联表
 */
public class AssessItem implements Serializable {

    private static final long serialVersionUID = -6069023089060012112L;

    /**
     * 方法id，表xccj_office.bus_assess_method
     */
    private Long methodId;
    /**
     * 项目id，表xccj_office.bus_standard
     */
    private Long standardId;
    /**
     * 得分占比
     */
    private BigDecimal scoreRate;
    /**
     * 项目名称
     */
    private String standardTitle;

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long methodId) {
        this.methodId = methodId;
    }

    public Long getStandardId() {
        return standardId;
    }

    public void setStandardId(Long standardId) {
        this.standardId = standardId;
    }

    public BigDecimal getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(BigDecimal scoreRate) {
        this.scoreRate = scoreRate;
    }

    public String getStandardTitle() {
        return standardTitle;
    }

    public void setStandardTitle(String standardTitle) {
        this.standardTitle = standardTitle;
    }
}
