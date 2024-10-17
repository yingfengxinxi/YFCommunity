package com.wr.estate.web.manage.meter;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.manage.meter.MeterRuleService;
import com.wr.remote.estate.manage.meter.MeterRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-25 09:50:28
 * @Desc: 抄表设置控制层
 */
@RestController
@RequestMapping("/rule")
public class MeterRuleController {

    @Autowired
    private MeterRuleService meterRuleService;

    /**
     * 抄表设置详情
     *
     * @return
     */
    @RequiresPermissions("business:rule:info")
    @GetMapping("/getInfo")
    public AjaxResult Info(){
        MeterRuleVo meterRuleVo = meterRuleService.selectMeterRuleInfo();
        return AjaxResult.success(meterRuleVo);
    }

    /**
     * 抄表设置
     *
     * @return
     */
    @Log(title = "抄表设置", businessType = BusinessType.UPDATE)
    @PostMapping("/setting")
    public AjaxResult SettingRule(@RequestBody MeterRuleVo meterRuleVo){
        return AjaxResult.success(meterRuleService.settingRule(meterRuleVo));
    }
}
