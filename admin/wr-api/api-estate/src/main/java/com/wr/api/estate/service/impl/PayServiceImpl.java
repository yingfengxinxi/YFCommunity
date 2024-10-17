package com.wr.api.estate.service.impl;

import com.wr.api.estate.mapper.PayMapper;
import com.wr.api.estate.service.PayService;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 15:02:27
 * @Desc:
 */
@Service
public class PayServiceImpl implements PayService {
    @Resource
    private PayMapper payMapper;
    @Override
    public List<CommunityWechat> selectWechatById(Long communityId, Long wechatId) {
        return payMapper.selectWechatById(communityId,wechatId);
    }

    @Override
    public CommunityAli selectAliById(Long alipayId) {
        return payMapper.selectAliById(alipayId);
    }
}
