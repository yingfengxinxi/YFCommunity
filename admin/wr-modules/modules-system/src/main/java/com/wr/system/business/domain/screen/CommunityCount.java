package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 14:52:52
 * @Desc: 社区人员结构
 */
public class CommunityCount {

    private static final long serialVersionUID = -5782957337168721196L;

    /**
     * 楼栋数量
     */
    private Integer buildingNum;

    /**
     * 业主户数
     */
    private Integer ownerNum;

    /**
     * 小区人员总数
     */
    private Integer peopleNum;

    /**
     * 停车位数量
     */
    private Integer carportNum;

    /**
     * 小区车辆总数
     */
    private Integer vehicleNum;

    public Integer getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
    }

    public Integer getOwnerNum() {
        return ownerNum;
    }

    public void setOwnerNum(Integer ownerNum) {
        this.ownerNum = ownerNum;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getCarportNum() {
        return carportNum;
    }

    public void setCarportNum(Integer carportNum) {
        this.carportNum = carportNum;
    }

    public Integer getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(Integer vehicleNum) {
        this.vehicleNum = vehicleNum;
    }
}
