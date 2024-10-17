package com.wr.govern.web.member;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.common.core.constant.ScheduleConstants;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.member.MemberService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.customer.decorate.Account;
import com.wr.remote.govern.party.Member;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.vo.MemberVO;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-26 16:18:42
 * @Desc: 党员管理 控制层
 */
@RestController
@RequestMapping("/member")
public class MemberController extends BaseController {
    @Autowired
    private MemberService memberService;

    /**
     * 获取党员列表
     *
     * @param memberVO
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("govern:member:list")
    @GetMapping("/list")
    public TableDataInfo list(MemberVO memberVO) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if(ObjectUtils.isEmpty(sysUser)){
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        memberVO.setEstateId(estateId);
        startPage();
        List<MemberVO> memberAll = memberService.getMemberAll(memberVO);
        return getDataTable(memberAll);
    }

    /**
     * 获取党组织
     *
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @GetMapping("/partyAll")
    public AjaxResult partyAll() {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if(ObjectUtils.isEmpty(sysUser)){
            return null;
        }
        Long estateId = sysUser.getEstateId();
        List<Party> partyAll = memberService.getPartyAll(estateId);
        return AjaxResult.success(partyAll);
    }

    /**
     * 获取详细信息
     */
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable Long memberId) {
        MemberVO memberById = memberService.getMemberById(memberId);
        return AjaxResult.success(memberById);
    }

    /**
     * 获取用户
     */
    @GetMapping(value = "/getOwnerAll")
    public AjaxResult getOwnerAll(Member member) {
        List<Account> ownerAll = memberService.getOwnerAll(member);
        return AjaxResult.success(ownerAll);
    }

    /**
     * 添加党员
     *
     * @param member
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:member:add")
    @Log(title = "党员管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Member member) {
        // 获取账号信息
        Account accountById = memberService.getAccountById(member.getAccountId());
        if (UserConstants.NOT_UNIQUE.equals(memberService.checkNameUnique(member))) {
            return AjaxResult.error("添加党员'" + accountById.getAccountName() + "'失败，该党员已建立");
        }
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        member.setCreateBy(userName);
        member.setUpdateBy(userName);
        Integer num = memberService.addMember(member);
        return toAjax(num);
    }

    /**
     * 修改
     *
     * @param member
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:member:edit")
    @Log(title = "党员管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody Member member) {
        // 获取账号信息
        Account accountById = memberService.getAccountById(member.getAccountId());
        if (UserConstants.NOT_UNIQUE.equals(memberService.checkNameUnique(member))) {
            return AjaxResult.error("修改党员'" + accountById.getAccountName() + "'失败，该党员已建立");
        }
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        member.setUpdateBy(userName);
        Integer num = memberService.upMember(member);
        return toAjax(num);
    }

    /**
     * 删除党员
     *
     * @param member
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "删除党员", businessType = BusinessType.DELETE)
    @PostMapping("/deleteMember")
    public AjaxResult deleteMember(@RequestBody Member member) {
        if(member == null || member.getMemberId() == null){
            throw new ServiceException("获取党员信息id失败，请刷新页面重试！");
        }

        LambdaUpdateWrapper<Member> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq( Member::getMemberId,member.getMemberId() )
                .set(Member::getDelFlag, ScheduleConstants.Status.PAUSE.getValue() );
        boolean remove = memberService.update( wrapper );
        return toAjax(remove);
    }
}
