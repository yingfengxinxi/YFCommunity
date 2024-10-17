package com.wr.aiot.web;

import com.wr.aiot.service.VisualService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Visual;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:03:07
 * @Desc: 可视对讲管理
 */
@RestController
@RequestMapping("/visual")
@Api(tags = "可视对讲管理")
public class VisualController extends BaseController {
    @Autowired
    private VisualService visualService;

    /**
     * @param visual
     * @return
     */
    @ApiOperation("分页列表")
    @GetMapping("/list")
    public TableDataInfo list(Visual visual) {
        startPage();
        List<Visual> list = visualService.selectVisualList(visual);
        return getDataTable(list);
    }

    /**
     * 设备同步
     */
    @ApiOperation("设备同步")
    @Log(title = "可视对讲", businessType = BusinessType.INSERT)
    @PostMapping("/sync_data/{communityId}")
    public AjaxResult syncData(@PathVariable Long communityId) {

        try {
            return visualService.syncVisualData(communityId);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(510, e.getMessage());
        }
    }

    /**
     * 更新设备状态
     *
     * @param visual 信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("更新设备状态")
    @PostMapping("/online")
    public AjaxResult checkOnline(@RequestBody Visual visual) {

        try {
            return visualService.checkOnline(visual);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id查询数据
     */
    @ApiOperation("根据id查询数据")
    @GetMapping(value = "/{visualId}")
    public AjaxResult getInfo(@PathVariable Long visualId) {

        return AjaxResult.success(visualService.selectVisualById(visualId));
    }

    /**
     * 更新可视对讲
     */
    @ApiOperation("更新可视对讲")
    @Log(title = "可视对讲", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Visual visual) {
        visual.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(visualService.updateVisual(visual));
    }

    /**
     * 批量设置可视对讲设备二维码
     */
    @ApiOperation("批量设置可视对讲设备二维码")
    @Log(title = "可视对讲", businessType = BusinessType.UPDATE)
    @PutMapping("/qr")
    public AjaxResult qrCode(@RequestBody Visual visual) {
        return toAjax(visualService.batchUpdateDeviceQr(visual));
    }

    /**
     * 可视对讲开锁事件订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("可视对讲开锁事件订阅")
    @Log(title = "可视对讲", businessType = BusinessType.OTHER)
    @PostMapping("/subscribe/{communityId}")
    public AjaxResult subscribe(@PathVariable Long communityId) {
        return AjaxResult.error("演示模式！！");
        //return visualService.subscribeLock(communityId);
    }

    /**
     * 可视对讲开锁事件取消订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("可视对讲开锁事件取消订阅")
    @Log(title = "可视对讲", businessType = BusinessType.OTHER)
    @PostMapping("/unsubscribe/{communityId}")
    public AjaxResult unsubscribe(@PathVariable Long communityId) {
        return AjaxResult.error("演示模式！！");
        //return visualService.unsubscribeLock(communityId);
    }

}
