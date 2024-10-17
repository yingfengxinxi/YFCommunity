package com.wr.remote.estate.center.vo;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-12 11:21:50
 * @Desc:
 */
public class BuildingInsertVO {

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 楼宇名
     */
    private String buildingName;

    /**
     * 起始楼宇号
     */
    private Integer startBuildingNum;

    /**
     * 结尾楼宇号
     */
    private Integer endBuildingNum;

    /**
     * 排除楼宇号
     */
    private List<Integer> excludeBuildingNums;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getStartBuildingNum() {
        return startBuildingNum;
    }

    public void setStartBuildingNum(Integer startBuildingNum) {
        this.startBuildingNum = startBuildingNum;
    }

    public Integer getEndBuildingNum() {
        return endBuildingNum;
    }

    public void setEndBuildingNum(Integer endBuildingNum) {
        this.endBuildingNum = endBuildingNum;
    }

    public List<Integer> getExcludeBuildingNums() {
        return excludeBuildingNums;
    }

    public void setExcludeBuildingNums(List<Integer> excludeBuildingNums) {
        this.excludeBuildingNums = excludeBuildingNums;
    }

    @Override
    public String toString() {
        return "BuildingInsertVO{" +
                "communityId=" + communityId +
                ", buildingName='" + buildingName + '\'' +
                ", startBuildingNum=" + startBuildingNum +
                ", endBuildingNum=" + endBuildingNum +
                ", excludeBuildingNums=" + excludeBuildingNums +
                '}';
    }
}
