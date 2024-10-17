package com.wr.govern.web.partypay;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.partypay.MemberFeeService;
import com.wr.remote.govern.party.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: SJiang
 * @Date: 2022-11-03 14:13:32
 * @Desc: 党费归集-党费设置 控制层
 */
@RestController
@RequestMapping("/memberFee")
public class MemberFeeController extends BaseController {
    @Autowired
    private MemberFeeService memberFeeService;

    /**
     * 党费设置
     *
     * @param member
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "党费设置", businessType = BusinessType.UPDATE)
    @RequiresPermissions("govern:memberFee:edit")
    @PutMapping(value = "/edit")
    public AjaxResult edit(@RequestBody Member member) {
        Integer num = memberFeeService.partyFeeEdit(member);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
