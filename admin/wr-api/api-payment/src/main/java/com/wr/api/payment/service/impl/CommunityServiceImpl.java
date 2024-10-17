package com.wr.api.payment.service.impl;

import com.wr.api.payment.mapper.CommunityMapper;
import com.wr.api.payment.service.CommunityService;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-11 18:12:29
 * @Desc: 小区业务处理
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<CommunityWechat> selectWechatByCommunityId(Long communityId) {

        return communityMapper.selectWechatByCommunityId(communityId);
    }

    @Override
    public List<CommunityAli> selectAliByCommunityId(Long communityId) {

        return communityMapper.selectAliByCommunityId(communityId);
    }

}
