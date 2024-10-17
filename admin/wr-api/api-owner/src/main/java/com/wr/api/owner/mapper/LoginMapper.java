package com.wr.api.owner.mapper;

import com.wr.api.owner.entity.vo.CommunityVO;
import com.wr.api.owner.entity.vo.VisitorVO;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.decorate.Account;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import com.wr.remote.govern.party.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 19:00:18
 * @Desc: 业主端登录数据层
 */
public interface LoginMapper {

    /**
     * 根据登录名查询账号
     *
     * @param loginName 登录名
     * @return com.wr.remote.estate.customer.decorate.Account
     */
    Account selectAccountByLoginName(String loginName);

    /**
     * 根据小区第查询所属物业id
     *
     * @param communityId 小区id
     * @return java.lang.Long
     */
    Long selectEstateIdByCommunityId(Long communityId);

    /**
     * 根据账号查询党员信息
     *
     * @param accountId 账号id
     * @return java.lang.Long
     */
    Member selectMemberByAccountId(Long accountId);

    /**
     * 根据账号id查询业主信息
     *
     * @param accountId 账号id
     * @return com.wr.remote.estate.manage.contract.Owner
     */
    Owner selectOwnerByAccountId(Long accountId);

    /**
     * 根据业主id查询所在委员会id
     *
     * @param ownerId 业主id
     * @return java.lang.Long
     */
    Long selectCommitteeIdByOwnerId(Long ownerId);

    /**
     * 根据账号id查询租客信息
     *
     * @param accountId 账号id
     * @return com.wr.remote.estate.manage.contract.Tenant
     */
    Tenant selectTenantByAccountId(Long accountId);

    /**
     * 根据账号id查询租客信息
     *
     * @param accountId 账号id
     * @return com.wr.remote.estate.manage.contract.Tenant
     */
    VisitorVO selectVisitorByAccountId(Long accountId);

    /**
     * 根据身份证号查询业主信息
     *
     * @param idCardNo 身份证号
     * @return com.wr.remote.estate.manage.contract.Owner
     */
    Owner selectOwnerByCardNo(String idCardNo);

    /**
     * 根据身份证号查询租客信息
     *
     * @param idCardNo 身份证号
     * @return com.wr.remote.estate.manage.contract.Tenant
     */
    Tenant selectTenantByCardNo(String idCardNo);

    /**
     * 根据身份证号查询访客账号id
     *
     * @param idCardNo 身份证号
     * @return java.lang.Long
     */
    Long selectVisitorAccountByCardNo(String idCardNo);

    /**
     * 根据区域、业主id查询由房产的小区
     *
     * @param areaId  区域id
     * @param ownerId 业主id
     * @return java.util.List<com.wr.api.owner.entity.vo.CommunityVO>
     */
    List<CommunityVO> selectCommunityListByOwner(@Param("areaId") Long areaId, @Param("ownerId") Long ownerId);

    /**
     * 根据区域id、租客id查询有租房的小区
     *
     * @param areaId   区域id
     * @param tenantId 租客id
     * @return java.util.List<com.wr.api.owner.entity.vo.CommunityVO>
     */
    List<CommunityVO> selectCommunityListByTenant(@Param("areaId") Long areaId, @Param("tenantId") Long tenantId);

    /**
     * 修改密码
     *
     * @param accountId 登录账号id
     * @param password  密码
     * @return java.lang.Integer
     */
    Integer updatePassword(@Param("accountId") Long accountId, @Param("password") String password);

    /**
     * 新增访客账户
     *
     * @param account 账户信息
     * @return java.lang.Integer
     * @author yx
     * @date 2022/12/1 10:33
     */
    Integer insertAccount(Account account);

    /**
     * 根据小区ID查小区名称
     * @param communtityId
     * @return: java.lang.String
     * @date: 2023/3/24 16:36
     * @author: SJiang
     **/
    String selectCommunityName(@Param("communtityId") Long communtityId);

    /**
     * 根据账号id查询业主信息
     *
     * @param accountId 账号id
     * @return com.wr.remote.estate.manage.contract.Owner
     */
    Map findOneOwnerByAccount(Long accountId);

    /**
    * @Author SUNk
    * @Description 根据手机号查询是否注册
    * @Date 15:19 2024/3/2
    * @Param [phone]
    * @return java.lang.Boolean
    **/
    Integer findRegisterByphone(String phone);

    /**
     * 根据账号查询党员信息
     *
     * @param accountId 账号id
     * @return java.lang.Long
     */
    Member selectMemberByCommunityId(@Param("accountId") Long accountId, @Param("communityId") Long communityId);

    void updateAccountType(Account account);
}
