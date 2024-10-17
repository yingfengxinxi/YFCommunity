package com.wr.estate.service.impl.financial;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.financial.AccountManagementMapper;
import com.wr.estate.service.financial.AccountManagementService;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/1
 * @Description:
 * @FileName: AccountManagementServiceImpl
 * @History:
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AccountManagementServiceImpl implements AccountManagementService {

    @Autowired
    AccountManagementMapper accountManagementMapper;

    @Override
    public List<CommunityAli> selectAliList(CommunityAli communityAli) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        communityAli.setEstateId(estateId);
        return accountManagementMapper.selectAliList(communityAli);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertAli(CommunityAli communityAli) {
        communityAli.setCreateBy(SecurityUtils.getUsername());
        return accountManagementMapper.insertAli(communityAli);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateAli(CommunityAli communityAli) {
        communityAli.setUpdateBy(SecurityUtils.getUsername());
        return accountManagementMapper.updateAli(communityAli);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer removeAli(Long alipayId) {

        return accountManagementMapper.removeAli(alipayId);
    }

    @Override
    public List<CommunityWechat> selectWechatList(CommunityWechat communityWechat) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        communityWechat.setEstateId(estateId);
        return accountManagementMapper.selectWechatList(communityWechat);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertWechat(CommunityWechat communityWechat) {
        communityWechat.setCreateBy(SecurityUtils.getUsername());
        return accountManagementMapper.insertWechat(communityWechat);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateWechat(CommunityWechat communityWechat) {
        communityWechat.setUpdateBy(SecurityUtils.getUsername());
        return accountManagementMapper.updateWechat(communityWechat);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer removeWechat(Long wechatId) {

        return accountManagementMapper.removeWechat(wechatId);
    }

}
