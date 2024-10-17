package com.wr.remote.estate.center.vo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-14 17:20:53
 * @Desc:
 */
public class GraphVO {

    /**
     *  楼栋图形数据
     */
    private List<BuildingGraphVO> buildingGraphList;

    /**
     * 查询所有房间号
     */
    private List<String> roomNumbers;

    private Map<Integer, Long> roomStatusStatistics;

    public List<BuildingGraphVO> getBuildingGraphList() {
        return buildingGraphList;
    }

    public void setBuildingGraphList(List<BuildingGraphVO> buildingGraphList) {
        this.buildingGraphList = buildingGraphList;
    }

    public List<String> getRoomNumbers() {
        return roomNumbers;
    }

    public void setRoomNumbers(List<String> roomNumbers) {
        this.roomNumbers = roomNumbers;
    }

    public Map<Integer, Long> getRoomStatusStatistics() {
        return roomStatusStatistics;
    }

    public void setRoomStatusStatistics(Map<Integer, Long> roomStatusStatistics) {
        this.roomStatusStatistics = roomStatusStatistics;
    }

    @Override
    public String toString() {
        return "GraphVO{" +
                "buildingGraphList=" + buildingGraphList +
                ", roomNumbers=" + roomNumbers +
                ", roomStatusTotal=" + roomStatusStatistics +
                '}';
    }
}
