package com.wr.estate.web.customer.Venue;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.VenueRuleEntity;
import com.wr.estate.service.customer.Venue.VenueRuleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueRuleController
 * @History:
 */
@RestController
@RequestMapping("/rule")
public class VenueRuleController extends BaseController {
    @Autowired
    private VenueRuleService venueRuleService;

    /**
     * 查看场馆预约规则
     */
    // @PreAuthorize("@ss.hasPermi('system:rule:list')")
    @GetMapping("/list")
    public AjaxResult list() {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        VenueRuleEntity ruleEntity = venueRuleService.selectVenueRule(estateId);
        return AjaxResult.success(ruleEntity);
    }


    /**
     * 获取配置规则详细信息
     */
    // @PreAuthorize("@ss.hasPermi('system:rule:query')")
    @GetMapping(value = "/{ruleId}")
    public AjaxResult getInfo(@PathVariable("ruleId") Long ruleId) {
        return AjaxResult.success(venueRuleService.selectVenueRuleByRuleId(ruleId));
    }

    /**
     * 新增配置规则
     */
    //@PreAuthorize("@ss.hasPermi('system:rule:add')")
    @Log(title = "配置规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add() {
        return toAjax(venueRuleService.insertVenueRule(new VenueRuleEntity()));
    }

    /**
     * 修改配置规则
     */
    @PreAuthorize("@ss.hasPermi('system:rule:edit')")
    @Log(title = "配置规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VenueRuleEntity venueRule) {
        return toAjax(venueRuleService.updateVenueRule(venueRule));
    }

    /**
     * 删除配置规则
     */
    // @PreAuthorize("@ss.hasPermi('system:rule:remove')")
    @Log(title = "配置规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds) {
        return toAjax(venueRuleService.deleteVenueRuleByRuleIds(ruleIds));
    }
}
