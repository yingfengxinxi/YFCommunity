package com.wr.remote.govern.autonomy.decide;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-10-28 17:07:43
 * @Desc:
 */
public class DecideOwner extends BaseEntity {

    private static final long serialVersionUID = 4337925145934549549L;
    /**
     * 自治表决id自治表决id，表xccj_govern.bus_decide表xccj_govern.bus_decide
     */
    private Long decideId;

    /**
     * 业主id，表xccj_community.bus_owner
     */
    private Long ownerId;


    public Long getDecideId() {
        return decideId;
    }

    public void setDecideId(Long decideId) {
        this.decideId = decideId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
