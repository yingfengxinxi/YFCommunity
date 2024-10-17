package com.wr.remote.estate.customer.goods.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 19:07:05
 * @Desc: 物品管理VO
 */
public class GoodsVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -22202239815980013L;

    /** 主键*/
    private Long goodsId;

    /** 小区id，表bus_community*/
    private Long communityId;

    /** 物品编号*/
    private String goodsNo;

    /** 物品名称*/
    @Excel(name = "物品名称",sort = 0)
    private String goodsName;

    /** 分类id，表bus_classify*/
    private Long classifyId;

    /** 物品价格*/
    private BigDecimal goodsPrice;

    /** 图片路径*/
    private String goodsUrl;

    /** 借用状态：0=空闲中，1=借用中*/
    private String borrowStatus;

    /** 存放地址*/
    private String address;

    /** 删除标记：0=未删除，1=已删除*/
    private String delFlag;

    /** 分类信息*/
    private String classifyName;

    /** 借用次数*/
    private Integer borrowCount;

    /** 借用天数*/
    private Integer borrowDays;

    /** 借用人名字*/
    @Excel(name = "借用人姓名",sort = 1)
    private String ownerName;

    /** 借用人手机号*/
    @Excel(name = "手机号",sort = 2)
    private String ownerPhone;

    @Excel(name = "小区",sort = 3)
    private String communityName;

    @Excel(name = "楼栋",sort = 4)
    private String buildingName;

    @Excel(name = "单元",sort = 5)
    private String unitName;

    @Excel(name = "房间",sort = 6)
    private String roomName;

    private Long accountId;


    /** 审核状态：0=审核中，1=驳回驳回，2=通过*/
    private String auditStatus;

    /** 驳回理由*/
    private String rejectReason;

    /** 借用申请时间*/
    @Excel(name = "申请时间",width = 30, dateFormat = "yyyy-MM-dd",sort = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 预计归还时间*/
    @Excel(name = "预计归还时间",width = 30, dateFormat = "yyyy-MM-dd",sort = 8)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    private List<BorrowVo> borrowVos;

    /** 审批人*/
    private  String auditBy;

    /** 审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 赔偿方式：0=现金，1=物品*/
    private String compensateWay;

    /** 赔偿时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date compensateTime;

    public Date getCompensateTime() {
        return compensateTime;
    }

    public void setCompensateTime(Date compensateTime) {
        this.compensateTime = compensateTime;
    }

    public String getCompensateWay() {
        return compensateWay;
    }

    public void setCompensateWay(String compensateWay) {
        this.compensateWay = compensateWay;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public List<BorrowVo> getBorrowVos() {
        return borrowVos;
    }

    public void setBorrowVos(List<BorrowVo> borrowVos) {
        this.borrowVos = borrowVos;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public List<BorrowVo> getBorrows() {
        return borrowVos;
    }

    public void setBorrows(List<BorrowVo> borrowVos) {
        this.borrowVos = borrowVos;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(Integer borrowDays) {
        this.borrowDays = borrowDays;
    }

    public Integer getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(Integer borrowCount) {
        this.borrowCount = borrowCount;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }
}
