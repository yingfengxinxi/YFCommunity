package com.wr.remote.estate.manage.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.remote.estate.manage.contract.Procurement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-10-17 13:10:44
 * @Desc: 采购合同vo
 */
public class BuyerVo extends Procurement {


    private static final long serialVersionUID = -3870259306508588049L;
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 物业名称（甲方公司）
     */
    private String estateName;

    /**
     * 法人代表（甲方负责人）
     */
    private String legal;

    /**
     * 服务热线（甲方联系方式）
     */
    private String hotline;

    /**
     * 乙方公司
     */
    private String companyName;

    /**
     * 乙方负责人
     */
    private String companyHead;

    /**
     * 乙方联系电话
     */
    private String companyPhone;


    /** 采购人 员工姓名 */
    private String staffName;

    /**
     * 物品名称（设备）
     */
    private String materialName;

    /**
     * 总价
     */
    private BigDecimal totalAmount;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 社区id
     */
    private Long communityId;

    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 租客姓名
     */
    private String tenantName;

    /**
     *实际金额
     */
    private BigDecimal  actuallyAmount;


    /** 员工：账号/手机号  采购人 */
    @Excel(name = "账号/手机号")
    private String staffPhone;

    /**
     *设备单价
     */
    private BigDecimal unitPrice;


    /**
     * 物品规格
     */
    private String specification;

    /**
     * 数量
     */
    private Long materialNum;

    /**
     * 合同附件
     */
    private String annexUrl;



    //公司负责人
    private String companyPerson;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCompanyPerson() {
        return companyPerson;
    }

    public void setCompanyPerson(String companyPerson) {
        this.companyPerson = companyPerson;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public BigDecimal getActuallyAmount() {
        return actuallyAmount;
    }

    public void setActuallyAmount(BigDecimal actuallyAmount) {
        this.actuallyAmount = actuallyAmount;
    }

    @Override
    public Long getCommunityId() {
        return communityId;
    }

    @Override
    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @Override
    public String getContractNo() {
        return contractNo;
    }

    @Override
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getCompanyHead() {
        return companyHead;
    }

    @Override
    public void setCompanyHead(String companyHead) {
        this.companyHead = companyHead;
    }

    @Override
    public String getCompanyPhone() {
        return companyPhone;
    }

    @Override
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Long getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Long materialNum) {
        this.materialNum = materialNum;
    }

    @Override
    public String getAnnexUrl() {
        return annexUrl;
    }

    @Override
    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    @Override
    public String toString() {
        return "BuyerVo{" +
                "contractNo='" + contractNo + '\'' +
                ", estateName='" + estateName + '\'' +
                ", legal='" + legal + '\'' +
                ", hotline='" + hotline + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyHead='" + companyHead + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", staffName='" + staffName + '\'' +
                ", materialName='" + materialName + '\'' +
                ", totalAmount=" + totalAmount +
                ", createTime=" + createTime +
                ", communityId=" + communityId +
                ", tenantName='" + tenantName + '\'' +
                ", actuallyAmount=" + actuallyAmount +
                ", staffPhone='" + staffPhone + '\'' +
                ", unitPrice=" + unitPrice +
                ", specification='" + specification + '\'' +
                ", materialNum=" + materialNum +
                ", annexUrl='" + annexUrl + '\'' +
                '}';
    }
}
