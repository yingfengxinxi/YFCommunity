package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 14:52:52
 * @Desc: 字典数据
 */
public class MeterReadingWorkVo {

    private static final long serialVersionUID = -5782957337168721196L;

    /**
     * 能耗类型0水表，1电表
     */
    private String energyConsumptionType;

    /**
     * 抄表类型 0消防用水,1办公用水,2绿化用水,3人防用水,4保洁用水,5公共能耗,6办公用电,7充电桩
     */
    private String meterReadingType;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 年份
     */
    private Long communityId;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getEnergyConsumptionType() {
        return energyConsumptionType;
    }

    public void setEnergyConsumptionType(String energyConsumptionType) {
        this.energyConsumptionType = energyConsumptionType;
    }

    public String getMeterReadingType() {
        return meterReadingType;
    }

    public void setMeterReadingType(String meterReadingType) {
        this.meterReadingType = meterReadingType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
