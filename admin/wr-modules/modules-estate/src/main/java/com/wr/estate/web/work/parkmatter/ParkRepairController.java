package com.wr.estate.web.work.parkmatter;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.parkmatter.ParkRepairService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 15:11:45
 * @Desc:
 */
@RestController
@RequestMapping("parkrepair")
public class ParkRepairController extends BaseController {

    @Resource
    private ParkRepairService parkRepairService;

    @GetMapping("search")
    @RequiresPermissions("work:parkrepair:search")
    public TableDataInfo search(@RequestParam Map<String,Object> params) {
        startPage();
        return getDataTable(parkRepairService.search(params));
    }
    @RequiresPermissions("work:parkrepair:update")
    @Log(title = "修改园区报事类型", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public AjaxResult update(@RequestBody Map<String,Object> params) {

        return  parkRepairService.update(params);
    }
    @RequiresPermissions("work:parkrepair:save")
    @Log(title = "新增园区报事类型", businessType = BusinessType.INSERT)
    @PostMapping("save")
    public AjaxResult save(@RequestBody Map<String,Object> params) {

        return  parkRepairService.save(params);
    }
    @RequiresPermissions("work:parkrepair:removes")
    @Log(title = "删除园区报事类型", businessType = BusinessType.DELETE)
    @PostMapping("removes")
    public AjaxResult removes(@RequestBody Long[] ids) {

        return AjaxResult.success(parkRepairService.removes(ids));
    }
}
