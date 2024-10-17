package com.wr.api.owner.entity.home.advice;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 09:22:41
 * @Desc: 意见箱表
 */
public class Suggestion extends BaseEntity {
    private static final long serialVersionUID = -4070281975904065430L;
    /**
     * 主键
     */
    private Long suggestId;

    /**
     * 发布人账号id，表bus_account
     */
    private Long accountId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;

    /**
     * 单元id，表bus_unit
     */
    private Long unitId;

    /**
     * 当前所住房间id，表bus_room
     */
    private Long roomId;

    /**
     * 类型：见数据字典 sys_suggest_type
     */
    private String suggestType;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Long suggestId) {
        this.suggestId = suggestId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getSuggestType() {
        return suggestType;
    }

    public void setSuggestType(String suggestType) {
        this.suggestType = suggestType;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "suggestId=" + suggestId +
                ", accountId=" + accountId +
                ", communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", roomId=" + roomId +
                ", suggestType='" + suggestType + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
