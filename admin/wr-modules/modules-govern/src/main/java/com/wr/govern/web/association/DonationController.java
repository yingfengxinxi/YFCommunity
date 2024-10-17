package com.wr.govern.web.association;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.association.DonationService;
import com.wr.remote.govern.association.Donation;
import com.wr.remote.govern.association.vo.DonationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-28 09:24:57
 * @Desc: 业主捐赠控制层
 */
@RestController
@RequestMapping("/donation")
public class DonationController extends BaseController {

    @Autowired
    private DonationService donationService;

    /**
     * 获取业主捐赠列表
     * @param donation
     * @return
     */
    @RequiresPermissions("govern:donation:list")
    @GetMapping("/list")
    public TableDataInfo getList(Donation donation){
        startPage();
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        donation.setEstateId(estateId);
        List<Donation> donationList=donationService.getList(donation);
        return getDataTable(donationList);
    }

    /**
     * 添加业主捐赠信息
     * @param donationVO
     * @return
     */
    @RequiresPermissions("govern:donation:add")
    @Log(title = "添加业主捐赠信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addDonation(@Validated @RequestBody DonationVO donationVO){
        return toAjax(donationService.addDonation(donationVO));
    }

    /**
     * 修改业主捐赠信息
     * @param donation
     * @return
     */
    @RequiresPermissions("govern:donation:edit")
    @Log(title = "修改业主捐赠信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateDonation(@Validated @RequestBody DonationVO donation){
        return toAjax(donationService.updateDonation(donation));
    }

    /**
     * 业主捐赠信息详情
     * @param donationId
     * @return
     */
    @RequiresPermissions("govern:donation:list")
    @GetMapping("/getDonationDetail/{donationId}")
    public AjaxResult getDonationDetail(@PathVariable Long donationId){
        return AjaxResult.success(donationService.getDonationDetail(donationId));
    }

    /**
     * 删除业主捐赠信息
     * @param donationId
     * @return
     */
    @RequiresPermissions("govern:donation:remove")
    @Log(title = "删除业主捐赠信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{donationId}")
    public AjaxResult delDonation(@PathVariable Long donationId){
        return toAjax(donationService.delDonation(donationId));
    }

}
