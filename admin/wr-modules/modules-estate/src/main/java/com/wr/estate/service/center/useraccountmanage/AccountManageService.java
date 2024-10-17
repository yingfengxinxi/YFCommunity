package com.wr.estate.service.center.useraccountmanage;

import com.wr.estate.entity.vo.AccountVo;
import com.wr.remote.estate.manage.contract.Account;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-11 13:31:19
 * @Desc:
 */
public interface AccountManageService {
    /**
     * 查询小区下的账号
     *
     * @param accountVo
     * @return
     */
    List<AccountVo> queryAccountListByCommunityId(AccountVo accountVo);

    /**
     * 获取账号密码
     *
     * @param id
     * @return
     */
    String getAccountPwd(Long id);

    /**
     * 修改账号密码
     *
     * @param id
     * @param pwd
     * @return
     */
    int updateAccountPwd(Long id, String pwd);

    Account getByAccountId(Long accountId);

    Long getAccountIdByLoginName(String ownerPhone);

    /**
     * @param communityId
     * @param ownerPhone
     * @param ownerName
     * @return
     */
    public Long registerAccount(Long communityId, String ownerPhone, String ownerName);

    void updateIsPropertyCouncil(Long accountId, String isPropertyCouncil);

    /**
     *
     * @param faceUrl
     * @param accountName
     * @param accountNick
     * @param accountId
     */
    void updateAccountName(String faceUrl, String accountName, String accountNick, Long accountId);
}
