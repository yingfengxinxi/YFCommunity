package com.wr.remote.estate.center.vo;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-12 11:21:50
 * @Desc:
 */
public class RoomInsertVO {

    /**
     * 楼栋id
     */
    private Long unitId;

    /**
     * 楼层数
     */
    private Integer floorsNum;

    /**
     * 每层户数
     */
    private Integer roomNum;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
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
        return "RoomInsertVO{" +
                "unitId=" + unitId +
                ", floorsNum=" + floorsNum +
                ", roomNum=" + roomNum +
                '}';
    }
}
