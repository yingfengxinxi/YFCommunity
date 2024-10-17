package com.wr.remote.estate.car;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2022/10/13 9:50
 * @Version 1.5
 */
public class Car extends BaseEntity {

    /**
     * 主键
     */
    private Long logId;

    /**
     * 用户类型：0=业主，1=租客，2=临时车
     */
    private String userType;

    /**
     * 车主姓名，user_type=2时，可能为空
     */
    private String userName;

    /**
     * 手机号，user_type=2时，可能为空
     */
    private String userPhone;

    /**
     * 身份证号，user_type=2时，可能为空
     */
    private String idCardNo;

    /**
     * 车牌号
     */
    private String plateNo;

    /**
     * 车牌类型:0=标准民用车/军车，1=02式民用车牌，2=武警车车牌，3=警车车牌，4=民用车双行尾牌，5=使馆车牌，6=农用车车牌，7=摩托车，8=新能源车，255=其它车牌
     */
    private Integer plateType;

    /**
     * 车牌颜色:0=蓝色，1=黄色，2=白色，3=黑色，4=绿色，5=民航黑色，255=其他颜色
     */
    private Integer plateColor;

    /**
     * 车辆类型:0=其他车，1=小型车，2=大型车，3=摩托车
     */
    private Integer vehicleType;

    /**
     * 车辆颜色:0=其他颜色，1=白色，2=银色，3=灰色，4=黑色，5=红色，6=深蓝色，7=蓝色，8=黄色，9=绿色，10=棕色，11=粉色，12=紫色，13=深灰，14=杏色，255=未识别的车辆颜色
     */
    private Integer vehicleColor;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 车库类型：0=车库，1=大门
     */
    private String parkType;

    /**
     * 停车库名称
     */
    private String parkName;

    /**
     * 出入口名称
     */
    private String gateName;

    /**
     * 车道名称
     */
    private String roadwayName;

    /**
     * 进出场类型，0：进场，1：出场
     */
    private int inoutType;

    /**
     * 进出场发生时间
     */
    private Date inoutTime;

    /**
     * 出入口编号
     */
    private String gateIndex;

    /**
     * 车库编号
     */
    private String parkIndex;

    /**
     * 进出场开门时间
     */
    private Date inoutOpen;

    /**
     * 支付状态：0=未支付，1=已付
     */
    private String payStatus;

    private String tableName;

    private Date month;

    public Car() {
    }


    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Integer getPlateType() {
        return plateType;
    }

    public void setPlateType(Integer plateType) {
        this.plateType = plateType;
    }

    public Integer getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Integer plateColor) {
        this.plateColor = plateColor;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(Integer vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getParkType() {
        return parkType;
    }

    public void setParkType(String parkType) {
        this.parkType = parkType;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public String getRoadwayName() {
        return roadwayName;
    }

    public void setRoadwayName(String roadwayName) {
        this.roadwayName = roadwayName;
    }

    public int getInoutType() {
        return inoutType;
    }

    public void setInoutType(int inoutType) {
        this.inoutType = inoutType;
    }

    public Date getInoutTime() {
        return inoutTime;
    }

    public void setInoutTime(Date inoutTime) {
        this.inoutTime = inoutTime;
    }

    public Date getInoutOpen() {
        return inoutOpen;
    }

    public void setInoutOpen(Date inoutOpen) {
        this.inoutOpen = inoutOpen;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getGateIndex() {
        return gateIndex;
    }

    public void setGateIndex(String gateIndex) {
        this.gateIndex = gateIndex;
    }

    public String getParkIndex() {
        return parkIndex;
    }

    public void setParkIndex(String parkIndex) {
        this.parkIndex = parkIndex;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
