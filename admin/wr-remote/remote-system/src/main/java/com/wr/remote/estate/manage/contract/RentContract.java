package com.wr.remote.estate.manage.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 租房合同表
 */
public class RentContract extends BaseEntity {

    private static final long serialVersionUID = 3916610432065009767L;
    /**
     * 主键
     */
    private Long contractId;
    /**
     * 租客id，表bus_tenant
     */
    private Long tenantId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;
    /**
     * 单元id，表bus_unit
     */
    private Long unitId;
    /**
     * 当前所住房间id，表bus_room
     */
    private Long roomId;
    /**
     * 业主id，表bus_owner（物业承租时为0）
     */
    private Long ownerId;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 月租金
     */
    private BigDecimal monthRent;
    /**
     * 合同开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractStart;
    /**
     * 合同结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractEnd;

    /**
     * 合同文件路径
     */
    private String contractUrl;


    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(BigDecimal monthRent) {
        this.monthRent = monthRent;
    }

    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "RentContract{" +
                "contractId=" + contractId +
                ", tenantId=" + tenantId +
                ", communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", roomId=" + roomId +
                ", ownerId=" + ownerId +
                ", contractNo='" + contractNo + '\'' +
                ", monthRent=" + monthRent +
                ", contractStart=" + contractStart +
                ", contractEnd=" + contractEnd +
                ", contractUrl='" + contractUrl + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
