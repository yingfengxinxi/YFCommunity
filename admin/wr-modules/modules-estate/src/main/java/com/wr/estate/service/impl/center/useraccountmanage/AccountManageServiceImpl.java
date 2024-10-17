package com.wr.estate.service.impl.center.useraccountmanage;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.AccountVo;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import com.wr.remote.estate.manage.contract.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-11 13:31:52
 * @Desc:
 */
@Service
public class AccountManageServiceImpl implements AccountManageService {
    @Resource
    private AccountManageMapper accountManageMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<AccountVo> queryAccountListByCommunityId(AccountVo accountVo) {
        List<AccountVo> accountVos = accountManageMapper.queryAccountListByCommunityId(accountVo);
        if (CollectionUtils.isNotEmpty(accountVos)) {
            accountVos.stream().forEach(av -> {
                av.setLoginName(DesensitizationUtils.phone(av.getLoginName()));
                av.setAccountName(DesensitizationUtils.name(av.getAccountName()));
                av.setAccountNick(DesensitizationUtils.name(av.getAccountNick()));
                if (StringUtils.isNotEmpty(av.getCardType())) {
                    if (av.getCardType().equals("I")) {
                        av.setCardNo(DesensitizationUtils.cardNo(av.getCardNo()));
                    }
                }

            });
        }
        return accountVos;
    }


    @Override
    public String getAccountPwd(Long id) {
        return accountManageMapper.getAccountPwd(id);
    }

    @Override
    public int updateAccountPwd(Long id, String pwd) {

        return accountManageMapper.updateAccountPwd(pwd, id);
    }

    @Override
    public Account getByAccountId(Long accountId) {
        return accountManageMapper.getByAccountId(accountId);
    }

    @Override
    public Long getAccountIdByLoginName(String ownerPhone) {
        return accountManageMapper.getAccountIdByLoginName(ownerPhone);
    }

    /**
     * @param communityId
     * @param ownerPhone
     * @param ownerName
     * @return
     */
    @Override
    public Long registerAccount(Long communityId, String ownerPhone, String ownerName) {
        Account account = new Account();
        account.setCommunityId(communityId);
        account.setLoginName(ownerPhone);
        account.setPassword(SecurityUtils.encryptPassword("1234abc"));
        // 账号类型（业主）
        account.setAccountType("0");
        account.setAccountName(ownerName);
        account.setAccountNick(ownerName);
        // 数据来源（物业注册）
        account.setDataSource("0");
        // 是否是志愿者（不是）
        account.setVolunteer("0");
        account.setCreateBy(ownerPhone);
        account.setUpdateBy(ownerPhone);
        String avatar = redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "ownerAvatarMain");
        account.setAvatar(avatar);
        return accountManageMapper.inAccount(account);
    }

    @Override
    public void updateIsPropertyCouncil(Long accountId, String isPropertyCouncil) {

        accountManageMapper.updateIsPropertyCouncil(accountId, isPropertyCouncil);
    }

    @Override
    public void updateAccountName(String faceUrl, String accountName, String accountNick, Long accountId) {
        accountManageMapper.updateAccountName(faceUrl, accountName, accountNick, accountId);
    }


}
