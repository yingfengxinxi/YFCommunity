package com.wr.govern.web.partypay;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.partypay.StatisticsService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.PartyPay;
import com.wr.remote.govern.party.vo.MemberVO;
import com.wr.remote.govern.party.vo.PartyPayVO;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-01 17:03:49
 * @Desc: 党员归集-激费统计 控制层
 */
@RestController
@RequestMapping("/partyPay")
public class StatisticsController extends BaseController {
    @Autowired
    private StatisticsService partyMoneyService;

    /**
     * 获取激费列表
     *
     * @param partyPay
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("govern:partyPay:list")
    @GetMapping("/list")
    public TableDataInfo list(PartyPay partyPay) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        partyPay.setEstateId(estateId);
        startPage();
        List<PartyPayVO> partyPayAll = partyMoneyService.getPartyPayAll(partyPay);
        return getDataTable(partyPayAll);
    }

    /**
     * 查询统计数据
     *
     * @param partyPay
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping(value = "/statistics")
    public AjaxResult statistics(PartyPay partyPay) {
        if (ObjectUtils.isEmpty(partyPay.getCommunityId())) {
            return AjaxResult.error("请选择小区！");
        }
        return partyMoneyService.queryStatisticsByDate(partyPay);
    }

    /**
     * 获取党组织
     * @param communityId 所属小区
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping(value = "/partyList/{communityId}")
    public AjaxResult partyList(@PathVariable Long communityId) {
        if (ObjectUtils.isEmpty(communityId)) {
            return AjaxResult.error("参数不能为空");
        }
        List<Party> partyAll = partyMoneyService.getPartyAll(communityId);
        return AjaxResult.success(partyAll);
    }

    /**
     * 查党员数据
     *
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping(value = "/memberList/{partyId}")
    public AjaxResult memberList(@PathVariable Long partyId) {
        if (ObjectUtils.isEmpty(partyId)) {
            return AjaxResult.error("请选择党组织");
        }
        List<MemberVO> memberAll = partyMoneyService.getMemberAll(partyId);
        return AjaxResult.success(memberAll);
    }

    /**
     * 新建激费
     *
     * @param partyPay
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "激费统计", businessType = BusinessType.INSERT)
    @RequiresPermissions("govern:partyPay:add")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody PartyPay partyPay) {
        AjaxResult ajaxResult = partyMoneyService.addParyPay(partyPay);
        return ajaxResult;
    }

    /**
     * 归集
     *
     * @param logIds
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "激费统计", businessType = BusinessType.UPDATE)
    @RequiresPermissions("govern:partyPay:edit")
    @PutMapping(value = "/{logIds}")
    public AjaxResult add(@PathVariable Long[] logIds) {
        Integer num = partyMoneyService.upCollectStatus(logIds);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
