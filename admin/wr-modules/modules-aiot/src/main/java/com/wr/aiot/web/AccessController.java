package com.wr.aiot.web;

import com.wr.aiot.service.AccessService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:03:07
 * @Desc: 门禁信息管理
 */
@RestController
@RequestMapping("/access")
@Api(tags = "门禁信息管理")
public class AccessController extends BaseController {
    @Autowired
    private AccessService accessService;


    @ApiOperation("分页列表")
    @GetMapping("/list")
    public TableDataInfo list(Access access) {
        return listPage(access);
    }

    /**
     * 物管平台使用
     *
     * @param access
     * @return
     */
    @ApiOperation("分页列表")
    @GetMapping("/estateListPage")
    public TableDataInfo estateListPage(Access access) {
        return listPage(access);
    }

    public TableDataInfo listPage(Access access) {
        startPage();
        List<Access> list = accessService.selectAccessList(access);
        return getDataTable(list);
    }

    /**
     * 设备同步
     */
    @ApiOperation("设备同步")
    @Log(title = "门禁信息", businessType = BusinessType.INSERT)
    @PostMapping("/sync_data/{communityId}")
    public AjaxResult syncData(@PathVariable Long communityId) {

        try {
            return accessService.syncAccessData(communityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新设备状态
     *
     * @param access 门禁信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("更新设备状态")
    @PostMapping("/online")
    public AjaxResult checkOnline(@RequestBody Access access) {

        try {
            return accessService.checkOnline(access);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id查询数据
     */
    @ApiOperation("根据id查询数据")
    @GetMapping(value = "/{accessId}")
    public AjaxResult getInfo(@PathVariable Long accessId) {

        return AjaxResult.success(accessService.selectAccessById(accessId));
    }

    /**
     * 更新门禁
     */
    @ApiOperation("更新门禁")
    @Log(title = "门禁信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Access access) {
        access.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(accessService.updateAccess(access));
    }

    /**
     * 批量更新门禁
     */
    @ApiOperation("批量更新门禁信息")
    @Log(title = "门禁信息", businessType = BusinessType.UPDATE)
    @PutMapping("/batch")
    public AjaxResult batch(@RequestBody Access access) {
        access.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(accessService.batchUpdateAccess(access));
    }

    /**
     * 批量设置门禁设备二维码
     */
    @ApiOperation("批量设置门禁设备二维码")
    @Log(title = "门禁信息", businessType = BusinessType.UPDATE)
    @PutMapping("/qr")
    public AjaxResult qrCode(@RequestBody Access access) {
        return toAjax(accessService.batchUpdateDeviceQr(access));
    }

    /**
     * 合法卡比对事件订阅 198914
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("门禁事件订阅")
    @Log(title = "门禁信息", businessType = BusinessType.OTHER)
    @PostMapping("/subscribe/{communityId}")
    public AjaxResult subscribe(@PathVariable Long communityId) {

        return accessService.subscribeLegalCard(communityId);
    }

    /**
     * 合法卡比对事件取消订阅 198914
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("合法卡比对事件取消订阅")
    @Log(title = "门禁信息", businessType = BusinessType.OTHER)
    @PostMapping("/unsubscribe/{communityId}")
    public AjaxResult unsubscribe(@PathVariable Long communityId) {
        return accessService.unsubscribeLegalCard(communityId);
    }

    /**
     * 查询小区下未绑定门禁的门禁点
     *
     * @param access 门禁
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("查询小区下未绑定门禁的门禁点")
    @GetMapping(value = "/door")
    public AjaxResult all(Access access) {

        return AjaxResult.success(accessService.selectUnbindDoors(access));
    }

}
