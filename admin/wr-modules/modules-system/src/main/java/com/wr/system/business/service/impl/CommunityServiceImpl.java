package com.wr.system.business.service.impl;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.CommunityWechat;
import com.wr.system.business.domain.CommunityVo;
import com.wr.system.business.mapper.CommunityMapper;
import com.wr.system.business.mapper.EstateMapper;
import com.wr.system.business.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-16 15:51:11
 * @Desc:
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private EstateMapper estateMapper;

    @Override
    public CommunityVo selectCommunityVoById(Long communityId) {

        return communityMapper.selectCommunityVoById(communityId);
    }

    @Override
    public Integer communityTableColumnsById(String communityTable, Long communityId) {
        return communityMapper.communityTableColumnsById(communityTable, communityId);
    }

    @Override
    public List<CommunityVo> selectCommunityVoList(CommunityVo communityVo) {
        /*List<CommunityVo> communityVoList = communityMapper.selectCommunityVoList(communityVo);
        for (CommunityVo item : communityVoList) {
            if (StringUtils.isNotNull(item.getEstateId())) {
                EstateVo estateVo = estateMapper.selectEstateVoById(item.getEstateId());
                if (StringUtils.isNotNull(estateVo)) {
                    item.setEstateName(estateVo.getEstateName());
                }
                continue;
            }
            continue;
        }
        return communityVoList;*/
        return communityMapper.selectCommunityVoList(communityVo);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertCommunityVo(CommunityVo communityVo) {

        return communityMapper.insertCommunityVo(communityVo);
    }

    @Override
    public Integer insertCommunityHikVo(CommunityHik communityHik) {
        return communityMapper.insertCommunityHikVo(communityHik);
    }

    @Override
    public Integer insertCommunityWechatVo(CommunityWechat communityWechat) {
        return communityMapper.insertCommunityWechatVo(communityWechat);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateCommunityVo(CommunityVo communityVo) {

        return communityMapper.updateCommunityVo(communityVo);
    }

    @Override
    public Integer updateCommunityHikVo(CommunityHik communityHik) {
        return communityMapper.updateCommunityHikVo(communityHik);
    }

    @Override
    public Integer updateCommunityWechatVo(CommunityWechat communityWechat) {
        return communityMapper.updateCommunityWechatVo(communityWechat);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteCommunityVoById(Long communityId) {
        Integer count = communityMapper.deleteCommunityVoById(communityId);
        String[] table = new String[]{"bus_community_hik", "bus_community_wechat"};
        for (String item : table) {
            communityMapper.deleteCommunityTableVoById(item, communityId);
        }
        return count;
    }

    @Override
    public AjaxResult addSubscribe(Long[] communityIds) {

        int success = 0;
        int error = 0;
        for (int i = 0; i < communityIds.length; i++) {
            CommunityVo communityVo = communityMapper.selectCommunityVoById(communityIds[i]);
            if (null == communityVo) {
                error++;
                continue;
            }
            success++;
        }
        return AjaxResult.success("订阅成功:" + success + "条，" + "--" + "订阅失败:" + error + "条");
    }

    @Override
    public CommunityHik selectHikByCommunityId(Long communityId) {

        return communityMapper.selectHikByCommunityId(communityId);
    }
}
