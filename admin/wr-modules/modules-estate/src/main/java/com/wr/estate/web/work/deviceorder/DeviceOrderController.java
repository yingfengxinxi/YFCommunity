package com.wr.estate.web.work.deviceorder;

import java.util.List;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.entity.vo.DeviceCodeVO;
import com.wr.estate.entity.vo.DeviceOrderVO;
import com.wr.estate.service.work.deviceorder.DeviceOrderService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.deviceorder.DeviceOrder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 设备维修工单Controller
 *
 * @author 典韦
 * @date 2022-10-27
 */
@RestController
@RequestMapping("/work/deviceOrder")
public class DeviceOrderController extends BaseController {
    @Autowired
    private DeviceOrderService deviceOrderService;

    /**
     * 查询设备维修工单列表
     */
    @PreAuthorize("@ss.hasPermi('work:deviceOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceOrderVO deviceOrder) {
        startPage();
        List<DeviceOrderVO> list = deviceOrderService.selectDeviceOrderList(deviceOrder);
        return getDataTable(list);
    }

    /**
     * 查询设备维修工单列表
     */
    @GetMapping("/codeList")
    public AjaxResult codeList(@RequestParam("deviceType") String deviceType, @RequestParam("communityId") Long communityId) {
        List<DeviceCodeVO> list = deviceOrderService.selectDeviceOrderCodeList(deviceType, communityId);
        return AjaxResult.success(list);
    }

    /**
     * 获取设备维修工单详细信息
     */
    @PreAuthorize("@ss.hasPermi('work:deviceOrder:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return AjaxResult.success(deviceOrderService.selectDeviceOrderByOrderId(orderId));
    }

    /**
     * 新增设备维修工单
     */
    @PreAuthorize("@ss.hasPermi('work:deviceOrder:add')")
    @Log(title = "设备维修工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceOrder deviceOrder) {
        return toAjax(deviceOrderService.insertDeviceOrder(deviceOrder));
    }

    /**
     * 修改设备维修工单
     */
    @PreAuthorize("@ss.hasPermi('work:deviceOrder:edit')")
    @Log(title = "设备维修工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceOrder deviceOrder) {
        return toAjax(deviceOrderService.updateDeviceOrder(deviceOrder));
    }

    /**
     * 删除设备维修工单
     */
    @PreAuthorize("@ss.hasPermi('work:deviceOrder:remove')")
    @Log(title = "设备维修工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(deviceOrderService.deleteDeviceOrderByOrderIds(orderIds));
    }

    /**
     * 查询工单协助人信息
     */
    @GetMapping("/assistList")
    public AjaxResult selectAssistList(@RequestParam("orderType") String orderType, @RequestParam("orderId") Long orderId) {
        List<Staff> list = deviceOrderService.selectAssistList(orderType, orderId);
        return AjaxResult.success(list);
    }
}
