package com.wr.remote.estate.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author: WuKong
 * @Date: 2022-10-12 13:37:30
 * @Desc: 物业信息对象
 */
public class BusEstate extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 物业信息ID */
    private Long estateId;

    /** 父级物业ID */
    @Excel(name = "父级物业ID")
    private Long parentId;

    /** 祖籍列表 */
    @Excel(name = "祖籍列表")
    private String ancestors;

    /** 物业名称 */
    @Excel(name = "物业名称")
    private String estateName;

    /** 关联部门id，表xccj_estate_sys.sys_dept,dept_type=10 */
    @Excel(name = "关联部门id，表xccj_estate_sys.sys_dept,dept_type=10")
    private Long deptId;

    /** 机构编码 */
    @Excel(name = "机构编码")
    private String estateCode;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String creditCode;

    /** 服务热线 */
    @Excel(name = "服务热线")
    private String hotline;

    /** 法人代表 */
    @Excel(name = "法人代表")
    private String legal;

    /** 登记状态:0=存续（在营、开业、在册）、1=在业、2=吊销、3=注销、4=迁入、5=迁出、6=停业、7=清算 */
    @Excel(name = "登记状态:0=存续", readConverterExp = "在=营、开业、在册")
    private String registerStatus;

    /** 成立日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date foundDate;

    /** 注册资本（万） */
    @Excel(name = "注册资本", readConverterExp = "万=")
    private BigDecimal registerCapital;

    /** 实缴资本 */
    @Excel(name = "实缴资本")
    private BigDecimal actualCapital;

    /** 核准日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "核准日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approveDate;

    /** 工商注册号 */
    @Excel(name = "工商注册号")
    private String businessCode;

    /** 纳税人识别号 */
    @Excel(name = "纳税人识别号")
    private String taxpayerCode;

    /** 企业类型:0=有限责任公司、1=无限责任公司、2=两合公司、3=股份有限公司、4=股份两合公司 */
    @Excel(name = "企业类型:0=有限责任公司、1=无限责任公司、2=两合公司、3=股份有限公司、4=股份两合公司")
    private String firmType;

    /** 营业期限（开始） */
    @Excel(name = "营业期限", readConverterExp = "开=始")
    private Date periodBegin;

    /** 营业期限（结束），为空则无固定期限 */
    @Excel(name = "营业期限", readConverterExp = "结=束")
    private Date periodEnd;

    /** 纳税人资质 */
    @Excel(name = "纳税人资质")
    private String taxpayerQualify;

    /** 所属地区 */
    @Excel(name = "所属地区")
    private String areaName;

    /** 登记机关 */
    @Excel(name = "登记机关")
    private String registerOffice;

    /** 人员规模 */
    @Excel(name = "人员规模")
    private String staffSize;

    /** 参保人数 */
    @Excel(name = "参保人数")
    private Long insuredNum;

    /** 曾用名 */
    @Excel(name = "曾用名")
    private String formerName;

    /** 注册地址 */
    @Excel(name = "注册地址")
    private String registerAddress;

    /** 历史服务 */
    @Excel(name = "历史服务")
    private String historyServer;

    /** 简介 */
    @Excel(name = "简介")
    private String brief;

    /** 营业范围 */
    @Excel(name = "营业范围")
    private String businessScope;

    /** 删除状态:0=未删除;1=已删除 */
    private String delFlag;

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getEstateCode() {
        return estateCode;
    }

    public void setEstateCode(String estateCode) {
        this.estateCode = estateCode;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(String registerStatus) {
        this.registerStatus = registerStatus;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public BigDecimal getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(BigDecimal registerCapital) {
        this.registerCapital = registerCapital;
    }

    public BigDecimal getActualCapital() {
        return actualCapital;
    }

    public void setActualCapital(BigDecimal actualCapital) {
        this.actualCapital = actualCapital;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getTaxpayerCode() {
        return taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
        this.taxpayerCode = taxpayerCode;
    }

    public String getFirmType() {
        return firmType;
    }

    public void setFirmType(String firmType) {
        this.firmType = firmType;
    }

    public Date getPeriodBegin() {
        return periodBegin;
    }

    public void setPeriodBegin(Date periodBegin) {
        this.periodBegin = periodBegin;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getTaxpayerQualify() {
        return taxpayerQualify;
    }

    public void setTaxpayerQualify(String taxpayerQualify) {
        this.taxpayerQualify = taxpayerQualify;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRegisterOffice() {
        return registerOffice;
    }

    public void setRegisterOffice(String registerOffice) {
        this.registerOffice = registerOffice;
    }

    public String getStaffSize() {
        return staffSize;
    }

    public void setStaffSize(String staffSize) {
        this.staffSize = staffSize;
    }

    public Long getInsuredNum() {
        return insuredNum;
    }

    public void setInsuredNum(Long insuredNum) {
        this.insuredNum = insuredNum;
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getHistoryServer() {
        return historyServer;
    }

    public void setHistoryServer(String historyServer) {
        this.historyServer = historyServer;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "BusEstate{" +
                "estateId=" + estateId +
                ", parentId=" + parentId +
                ", ancestors='" + ancestors + '\'' +
                ", estateName='" + estateName + '\'' +
                ", deptId=" + deptId +
                ", estateCode='" + estateCode + '\'' +
                ", creditCode='" + creditCode + '\'' +
                ", hotline='" + hotline + '\'' +
                ", legal='" + legal + '\'' +
                ", registerStatus='" + registerStatus + '\'' +
                ", foundDate=" + foundDate +
                ", registerCapital=" + registerCapital +
                ", actualCapital=" + actualCapital +
                ", approveDate=" + approveDate +
                ", businessCode='" + businessCode + '\'' +
                ", taxpayerCode='" + taxpayerCode + '\'' +
                ", firmType='" + firmType + '\'' +
                ", periodBegin=" + periodBegin +
                ", periodEnd=" + periodEnd +
                ", taxpayerQualify='" + taxpayerQualify + '\'' +
                ", areaName='" + areaName + '\'' +
                ", registerOffice='" + registerOffice + '\'' +
                ", staffSize='" + staffSize + '\'' +
                ", insuredNum=" + insuredNum +
                ", formerName='" + formerName + '\'' +
                ", registerAddress='" + registerAddress + '\'' +
                ", historyServer='" + historyServer + '\'' +
                ", brief='" + brief + '\'' +
                ", businessScope='" + businessScope + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
