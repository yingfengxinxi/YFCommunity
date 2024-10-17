package com.wr.aiot.web;

import com.wr.aiot.service.CameraService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Camera;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:03:07
 * @Desc: 监控点管理
 */
@RestController
@RequestMapping("/camera")
@Api(tags = "物联网-监控点管理")
public class CameraController extends BaseController {
    @Autowired
    private CameraService cameraService;


    @GetMapping("/list")
    @ApiOperation("列表")
    public TableDataInfo list(Camera camera) {
        startPage();
        List<Camera> list = cameraService.selectCameraList(camera);
        return getDataTable(list);
    }

    /**
     * 设备同步
     */

    @Log(title = "监控点", businessType = BusinessType.INSERT)
    @ApiOperation("同步监控点")
    @PostMapping("/sync_data/{communityId}")
    public AjaxResult syncData(@PathVariable Long communityId) {

        try {
            return cameraService.syncCameraData(communityId);
        } catch (Exception e) {
            return AjaxResult.error(510, e.getMessage());
        }
    }

    /**
     * 更新设备状态
     *
     * @param camera 设备
     * @return com.wr.common.core.web.domain.AjaxResult
     */

    @PostMapping("/online")
    public AjaxResult checkOnline(@RequestBody Camera camera) {

        return cameraService.checkOnline(camera);
    }

    /**
     * 根据id查询数据
     */

    @GetMapping(value = "/{cameraId}")
    public AjaxResult getInfo(@PathVariable Long cameraId) {

        return AjaxResult.success(cameraService.selectCameraById(cameraId));
    }

    /**
     * 更新监控点
     */

    @Log(title = "监控点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Camera camera) {
        camera.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(cameraService.updateCamera(camera));
    }

    /**
     * 批量设置监控点设备二维码
     */

    @Log(title = "监控点", businessType = BusinessType.UPDATE)
    @PutMapping("/qr")
    public AjaxResult qrCode(@RequestBody Camera camera) {
        return toAjax(cameraService.batchUpdateDeviceQr(camera));
    }

    /**
     * 预览
     */

    @GetMapping(value = "/play")
    public AjaxResult getInfo(Camera camera) {

        return cameraService.selectCameraUrl(camera);
    }

}
