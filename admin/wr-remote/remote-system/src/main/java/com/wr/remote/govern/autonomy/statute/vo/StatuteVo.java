package com.wr.remote.govern.autonomy.statute.vo;

import com.wr.remote.govern.autonomy.statute.Statute;

/**
 * @Author: lusu
 * @Date: 2022-10-25 11:56:13
 * @Desc:  自治规约vo
 */
public class StatuteVo extends Statute {

    private static final long serialVersionUID = 2475683226346133294L;
    /**
     * 用户选择多个小区时， 使用数组进行接收
     */
    private Long[] communityIds;

    public Long[] getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(Long[] communityIds) {
        this.communityIds = communityIds;
    }
}
