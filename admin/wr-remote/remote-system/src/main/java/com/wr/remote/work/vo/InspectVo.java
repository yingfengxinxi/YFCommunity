package com.wr.remote.work.vo;

import com.wr.remote.work.inspect.Inspect;

/**
 * 巡检工单扩展类
 * @Author: lvzy
 * @Date: 2022-10-27 09:59:00
 * @Desc: 巡检工单扩展类
 */
public class InspectVo extends Inspect {

    private static final long serialVersionUID = 2557226221826496593L;

    /**
     * 巡检类型名称
     */
    private String tourName;

    /**
     * 执行人姓名
     */
    private String staffName;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 设备名称
     */
    private String facilityName;

    /**
     * 设备唯一标识
     */
    private String indexCode;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 门禁设备名称
     */
    private String deviceTypeName;

    /**
     * 设备地址
     */
    private String addressName;

    /**
     * 设备编号
     */
    private String facilityNo;

    /**
     * 设备型号
     */
    private String facilityType;

    /**
     * 执行人手机号
     */
    private String staffPhone;

    /**
     * 执行人身份证号
     */
    private String cardNo;

    /**
     * 主要负责人
     */
    private String mainStaffName;

    /**
     * 人员类别
     */
    private String personnelType;

    /**
     * 执行人姓名
     */
    private String userName;

    /**
     * 分配设置表ID
     */
    private Long allotId;

    /**
     * 订单状态: 1待分配，2待接单，3待执行，4转交中，5执行中，6已完成，7超时未完成，8超时完成
     */
    private String orderStatus;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getFacilityNo() {
        return facilityNo;
    }

    public void setFacilityNo(String facilityNo) {
        this.facilityNo = facilityNo;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMainStaffName() {
        return mainStaffName;
    }

    public void setMainStaffName(String mainStaffName) {
        this.mainStaffName = mainStaffName;
    }

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAllotId() {
        return allotId;
    }

    public void setAllotId(Long allotId) {
        this.allotId = allotId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "InspectVo{" +
                "tourName='" + tourName + '\'' +
                ", staffName='" + staffName + '\'' +
                ", planName='" + planName + '\'' +
                ", facilityName='" + facilityName + '\'' +
                ", indexCode='" + indexCode + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", addressName='" + addressName + '\'' +
                ", facilityNo='" + facilityNo + '\'' +
                ", facilityType='" + facilityType + '\'' +
                ", staffPhone='" + staffPhone + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", mainStaffName='" + mainStaffName + '\'' +
                ", personnelType='" + personnelType + '\'' +
                ", userName='" + userName + '\'' +
                ", allotId=" + allotId +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
