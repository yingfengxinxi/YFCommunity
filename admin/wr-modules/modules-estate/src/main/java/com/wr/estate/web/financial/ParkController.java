package com.wr.estate.web.financial;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.estate.entity.ParkPayEntity;
import com.wr.estate.service.financial.ParkService;
import com.wr.remote.domain.SysUser;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/2
 * @Description:
 * @FileName: ParkController
 * @History:
 */
@RestController
@RequestMapping("/park")
public class ParkController extends BaseController {

    @Autowired
    ParkService parkService;

    @GetMapping("/parkpaylist")
    @PreAuthorize("@ss.hasPermi('system:park:list')")
    public TableDataInfo parkpaylist(ParkPayEntity parkPayEntity) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.allNull(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        parkPayEntity.setEstateId(estateId);
        startPage();
        List<ParkPayEntity> list = parkService.selectParkPay(parkPayEntity);
        return getDataTable(list);
    }

    @GetMapping("/parkdurationlist/{communityId}")
    public TableDataInfo parkdurationlist(@PathVariable("communityId") Long communityId) {
        startPage();
        ParkDurationEntity parkDurationEntity = new ParkDurationEntity();
        parkDurationEntity.setCommunityId(communityId);
        List<ParkDurationEntity> list = parkService.selectParkDuration(parkDurationEntity);
        return getDataTable(list);
    }


    @PostMapping("/addparkpay")
    @PreAuthorize("@ss.hasPermi('system:park:add')")
    public AjaxResult addparkpay(@RequestBody ParkPayEntity parkPayEntity) {
        int row = parkService.addParkPay(parkPayEntity);
        if (row == -2) {
            return AjaxResult.error(555, "该小区已被添加");
        } else {
            return toAjax(row);
        }
    }

    @PutMapping("/updateparkpay")
    @PreAuthorize("@ss.hasPermi('system:park:update')")
    public AjaxResult updateparkpay(@RequestBody ParkPayEntity parkPayEntity) {
        return toAjax(parkService.updateParkPay(parkPayEntity));
    }

    @PostMapping("/addParkDuration")
    @PreAuthorize("@ss.hasPermi('system:park:addparkduration')")
    public AjaxResult addParkDuration(@RequestBody ParkDurationEntity parkDurationEntity) {
        double v = parkDurationEntity.getHourFee().doubleValue();
        if (v>99999){
            return AjaxResult.error("费用不能大于99999");
        }
        int row = parkService.addParkDuration(parkDurationEntity);
        if (row == -1) {
            return AjaxResult.error("规则最多不能超过六个");
        }
        if (row == -3) {
            return AjaxResult.error("结束时间不能小于开始计时时间");
        }
        if (row == -2) {
            return AjaxResult.error("该规则时间范围已存在");
        } else {
            return toAjax(row);
        }
    }

    @DeleteMapping("delPd/{durationId}")
    public AjaxResult delPd(@PathVariable("durationId") Long durationId) {
        int i = parkService.delPd(durationId);
        if (i <= 0) {
            return AjaxResult.error("删除失败");
        } else {
            return AjaxResult.success("删除成功");
        }
    }


    @PutMapping("/xgfy")
    @PreAuthorize("@ss.hasPermi('system:park:updatefree')")
    public AjaxResult xgyf(@RequestBody ParkDurationEntity parkDurationEntity) {
        double v = parkDurationEntity.getHourFee().doubleValue();
        if (v>99999){
            return AjaxResult.error("费用不能大于99999");
        }
        return toAjax(parkService.xgfy(parkDurationEntity));
    }
}
