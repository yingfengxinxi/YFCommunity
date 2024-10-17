package com.wr.remote.estate.manage.contract;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-10-17 11:54:18
 * @Desc:  采购合同表
 */
public class Procurement extends BaseEntity {

    private static final long serialVersionUID = -4753215095968698189L;
    /**
     * 主键
     */
    private Long  procurementId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 采购人id，表bus_staff
     */
    private Long staffId;

    /**
     * 物业id，表bus_estate（甲方）
     */
    private Long estateId;

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


    /**
     * 合同附件
     */
    private String annexUrl;

    /**
     * '删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getProcurementId() {
        return procurementId;
    }

    public void setProcurementId(Long procurementId) {
        this.procurementId = procurementId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyHead() {
        return companyHead;
    }

    public void setCompanyHead(String companyHead) {
        this.companyHead = companyHead;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "procurement{" +
                "procurementId=" + procurementId +
                ", communityId=" + communityId +
                ", contractNo='" + contractNo + '\'' +
                ", contractName='" + contractName + '\'' +
                ", staffId=" + staffId +
                ", estateId=" + estateId +
                ", companyName='" + companyName + '\'' +
                ", companyHead='" + companyHead + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", annexUrl='" + annexUrl + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
