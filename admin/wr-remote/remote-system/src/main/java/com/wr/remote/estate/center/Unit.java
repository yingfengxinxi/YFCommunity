package com.wr.remote.estate.center;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 19:55:38
 * @Desc: 单元
 */
public class Unit extends BaseEntity {
    /**
     * 主键
     */
    private Long unitId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;
    /**
     * 单元名
     */
    private String unitName;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "unitId=" + unitId +
                ", communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", unitName='" + unitName + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
