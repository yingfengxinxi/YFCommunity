package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.mapper.application.CommunityMapper;
import com.wr.api.estate.service.CommunityService;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/30 16:06
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<Community> selectAllCommunityList(Long communityId) {
        return communityMapper.selectAllCommunityList(communityId);
    }

    @Override
    public CommunityHik selectHikByCommunityId(Long communityId) {
        return communityMapper.selectHikByCommunityId(communityId);
    }
}
