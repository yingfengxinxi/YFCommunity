package com.wr.remote.estate.manage;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 15:36:27
 * @Desc:小区出入口管理表
 */
public class Passage extends BaseEntity {

    private static final long serialVersionUID = -8166503075875919339L;

    /** 主键id */
    private Long passageId;

    /** 小区id */
    private Long communityId;

    /** 出入口名称 */
    private String passageName;

    /** 删除标记：0=未删除；1=已删除 */
    private String delFlag;

    public Long getPassageId() {
        return passageId;
    }

    public void setPassageId(Long passageId) {
        this.passageId = passageId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getPassageName() {
        return passageName;
    }

    public void setPassageName(String passageName) {
        this.passageName = passageName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "passage{" +
                "passageId=" + passageId +
                ", communityId=" + communityId +
                ", passageName='" + passageName + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
