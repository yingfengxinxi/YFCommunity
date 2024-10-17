package com.wr.govern.web.member;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.member.RejectedService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.party.vo.MemberVO;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-27 20:14:11
 * @Desc: 审核未通过 控制层
 */
@RestController
@RequestMapping("/rejected")
public class RejectedController extends BaseController {
    @Autowired
    private RejectedService rejectedService;

    /**
     * 获取入党审核列表
     *
     * @param memberVO
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("govern:rejected:list")
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
        List<MemberVO> memberAll = rejectedService.getMemberAll(memberVO);
        return getDataTable(memberAll);
    }
}
