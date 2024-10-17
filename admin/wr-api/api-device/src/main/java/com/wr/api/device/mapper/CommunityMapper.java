package com.wr.api.device.mapper;

import com.wr.common.customize.util.CommunityHik;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 09:52:46
 * @Desc:
 */
public interface CommunityMapper {

    /**
     * 根据小区id查询海康配置
     * @param communityId 小区id
     * @return com.wr.common.customize.util.CommunityHik
     */
    CommunityHik selectHikByCommunityId(Long communityId);
}

