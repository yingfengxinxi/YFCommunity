package com.wr.govern.web.member;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.member.AuditService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.party.Member;
import com.wr.remote.govern.party.vo.MemberVO;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-27 16:53:09
 * @Desc: 入党审核 控制层
 */
@RestController
@RequestMapping("/audit")
public class AuditController extends BaseController {
    @Autowired
    private AuditService auditService;

    /**
     * 获取入党审核列表
     *
     * @param memberVO
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("govern:audit:list")
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
        List<MemberVO> memberAll = auditService.getMemberAll(memberVO);
        return getDataTable(memberAll);
    }

    /**
     * 获取详细信息
     */
    @RequiresPermissions("govern:audit:query")
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable Long memberId) {
        MemberVO memberById = auditService.getMemberById(memberId);
        return AjaxResult.success(memberById);
    }

    /**
     * 审核通过
     *
     * @param memberId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "入党审核-审核通过", businessType = BusinessType.UPDATE)
    @PutMapping("/through/{memberId}")
    public AjaxResult through(@PathVariable Long memberId) {
        Integer num = auditService.through(memberId);
        return toAjax(num);
    }

    /**
     * 驳回
     *
     * @param member
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "入党审核-驳回", businessType = BusinessType.UPDATE)
    @PutMapping("/rejected")
    public AjaxResult rejected(@RequestBody Member member) {
        Integer num = auditService.rejected(member);
        return toAjax(num);
    }
}
