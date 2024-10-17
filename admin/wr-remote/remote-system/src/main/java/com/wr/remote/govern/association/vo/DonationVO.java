package com.wr.remote.govern.association.vo;

import com.wr.remote.govern.association.Donation;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-28 11:42:11
 * @Desc: 业主捐赠视图类
 */
public class DonationVO extends Donation {

    /**
     * 小区id
     */
    private Long[] communityIds;

    public DonationVO() {
    }

    public Long[] getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(Long[] communityIds) {
        this.communityIds = communityIds;
    }
}
