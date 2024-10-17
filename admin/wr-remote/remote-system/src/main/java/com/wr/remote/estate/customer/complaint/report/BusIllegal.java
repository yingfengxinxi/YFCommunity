package com.wr.remote.estate.complaint.report;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @description 违法举报表
 * @author 荀彧
 * @date 2022-10-13
 */
public class BusIllegal extends BaseEntity {

    /**
     * 主键
     */
    private Long illegalId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 发布人账号id，表bus_account
     */
    private Long accountId;

    /**
     * 类型：见数据字典 sys_illegal_type
     */
    private String illegalType;

    /**
     * 图片
     */
    private String illegalUrl;

    /**
     * 举报说明
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public BusIllegal() {}

    public Long getIllegalId() {
        return illegalId;
    }

    public void setIllegalId(Long illegalId) {
        this.illegalId = illegalId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getIllegalType() {
        return illegalType;
    }

    public void setIllegalType(String illegalType) {
        this.illegalType = illegalType;
    }

    public String getIllegalUrl() {
        return illegalUrl;
    }

    public void setIllegalUrl(String illegalUrl) {
        this.illegalUrl = illegalUrl;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}
