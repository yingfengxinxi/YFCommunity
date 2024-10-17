package com.wr.estate.web.work.inspect;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.service.work.inspect.InspectService;
import com.wr.remote.work.vo.InspectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 巡检工单
 * @Author: lvzy
 * @Date: 2022-10-25 11:22:34
 * @Desc: 巡检工单
 */
@RestController
@RequestMapping("/inspect/ins")
public class InspectController extends BaseController {

    @Autowired
    private InspectService service;

    @GetMapping("/listPage")
    public TableDataInfo listPage(InspectVo inspectVo) {
        startPage();
        List<InspectVo> listPage = service.listPage(inspectVo);
        return getDataTable(listPage);
    }

    /**
     * 获取设备类型
     * @return
     */
    @GetMapping("/deviceType")
    public AjaxResult deviceType() {
        return AjaxResult.success(service.deviceType());
    }

    /**
     * 获取设备编号
     * @return
     */
    @GetMapping("/deviceNo")
    public AjaxResult deviceNo(InspectVo inspectVo) {
        return AjaxResult.success(service.deviceNo(inspectVo.getDeviceType(), inspectVo.getCommunityId()));
    }

    /**
     * 新增或修改
     * @param inspectVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('estate:ins:add')")
    @Log(title = "新增或修改巡检工单", businessType = BusinessType.INSERT)
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveOrUpdate(@RequestBody InspectVo inspectVo) {
        return service.saveOrUpdate(inspectVo);
    }

    /**
     * 设备详情
     * @param inspectVo
     * @return
     */
    @GetMapping("/detailList")
    public AjaxResult detailList(InspectVo inspectVo) {
        List<InspectVo> inspectVos = service.detailList(inspectVo.getInspectId());
        return AjaxResult.success(inspectVos);
    }


    @GetMapping("/updateDetail")
    public AjaxResult updateDetail(InspectVo inspectVo) {
        return AjaxResult.success(service.updateDetail(inspectVo.getInspectId()));
    }

    @PreAuthorize("@ss.hasPermi('estate:ins:remove')")
    @Log(title = "删除巡检计划", businessType = BusinessType.DELETE)
    @PostMapping("/removes")
    public AjaxResult removes(@RequestBody Long[] ids) {
        return service.removes(ids);
    }

    /**
     * 确定分配
     * @param inspectVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('estate:ins:share')")
    @Log(title = "分配巡检工单按钮", businessType = BusinessType.UPDATE)
    @PostMapping("/shareUpdate")
    public AjaxResult shareUpdate(@RequestBody InspectVo inspectVo) {
        return service.shareUpdate(inspectVo);
    }

}
