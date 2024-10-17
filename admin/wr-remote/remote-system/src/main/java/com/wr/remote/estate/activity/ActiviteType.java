package com.wr.remote.estate.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-12 14:02:33
 * @Desc:
 */
public class ActiviteType extends BaseEntity {

    /** '主键' */
    private Long typeId;
    /** '小区ID' */
    private Long communityId;
    /** '小区名称' */
    private String communityName;
    /** '类别名称' */
    private String typeName;
    /** '删除标记' */
    private Long delFlag;
    /** '创建人' */
    private String createBy;
    /** '创建人' */
    private String updateBy;
    /** '创建时间' */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date createTime;
    /** '更新时间' */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;
    /** '物业' */
    private Long estateId;

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

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

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
