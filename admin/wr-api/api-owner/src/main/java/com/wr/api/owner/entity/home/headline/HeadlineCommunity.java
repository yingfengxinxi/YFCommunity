package com.wr.api.owner.entity.home.headline;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-11-14 20:15:07
 * @Desc:  社区头条 —— 公示  关联表
 */
public class HeadlineCommunity extends BaseEntity {
    private static final long serialVersionUID = 4710487945322425119L;

    /**
     * 社区头条id，表bus_headline
     */
    private Long headlineId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    public Long getHeadlineId() {
        return headlineId;
    }

    public void setHeadlineId(Long headlineId) {
        this.headlineId = headlineId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }


    @Override
    public String toString() {
        return "HeadlineCommunity{" +
                "headlineId=" + headlineId +
                ", communityId=" + communityId +
                '}';
    }
}
