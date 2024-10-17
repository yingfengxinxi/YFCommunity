package com.wr.remote.govern.security.vo;

import com.wr.remote.govern.security.Prevent;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-25 14:41:04
 * @Desc:
 */
public class PreventVO extends Prevent {

    /**
     * 小区id
     */
    private Long[] communityIds;

    public PreventVO() {

    }

    public Long[] getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(Long[] communityIds) {
        this.communityIds = communityIds;
    }

    @Override
    public String toString() {
        return "PreventVO{" +
                "communityIds=" + Arrays.toString(communityIds) +
                '}';
    }
}
