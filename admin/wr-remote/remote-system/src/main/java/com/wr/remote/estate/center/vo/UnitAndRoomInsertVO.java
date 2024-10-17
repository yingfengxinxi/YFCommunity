package com.wr.remote.estate.center.vo;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-12 11:21:50
 * @Desc:
 */
public class UnitAndRoomInsertVO {

    /**
     * 楼栋id
     */
    private Long buildingId;

    /**
     * 起始单元
     */
    private Integer startUnitNum;

    /**
     * 结尾单元
     */
    private Integer endUnitNum;

    /**
     * 排除单元
     */
    private List<Integer> excludeUnitNums;

    /**
     * 楼层数
     */
    private Integer floorsNum;

    /**
     * 每层户数
     */
    private Integer roomNum;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getStartUnitNum() {
        return startUnitNum;
    }

    public void setStartUnitNum(Integer startUnitNum) {
        this.startUnitNum = startUnitNum;
    }

    public Integer getEndUnitNum() {
        return endUnitNum;
    }

    public void setEndUnitNum(Integer endUnitNum) {
        this.endUnitNum = endUnitNum;
    }

    public List<Integer> getExcludeUnitNums() {
        return excludeUnitNums;
    }

    public void setExcludeUnitNums(List<Integer> excludeUnitNums) {
        this.excludeUnitNums = excludeUnitNums;
    }

    public Integer getFloorsNum() {
        return floorsNum;
    }

    public void setFloorsNum(Integer floorsNum) {
        this.floorsNum = floorsNum;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    @Override
    public String toString() {
        return "UnitAndRoomInsertVO{" +
                "buildingId=" + buildingId +
                ", startUnitNum=" + startUnitNum +
                ", endUnitNum=" + endUnitNum +
                ", excludeUnitNums=" + excludeUnitNums +
                ", floorsNum=" + floorsNum +
                ", roomNum=" + roomNum +
                '}';
    }
}
