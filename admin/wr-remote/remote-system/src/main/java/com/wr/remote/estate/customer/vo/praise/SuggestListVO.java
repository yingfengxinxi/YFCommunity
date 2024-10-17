package com.wr.remote.estate.custom.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: 荀彧
 * @Date: 2022-10-12 13:54:44
 * @Desc: 意见箱表VO
 */
public class SuggestListVO extends BaseEntity {

    // 意见表ID
    private Long suggestId;
    // 小区ID
    private Long communityId;
    // 业主名称
    private String accountNick;
    // 楼栋名
    private String buildingName;
    // 建议类型
    private String suggestType;
    // 建议内容
    private String remark;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public SuggestListVO() {
    }

    public String getAccountNick() {
        return accountNick;
    }

    public void setAccountNick(String accountNick) {
        this.accountNick = accountNick;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getSuggestType() {
        return suggestType;
    }

    public void setSuggestType(String suggestType) {
        this.suggestType = suggestType;
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

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Long suggestId) {
        this.suggestId = suggestId;
    }
}
