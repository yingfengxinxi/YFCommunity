package com.wr.remote.estate.manage.contract;


import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-17 16:08:54
 * @Desc: 服务合同实体类
 */
public class ServerContract extends BaseEntity {

    /**
     * 主键
     */
    private Long serverId;


    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 签约人id，表xccj_estate_sys.sys_user(user_type=10,11)
     */
    private Long userId;

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
     * 金额
     */
    private BigDecimal serverAmount;

    /**
     * 合同附件
     */
    private String annexUrl;

    /**
     * 合同图片
     */
    private String imageUrl;

    /**
     * 是否删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;

    public ServerContract() {
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public BigDecimal getServerAmount() {
        return serverAmount;
    }

    public void setServerAmount(BigDecimal serverAmount) {
        this.serverAmount = serverAmount;
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
