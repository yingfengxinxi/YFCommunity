package com.wr.api.owner.entity.home.advice;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 09:19:55
 * @Desc: 违法举报表
 */
public class Illegal extends BaseEntity {
    private static final long serialVersionUID = -3627548846626573928L;
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
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Illegal{" +
                "illegalId=" + illegalId +
                ", communityId=" + communityId +
                ", accountId=" + accountId +
                ", illegalType='" + illegalType + '\'' +
                ", illegalUrl='" + illegalUrl + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
