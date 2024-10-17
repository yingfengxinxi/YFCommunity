package com.wr.remote.estate.manage.meter;

import java.io.Serializable;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-24 16:03:16
 * @Desc: 抄表设置Vo
 */
public class MeterRuleVo implements Serializable {

    private static final long serialVersionUID = -6218788275218608361L;

    /** 主键*/
    private Long ruleId;

    /** 所属物业id，表xccj_community.bus_estate*/
    private Long estateId;

    /** 水表抄表日期*/
    private Integer waterDay;

    /** 电表抄表日期*/
    private Integer eleDay;

    /** 燃气表抄表日期*/
    private Integer gasDay;

    /** 能耗推送：0=否，1=是*/
    private String meterPush;

    /**
     * 能耗推送日期
     *
     */
    private Integer pushDay;

    /** 创建人*/
    private String createBy;

    private String updateBy;

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public Integer getWaterDay() {
        return waterDay;
    }

    public void setWaterDay(Integer waterDay) {
        this.waterDay = waterDay;
    }

    public Integer getEleDay() {
        return eleDay;
    }

    public void setEleDay(Integer eleDay) {
        this.eleDay = eleDay;
    }

    public Integer getGasDay() {
        return gasDay;
    }

    public void setGasDay(Integer gasDay) {
        this.gasDay = gasDay;
    }

    public String getMeterPush() {
        return meterPush;
    }

    public void setMeterPush(String meterPush) {
        this.meterPush = meterPush;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getPushDay() {
        return pushDay;
    }

    public void setPushDay(Integer pushDay) {
        this.pushDay = pushDay;
    }

    @Override
    public String toString() {
        return "MeterRuleVo{" +
                "ruleId=" + ruleId +
                ", estateId=" + estateId +
                ", waterDay=" + waterDay +
                ", eleDay=" + eleDay +
                ", gasDay=" + gasDay +
                ", meterPush='" + meterPush + '\'' +
                ", pushDay=" + pushDay +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}
