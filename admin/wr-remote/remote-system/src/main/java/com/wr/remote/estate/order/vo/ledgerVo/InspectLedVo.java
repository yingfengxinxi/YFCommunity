package com.wr.remote.estate.order.vo.ledgerVo;

import com.wr.remote.work.inspect.Inspect;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 13:59:26
 * @Desc: 巡检台账vo
 */
public class InspectLedVo extends Inspect {

    private static final long serialVersionUID = -316398308385370712L;

    /**
     * 巡检类型名称
     */
    private String tourName;

    /**
     * 分配方式名称
     */
    private String allotWayName;

    /**
     * 执行人姓名
     */
    private String executeName;

    /**
     * 设备类型名称
     */
    private String deviceTypeName;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 唯一标识符
     */
    private String indexCode;

    /**
     * 设备所在地址
     */
    private String addressName;

    /**
     * 巡检模式名称
     */
    private String planModelName;

    /**
     * 人员姓名 (执行人)
     */
    private String staffName;

    /**
     * 手机号 (执行人)
     */
    private String staffPhone;

    /**
     * 身份证号 (执行人)
     */
    private String cardNo;

    /**
     * 人员类别 (执行人)
     */
    private String workPost;

    /*是否主要负责人*/
    private String whether;

    /*异常状态*/
    private String status;
    /*异常描述*/
    private String abnormalInfo;

    /**
     * 设备编号
     */
    private String facilityNo;

    /**
     * 设备型号
     */
    private String facilityType;

    /**
     * 设备名称
     */
    private String facilityName;

    /**
     * 工单编号
     */
    private String inspectNo;

    /**
     * 工单名称
     */
    private String inspectName;

    /**
     * 工单来源：0=计划生成，1=后台添加，2=其他工单
     */
    private String dataSource;
    /*异常状态*/
    private String inspectResult;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    public String getWhether() {
        return whether;
    }

    public void setWhether(String whether) {
        this.whether = whether;
    }

    public String getInspectResult() {
        return inspectResult;
    }

    public void setInspectResult(String inspectResult) {
        this.inspectResult = inspectResult;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getAllotWayName() {
        return allotWayName;
    }

    public void setAllotWayName(String allotWayName) {
        this.allotWayName = allotWayName;
    }

    public String getExecuteName() {
        return executeName;
    }

    public void setExecuteName(String executeName) {
        this.executeName = executeName;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPlanModelName() {
        return planModelName;
    }

    public void setPlanModelName(String planModelName) {
        this.planModelName = planModelName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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

    public String getAbnormalInfo() {
        return abnormalInfo;
    }

    public void setAbnormalInfo(String abnormalInfo) {
        this.abnormalInfo = abnormalInfo;
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

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @Override
    public String getInspectNo() {
        return inspectNo;
    }

    @Override
    public void setInspectNo(String inspectNo) {
        this.inspectNo = inspectNo;
    }

    @Override
    public String getInspectName() {
        return inspectName;
    }

    @Override
    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    @Override
    public String getDataSource() {
        return dataSource;
    }

    @Override
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
