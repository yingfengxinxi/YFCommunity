package com.wr.remote.estate.center.vo;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-12 11:21:50
 * @Desc:
 */
public class BuildingUpdateVO {

    /**
     * 楼宇id
     */
    private Long buildingId;

    /**
     * 楼宇名
     */
    private String buildingName;

    /**
     * 修改人
     */
    private String updateBy;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "BuildingUpdateVO{" +
                "buildingId=" + buildingId +
                ", buildingName='" + buildingName + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}
