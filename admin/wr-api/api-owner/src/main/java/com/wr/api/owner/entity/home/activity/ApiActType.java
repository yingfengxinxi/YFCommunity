package com.wr.api.owner.entity.home.activity;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-11-16 13:07:59
 * @Desc:  活动类型表
 */
public class ApiActType extends BaseEntity {


    private static final long serialVersionUID = -8450825902262166044L;

    /**
     * 主键
     */
    private Long typeId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 活动类型名称
     */
    private String typeName;


    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ApiActType{" +
                "typeId=" + typeId +
                ", communityId=" + communityId +
                ", typeName='" + typeName + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
