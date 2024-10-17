package com.wr.api.estate.service;

import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.Community;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/30 16:06
 */
public interface CommunityService {

    /**
     *
     * @return
     */
    List<Community> selectAllCommunityList(Long estateId);

    /**
     *
     * @param communityId
     * @return
     */
    CommunityHik selectHikByCommunityId(Long communityId);
}
