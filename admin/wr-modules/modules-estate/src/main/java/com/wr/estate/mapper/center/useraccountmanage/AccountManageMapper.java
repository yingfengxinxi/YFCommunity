package com.wr.estate.mapper.center.useraccountmanage;

import com.wr.estate.entity.vo.AccountVo;
import com.wr.remote.estate.manage.contract.Account;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-11 11:26:07
 * @Desc: 账号管理 mapper
 */
public interface AccountManageMapper {

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
     * 根据登陆名获取accountId
     *
     * @param loginName 登陆名
     * @return accountId
     */
    Long getAccountIdByLoginName(String loginName);

    /**
     * 修改账号为业主类型
     *
     * @param accountId 账号id
     */
    void updateOwnerById(Long accountId);

    /**
     * 修改账号密码
     *
     * @param id
     * @param pwd
     * @return
     */
    int updateAccountPwd(@Param("pwd") String pwd, @Param("id") Long id);

    /**
     * 更改头像
     *
     * @param faceUrl
     * @param accountName
     * @param accountNick
     * @param accountId
     * @return
     */
    int updateAccountName(@Param("faceUrl") String faceUrl, @Param("accountName") String accountName, @Param("accountNick") String accountNick, @Param("accountId") Long accountId);

    /**
     * 新增
     *
     * @param params
     * @return
     */
    int insert(Map<String, Object> params);

    Long inAccount(Account account);

    /**
     * 删除 多个
     *
     * @param id
     * @return
     */
    int deleteds(List<Long> id);

    /**
     * 删除 单个
     *
     * @param id
     * @return
     */
    int deleted(Long id);

    /**
     * 删除党员
     *
     * @param accountId
     * @return: java.lang.Integer
     * @author: SJiang
     * @date: 2022/11/29 16:14
     */
    Integer delMember(@Param("accountId") Long accountId);

    Account getByAccountId(@Param("accountId") Long accountId);

    Account checkPhoneUnique(@Param("tenantPhone") String tenantPhone);

    /**
     * 修改账号类型
     *
     * @param accountId
     * @return: java.lang.Integer
     * @date: 2023/4/25 13:58
     * @author: SJiang
     **/
    Integer upAccountType(@Param("accountId") Long accountId, @Param("updateBy") String updateBy, @Param("accountType") String accountType);

    void updateIsPropertyCouncil(@Param("accountId") Long accountId, @Param("isPropertyCouncil") String isPropertyCouncil);
}
