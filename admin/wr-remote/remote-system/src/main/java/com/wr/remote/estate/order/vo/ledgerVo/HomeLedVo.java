package com.wr.remote.estate.order.vo.ledgerVo;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.estate.order.ledger.HomeOrder;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 09:30:47
 * @Desc: 居家台账vo
 */
public class HomeLedVo extends HomeOrder {

    private static final long serialVersionUID = 3883313391635554156L;
    /**
     * 员工姓名
     */
    private String staffName;
    /**
     * 账号/手机号
     */
    private String staffPhone;
    /**
     * 身份证号码
     */
    private String cardNo;
    /**
     * 岗位：0=安防人员,1=维修人员,2=装修人员,3=场馆人员,4=保洁人员,5=志愿者
     */
    private String workPost;
    /**
     * 是否主要负责人
     */
    private String whether;
    /**
     * 异常id，表xccj_work.bus_abnormal
     */
    private Long abnormalId;
    /**
     * 异常描述，表xccj_work.bus_abnormal
     */
    private String abnormalInfo;
    /**
     * 异常状态，表xccj_work.bus_abnormal
     */
    private String status;
    /**
     * 户主姓名
     */
    private String ownerName;
    /**
     * 维修类型名称
     */
    private String repairName;
    /**
     * 工单图片地址
     */
    private String orderImageUrl;

    public String getOrderImageUrl() {
        return orderImageUrl;
    }

    public void setOrderImageUrl(String orderImageUrl) {
        this.orderImageUrl = orderImageUrl;
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

    public Long getAbnormalId() {
        return abnormalId;
    }

    public void setAbnormalId(Long abnormalId) {
        this.abnormalId = abnormalId;
    }

    public String getAbnormalInfo() {
        return abnormalInfo;
    }

    public void setAbnormalInfo(String abnormalInfo) {
        this.abnormalInfo = abnormalInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }
}
