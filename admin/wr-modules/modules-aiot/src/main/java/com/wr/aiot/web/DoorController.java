package com.wr.aiot.web;

import com.wr.aiot.service.DoorService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Door;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:03:07
 * @Desc: 门禁点管理
 */
@RestController
@RequestMapping("/door")
@Api(tags = "监控点管理")
public class DoorController extends BaseController {
    @Autowired
    private DoorService doorService;

    /**
     * @param door
     * @return
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(Door door) {
        startPage();
        List<Door> list = doorService.selectDoorList(door);
        return getDataTable(list);
    }

    /**
     * 设备同步
     */
    @ApiOperation("设备同步")
    @Log(title = "门禁点", businessType = BusinessType.INSERT)
    @PostMapping("/sync_data/{communityId}")
    public AjaxResult syncData(@PathVariable Long communityId) {
        try {
            return doorService.syncDoorData(communityId);
        } catch (Exception e) {
            return AjaxResult.error(510, e.getMessage());
        }

    }

    /**
     * 检测设备状态
     *
     * @param door 门禁点信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("检测设备状态")
    @PostMapping("/online")
    public AjaxResult checkOnline(@RequestBody Door door) {

        try {
            return doorService.checkOnline(door);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id查询数据
     */
    @ApiOperation("根据id查询数据")
    @GetMapping(value = "/{doorId}")
    public AjaxResult getInfo(@PathVariable Long doorId) {

        return AjaxResult.success(doorService.selectDoorById(doorId));
    }

    /**
     * 更新门禁点
     */
    @ApiOperation("更新门禁点")
    @Log(title = "门禁点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Door door) {
        door.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(doorService.updateDoor(door));
    }

    /**
     * 查询小区下所有门禁点
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("查询小区下所有门禁点")
    @GetMapping(value = "/all/{communityId}")
    public AjaxResult all(@PathVariable Long communityId) {

        return AjaxResult.success(doorService.selectAllDoorList(communityId));
    }

}
