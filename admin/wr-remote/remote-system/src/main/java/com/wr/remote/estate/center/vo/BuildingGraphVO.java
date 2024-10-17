package com.wr.remote.estate.center.vo;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-14 15:03:08
 * @Desc:
 */
public class BuildingGraphVO {

    /**
     * 所有房间编号
     */
    private Integer floor;

    /**
     * 房间图形详情
     */
    private List<RoomDetailVO> roomDetailList;

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public List<RoomDetailVO> getRoomDetailList() {
        return roomDetailList;
    }

    public void setRoomDetailList(List<RoomDetailVO> roomDetailList) {
        this.roomDetailList = roomDetailList;
    }

    @Override
    public String toString() {
        return "RoomGraphVO{" +
                "floor=" + floor +
                ", roomDetailList=" + roomDetailList +
                '}';
    }
}
