package com.wr.aiot.web;

import com.wr.aiot.service.BarrierService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Barrier;
import com.wr.remote.aiot.Roadway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:03:07
 * @Desc: 道闸管理
 */
@RestController
@RequestMapping("/barrier")
public class BarrierController extends BaseController {
    @Autowired
    private BarrierService barrierService;


    @GetMapping("/list")
    public TableDataInfo list(Barrier barrier) {
        startPage();
        List<Barrier> list = barrierService.selectBarrierList(barrier);
        return getDataTable(list);
    }

    /**
     * 设备同步
     */
    @Log(title = "道闸", businessType = BusinessType.INSERT)
    @PostMapping("/sync_data/{communityId}")
    public AjaxResult syncData(@PathVariable Long communityId) {

        return barrierService.syncBarrierData(communityId);
    }

    /**
     * 根据id查询数据
     */
    @GetMapping(value = "/{barrierId}")
    public AjaxResult getInfo(@PathVariable Long barrierId) {

        return AjaxResult.success(barrierService.selectBarrierById(barrierId));
    }

    /**
     * 更新道闸
     */
    @Log(title = "道闸", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Barrier barrier) {
        barrier.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(barrierService.updateBarrier(barrier));
    }

    /**
     * 批量设置道闸设备二维码
     */
    @Log(title = "道闸", businessType = BusinessType.UPDATE)
    @PutMapping("/qr")
    public AjaxResult qrCode(@RequestBody Barrier barrier) {
        return toAjax(barrierService.batchUpdateDeviceQr(barrier));
    }

    @GetMapping(value = "/unbid_roadway")
    public AjaxResult unbidRoadway(Barrier barrier) {
        List<Roadway> roadwayList = barrierService.selectUnbidRoadway(barrier);
        return AjaxResult.success(roadwayList);
    }

}
