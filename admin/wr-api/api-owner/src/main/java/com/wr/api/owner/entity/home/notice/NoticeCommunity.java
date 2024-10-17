package com.wr.api.owner.entity.home.notice;

import java.io.Serializable;

/**
 * @Author: lusu
 * @Date: 2022-11-14 17:23:15
 * @Desc: 社区公告表 关联表
 */
public class NoticeCommunity implements Serializable {

    private static final long serialVersionUID = -6904058758377663843L;

    /**
     * 主键
     */
    private Long  noticeId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    @Override
    public String toString() {
        return "NoticeCommunity{" +
                "noticeId=" + noticeId +
                ", communityId=" + communityId +
                '}';
    }
}
