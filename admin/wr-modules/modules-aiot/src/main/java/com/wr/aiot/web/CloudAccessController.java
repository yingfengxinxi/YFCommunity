package com.wr.aiot.web;

import com.wr.aiot.service.CloudAccessService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.remote.aiot.CloudAccess;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:03:07
 * @Desc: 云+门禁信息管理
 */
@RestController
@RequestMapping("/cloud")
@Api(tags = "云+门禁信息管理")
public class CloudAccessController extends BaseController {
    @Autowired
    private CloudAccessService accessService;


    @GetMapping("/list")
    public TableDataInfo list(CloudAccess cloudAccess) {
        startPage();
        List<CloudAccess> list = accessService.selectCloudAccessList(cloudAccess);
        return getDataTable(list);
    }

    /**
     * 设备添加
     */

    @Log(title = "云+门禁信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CloudAccess cloudAccess) {

        return toAjax(accessService.insertCloudAccess(cloudAccess));
    }

    /**
     * 根据id查询数据
     */

    @GetMapping(value = "/{accessId}")
    public AjaxResult getInfo(@PathVariable Long accessId) {

        return AjaxResult.success(accessService.selectCloudAccessById(accessId));
    }

    /**
     * 更新门禁
     */

    @Log(title = "云+门禁信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CloudAccess cloudAccess) {

        return toAjax(accessService.updateCloudAccess(cloudAccess));
    }

    /**
     * 批量设置门禁设备二维码
     */

    @Log(title = "云+门禁信息", businessType = BusinessType.UPDATE)
    @PutMapping("/qr")
    public AjaxResult qrCode(@RequestBody CloudAccess cloudAccess) {
        accessService.batchUpdateDeviceQr(cloudAccess);
        return AjaxResult.success();
    }

    /**
     * 批量删除门禁
     */

    @Log(title = "云+门禁信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult batch(@PathVariable("ids") Long[] ids) {

        return toAjax(accessService.removeCloudAccess(ids));
    }

    /**
     * 查询小区下未绑定门禁的门禁点
     * @param cloudAccess 门禁
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping(value = "/door")
    public AjaxResult door(CloudAccess cloudAccess) {

        return AjaxResult.success(accessService.selectUnbindDoors(cloudAccess));
    }

}
