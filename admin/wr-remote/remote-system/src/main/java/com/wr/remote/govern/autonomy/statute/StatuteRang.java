package com.wr.remote.govern.autonomy.statute;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-10-25 09:05:21
 * @Desc: 自治规约-小区关联表
 */
public class StatuteRang extends BaseEntity {

    private static final long serialVersionUID = -12960437807789651L;

    public StatuteRang(Long statuteId, Long communityId) {
        this.statuteId = statuteId;
        this.communityId = communityId;
    }

    public StatuteRang() {
    }

    /**
     * 自治规约id，表xccj_govern.bus_statute
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
