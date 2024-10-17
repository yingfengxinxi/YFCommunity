package com.wr.remote.govern.autonomy.decide;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-10-26 14:00:41
 * @Desc: 自治表决-小区关联表
 */
public class DecideRang extends BaseEntity {

    private static final long serialVersionUID = -374266913063141918L;
    /**
     * 自治表决id，表xccj_govern.bus_decide
     */
    private Long statuteId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    public Long getStatuteId() {
        return statuteId;
    }

    public void setStatuteId(Long statuteId) {
        this.statuteId = statuteId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
