package com.wr.api.owner.service.impl;

import com.wr.api.owner.mapper.CommunitysMapper;
import com.wr.api.owner.service.CommunityService;
import com.wr.remote.domain.Community;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.domain.Estate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 17:54:26
 * @Desc: 小区 业务实现
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunitysMapper communityMapper;

    @Override
    public Community getCommunityById(Long communityId) {
        return communityMapper.getCommunityById(communityId);
    }

    @Override
    public Estate getEstate(Long estateId) {
        return communityMapper.getEstate(estateId);
    }

    @Override
    public List<CommunityWechat> selectWechatByCommunityId(Long communityId) {

        return communityMapper.selectWechatByCommunityId(communityId);
    }

    @Override
    public CommunityWechat selectWechatById(Long wechatId) {

        return communityMapper.selectWechatById(wechatId);
    }

    @Override
    public List<CommunityAli> selectAliByCommunityId(Long communityId) {

        return communityMapper.selectAliByCommunityId(communityId);
    }

    @Override
    public CommunityAli selectAliById(Long alipayId) {

        return communityMapper.selectAliById(alipayId);
    }

    @Override
    public List<Community> getCommunityAll() {
        return communityMapper.getCommunityAll();
    }
}
