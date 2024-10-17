package com.wr.remote.govern.security;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-26 18:59:16
 * @Desc:
 */
public class Clue extends BaseEntity {

    /**
     * 主键
     */
    private Long clueId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    /**
     * 通报id，表xccj_govern.bus_bulletin
     */
    private Long bulletinId;

    /**
     * 举报人id，表xccj_community.bus_account
     */
    private Long accountId;

    /**
     * 举报人名称，表xccj_community.bus_account
     */
    private String accountName;

    /**
     * 地点
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getClueId() {
        return clueId;
    }

    public void setClueId(Long clueId) {
        this.clueId = clueId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public Long getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(Long bulletinId) {
        this.bulletinId = bulletinId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
