package com.wr.payment.service.impl;

import com.wr.payment.domain.CommunityAli;
import com.wr.payment.domain.CommunityWechat;
import com.wr.payment.mapper.CommunityMapper;
import com.wr.payment.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CommunityWechat selectWechatById(Long wechatId,Long communityId) {

        return communityMapper.selectWechatById(wechatId,communityId);
    }

    @Override
    public CommunityAli selectAliById(Long alipayId) {

        return communityMapper.selectAliById(alipayId);
    }

}
