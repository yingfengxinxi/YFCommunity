package com.wr.api.owner.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.dto.ChangeDTO;
import com.wr.api.owner.entity.dto.UserDTO;
import com.wr.api.owner.entity.home.visitor.vo.OwnerHouseVo;
import com.wr.api.owner.entity.vo.CommunityVO;
import com.wr.api.owner.entity.vo.VisitorVO;
import com.wr.api.owner.mapper.LoginMapper;
import com.wr.api.owner.mapper.home.visitor.ApiVisitorMapper;
import com.wr.api.owner.service.LoginService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.auth.Authentication;
import com.wr.common.customize.constant.ApiConstants;
import com.wr.common.customize.model.ApiUser;
import com.wr.common.payment.pay.util.WechatPayUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.customer.decorate.Account;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import com.wr.remote.govern.party.Member;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 18:59:12
 * @Desc: 业主端登录业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final LoginMapper loginMapper;
    private final RedisService redisService;
    @Resource
    private ApiVisitorMapper apiVisitorMapper;

    @Override
    public AjaxResult login(ApiUser apiUser) {
        String loginName = apiUser.getLoginName();
        String password = apiUser.getPassword();
        Account account = loginMapper.selectAccountByLoginName(loginName);
        Assert.notNull(account, "未查询到账号信息");
        boolean isTrue = SecurityUtils.matchesPassword(password, account.getPassword());
        Assert.isTrue(isTrue, "账号密码错误，请重试");
        // 获取登陆后的 token
        String token = Authentication.createToken(loginName, password);
        log.info("token:{}", token);
        redisService.setCacheObject(ApiConstants.LOGIN_KEY + loginName, apiUser);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("token", token);
        String communityName = loginMapper.selectCommunityName(account.getCommunityId());
        // 账号信息
        JSONObject accountJson = new JSONObject();
        accountJson.put("accountId", account.getAccountId());
        accountJson.put("communityId", account.getCommunityId());
        accountJson.put("communityName", communityName);
        accountJson.put("loginName", loginName);
        accountJson.put("avatar", account.getAvatar());
        accountJson.put("accountType", account.getAccountType());
        accountJson.put("accountName", account.getAccountName());
        accountJson.put("accountNick", account.getAccountNick());
        accountJson.put("volunteer", account.getVolunteer());
        // 查询所属物业id
        Long estateId = loginMapper.selectEstateIdByCommunityId(account.getCommunityId());
        accountJson.put("estateId", estateId);
        // 判断是否是党员
        Member member = loginMapper.selectMemberByAccountId(account.getAccountId());
        packageMemberData(accountJson, member);
        ajaxResult.put("account", accountJson);
        // 业主登录
        Owner owner;
        if ("0".equals(account.getAccountType())) {
            owner = loginMapper.selectOwnerByAccountId(account.getAccountId());
            if(owner == null){
                loginMapper.updateAccountType(account);
                ajaxResult.put("owner", null);
                return ajaxResult;
            }else {
                JSONObject ownerJson = packageOwnerData(owner);
                // 判断是个否是业主委员会成员
                Long committeeId = loginMapper.selectCommitteeIdByOwnerId(owner.getOwnerId());
                // 查询业主委员会信息
                ownerJson.put("committeeId", committeeId);
                ajaxResult.put("owner", ownerJson);
                return ajaxResult;
            }
        } else {
            ajaxResult.put("owner", null);
        }
        // 租客登录
        Tenant tenant;
        if ("1".equals(account.getAccountType())) {
            tenant = loginMapper.selectTenantByAccountId(account.getAccountId());
            return packageTenantData(tenant, ajaxResult);
        } else {
            ajaxResult.put("tenant", null);
        }
        // 访客登录
        VisitorVO visitorVO;
        if ("2".equals(account.getAccountType())) {
            visitorVO = loginMapper.selectVisitorByAccountId(account.getAccountId());
            return packageVisitorData(visitorVO, ajaxResult);
        } else {
            ajaxResult.put("visitorVO", null);
        }
        return ajaxResult;
    }

    private static void packageMemberData(JSONObject accountJson, Member member) {
        JSONObject memberJson = new JSONObject();
        Optional.ofNullable(member).ifPresent(m -> {
            memberJson.put("memberId", m.getMemberId());
            memberJson.put("partyId", m.getPartyId());
            memberJson.put("joinTime", m.getJoinTime());
            memberJson.put("auditStatus", m.getAuditStatus());
            memberJson.put("auditTime", m.getAuditTime());
            memberJson.put("rejectReason", m.getRejectReason());
            memberJson.put("partyFee", m.getPartyFee());
        });
        accountJson.put("member", memberJson);
    }

    /**
     * 封装业主信息
     *
     * @param owner 业主信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private static JSONObject packageOwnerData(Owner owner) {
        JSONObject ownerJson = new JSONObject();
        ownerJson.put("ownerId", owner.getOwnerId());
        ownerJson.put("buildingId", owner.getBuildingId());
        ownerJson.put("unitId", owner.getUnitId());
        ownerJson.put("roomId", owner.getRoomId());
        ownerJson.put("ownerName", owner.getOwnerName());
        ownerJson.put("ownerPhone", owner.getOwnerPhone());
        ownerJson.put("ownerRelation", owner.getOwnerRelation());
        ownerJson.put("parentId", owner.getParentId());
        ownerJson.put("gender", owner.getGender());
        ownerJson.put("cardType", owner.getCardType());
        ownerJson.put("cardNo", owner.getCardNo());
        ownerJson.put("cardFont", owner.getCardFont());
        ownerJson.put("cardBack", owner.getCardBack());
        ownerJson.put("auditStatus", owner.getAuditStatus());
        ownerJson.put("faceUrl", owner.getFaceUrl());

        return ownerJson;
    }

    /**
     * 封装租客信息
     *
     * @param tenant     租客信息
     * @param ajaxResult 返回结果
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private static AjaxResult packageTenantData(Tenant tenant, AjaxResult ajaxResult) {
        Assert.notNull(tenant, "未查询到租客信息");
        if ("0".equals(tenant.getAuditStatus())) {
            return AjaxResult.error("租客信息审核中");
        }
        if ("2".equals(tenant.getAuditStatus())) {
            return AjaxResult.error("租客信息审核不通过", tenant.getRejectReason());
        }
        JSONObject tenantJson = new JSONObject();
        tenantJson.put("tenantId", tenant.getTenantId());
        tenantJson.put("buildingId", tenant.getBuildingId());
        tenantJson.put("unitId", tenant.getUnitId());
        tenantJson.put("roomId", tenant.getRoomId());
        tenantJson.put("ownerId", tenant.getOwnerId());
        tenantJson.put("tenantName", tenant.getTenantName());
        tenantJson.put("tenantPhone", tenant.getTenantPhone());
        tenantJson.put("gender", tenant.getGender());
        tenantJson.put("cardType", tenant.getCardType());
        tenantJson.put("cardNo", tenant.getCardNo());
        tenantJson.put("cardFont", tenant.getCardFont());
        tenantJson.put("cardBack", tenant.getCardBack());
        tenantJson.put("auditStatus", tenant.getAuditStatus());

        ajaxResult.put("tenant", tenantJson);
        return ajaxResult;
    }

    /**
     * 封装访客信息
     *
     * @param visitorVO  访客信息
     * @param ajaxResult 返回结果
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private static AjaxResult packageVisitorData(VisitorVO visitorVO, AjaxResult ajaxResult) {
        Assert.notNull(visitorVO, "未查询到访客信息");
        JSONObject visitorJson = new JSONObject();
        visitorJson.put("visitorId", visitorVO.getVisitorId());
        visitorJson.put("visitorName", visitorVO.getVisitorName());
        visitorJson.put("visitorPhone", visitorVO.getVisitorPhone());
        visitorJson.put("cardType", visitorVO.getCardType());
        visitorJson.put("cardNo", visitorVO.getCardNo());

        ajaxResult.put("visitor", visitorJson);
        return ajaxResult;
    }

    @Override
    public List<CommunityVO> selectCommunityList(ChangeDTO changeDTO) {
        // 业主
        if ("0".equals(changeDTO.getAccountType())) {
            return loginMapper.selectCommunityListByOwner(changeDTO.getAreaId(), changeDTO.getUserId());
        }
        // 租客
        return loginMapper.selectCommunityListByTenant(changeDTO.getAreaId(), changeDTO.getUserId());
    }

    @Override
    public AjaxResult updatePassword(UserDTO userDTO) {
        Account account = loginMapper.selectAccountByLoginName(userDTO.getLoginName());
        Assert.notNull(account, "未查询到账号信息");
        if (!SecurityUtils.matchesPassword(userDTO.getOldPass(), account.getPassword())) {
            return AjaxResult.error("原密码错误");
        }
        return updateAccountPassword(account.getAccountId(), userDTO.getNewPass());
        // 业主
//        if ("0".equals(account.getAccountType())) {
//            return updateOwnerPassword(userDTO, account);
//        }
        // 租客
//        if ("1".equals(account.getAccountType())) {
//            return updateVisitorPassword(userDTO, account);
//        }
//        // 访客
//        if ("2".equals(account.getAccountType())) {
//            return updateTenantPassword(userDTO, account);
//        }
//        // 访客--装修工
//        return updateAccountPassword(account.getAccountId(), userDTO.getNewPass());
    }

    /**
     * 修改业主密码
     *
     * @param userDTO 校验数据
     * @param account 账号
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private AjaxResult updateOwnerPassword(UserDTO userDTO, Account account) {
//        Owner owner = loginMapper.selectOwnerByCardNo(userDTO.getIdCardNo());
//        Assert.notNull(owner, "未查询到业主信息");
//        if (!account.getAccountId().equals(owner.getAccountId())) {
//            return AjaxResult.error("手机号与身份证号不匹配");
//        }
        if (!SecurityUtils.matchesPassword(userDTO.getOldPass(), account.getPassword())) {
            return AjaxResult.error("原密码错误");
        }
        return updateAccountPassword(account.getAccountId(), userDTO.getNewPass());
    }

    /**
     * 修改租客密码
     *
     * @param userDTO 校验数据
     * @param account 账号
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private AjaxResult updateTenantPassword(UserDTO userDTO, Account account) {
        Tenant tenant = loginMapper.selectTenantByCardNo(userDTO.getIdCardNo());
        Assert.notNull(tenant, "未查询到租客信息");
//        if (!account.getAccountId().equals(tenant.getAccountId())) {
//            return AjaxResult.error("手机号与身份证号不匹配");
//        }
        if (!SecurityUtils.matchesPassword(userDTO.getOldPass(), account.getPassword())) {
            return AjaxResult.error("原密码错误");
        }
        return updateAccountPassword(account.getAccountId(), userDTO.getNewPass());
    }

    /**
     * 修改访客密码
     *
     * @param userDTO 校验数据
     * @param account 账号
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private AjaxResult updateVisitorPassword(UserDTO userDTO, Account account) {
        Long accountId = loginMapper.selectVisitorAccountByCardNo(userDTO.getIdCardNo());
        Assert.notNull(accountId, "未查询到访客信息");

//        if (!accountId.equals(account.getAccountId())) {
//            return AjaxResult.error("手机号与身份证号不匹配");
//        }
        if (!SecurityUtils.matchesPassword(userDTO.getOldPass(), account.getPassword())) {
            return AjaxResult.error("原密码错误");
        }
        return updateAccountPassword(account.getAccountId(), userDTO.getNewPass());
    }

    private AjaxResult updateAccountPassword(Long accountId, String newPass) {
        String password = SecurityUtils.encryptPassword(newPass);
        int num = loginMapper.updatePassword(accountId, password);
        return num > 0 ? AjaxResult.success("密码修改成功") : AjaxResult.error("密码修改失败");
    }

    @Override
    public AjaxResult logout(String loginName) {
        boolean result = redisService.deleteObject(ApiConstants.LOGIN_KEY + loginName);
        return result ? AjaxResult.success("登出成功") : AjaxResult.error("登出失败");
    }

    @Override
    public AjaxResult selectOwnerByAccountId(Long accountId) {
        AjaxResult ajaxResult = AjaxResult.success();
        Map account = loginMapper.findOneOwnerByAccount( accountId );

        //查询业主信息
        Owner owner = loginMapper.selectOwnerByAccountId(accountId);
        ajaxResult.put("owner", owner);

        //增加业主房产
        if(CollUtil.isNotEmpty( account )){
            List<OwnerHouseVo> ownerId = apiVisitorMapper.ownerInfo( MapUtil.getLong( account, "ownerId" ) );
            ajaxResult.put("houses", ownerId);
        }

        ajaxResult.put("account", account);
        // 查询所属物业id
        Long estateId = loginMapper.selectEstateIdByCommunityId( MapUtil.getLong( account,"communityId" ) );
        account.put("estateId", estateId);
        // 判断是否是党员
        Member member = loginMapper.selectMemberByCommunityId(MapUtil.getLong( account,"accountId" ),MapUtil.getLong( account,"communityId" ));

        account.put("member", member);
        return ajaxResult;
    }

    @Override
    public AjaxResult findRegisterByphone(String phone) {
        return AjaxResult.success(loginMapper.findRegisterByphone(phone) > 0);
    }

    @Override
    public AjaxResult loginByWeChat(ApiUser apiUser) {
        String loginName = apiUser.getLoginName();
        String password = apiUser.getPassword();
        Account account = loginMapper.selectAccountByLoginName(loginName);
        Assert.notNull(account, "未查询到账号信息");

        // 获取登陆后的 token
        String token = Authentication.createToken(loginName, password);
        log.info("token:{}", token);
        redisService.setCacheObject(ApiConstants.LOGIN_KEY + loginName, apiUser);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("token", token);
        String communityName = loginMapper.selectCommunityName(account.getCommunityId());
        // 账号信息
        JSONObject accountJson = new JSONObject();
        accountJson.put("accountId", account.getAccountId());
        accountJson.put("communityId", account.getCommunityId());
        accountJson.put("communityName", communityName);
        accountJson.put("loginName", loginName);
        accountJson.put("avatar", account.getAvatar());
        accountJson.put("accountType", account.getAccountType());
        accountJson.put("accountName", account.getAccountName());
        accountJson.put("accountNick", account.getAccountNick());
        accountJson.put("volunteer", account.getVolunteer());
        // 查询所属物业id
        Long estateId = loginMapper.selectEstateIdByCommunityId(account.getCommunityId());
        accountJson.put("estateId", estateId);
        // 判断是否是党员
        Member member = loginMapper.selectMemberByAccountId(account.getAccountId());
        packageMemberData(accountJson, member);
        ajaxResult.put("account", accountJson);
        // 业主登录
        Owner owner;
        if ("0".equals(account.getAccountType())) {
            owner = loginMapper.selectOwnerByAccountId(account.getAccountId());
            Assert.notNull(owner, "未查询到业主信息");
            /*if ("0".equals(owner.getAuditStatus())) {
                return AjaxResult.error("业主信息审核中");
            }
            if ("2".equals(owner.getAuditStatus())) {
                return AjaxResult.error("业主信息审核不通过", owner.getRejectReason());
            }*/
            JSONObject ownerJson = packageOwnerData(owner);
            // 判断是个否是业主委员会成员
            Long committeeId = loginMapper.selectCommitteeIdByOwnerId(owner.getOwnerId());
            // 查询业主委员会信息
            ownerJson.put("committeeId", committeeId);
            ajaxResult.put("owner", ownerJson);
            return ajaxResult;
        } else {
            ajaxResult.put("owner", null);
        }
        // 租客登录
        Tenant tenant;
        if ("1".equals(account.getAccountType())) {
            tenant = loginMapper.selectTenantByAccountId(account.getAccountId());
            return packageTenantData(tenant, ajaxResult);
        } else {
            ajaxResult.put("tenant", null);
        }
        // 访客登录
        VisitorVO visitorVO;
        if ("2".equals(account.getAccountType())) {
            visitorVO = loginMapper.selectVisitorByAccountId(account.getAccountId());
            return packageVisitorData(visitorVO, ajaxResult);
        } else {
            ajaxResult.put("visitorVO", null);
        }
        return ajaxResult;
    }

    @Override
    @Transactional
    public AjaxResult updatePasswordByIDCard(UserDTO userDTO) {
        Account account = loginMapper.selectAccountByLoginName(userDTO.getLoginName());
        Assert.notNull(account, "未查询到账号信息！");

        VisitorVO visitorVO = loginMapper.selectVisitorByAccountId( account.getAccountId() );
        Assert.notNull(visitorVO, "获取身份账号信息失败，请稍后重试！");

        if (!StringUtils.equals( userDTO.getIdCardNo(),visitorVO.getCardNo() )) {
            return AjaxResult.error("账号与身份信息不符，请检查后重新修改！");
        }
        return updateAccountPassword(account.getAccountId(), userDTO.getNewPass());
    }
}
