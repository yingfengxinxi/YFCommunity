package com.wr.estate.web.base;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.service.base.MainTainService;
import com.wr.remote.estate.base.BusRepairWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 维修工种管理控制器
 * 
 * @author WuKong
 * @date 2022-10-11
 */
@RestController
@RequestMapping("/basic/mainTain")
public class MainTainController extends BaseController
{
    @Autowired
    private MainTainService mainTainService;


    @GetMapping("/list")
    public TableDataInfo list(BusRepairWork busRepairWork)
    {
        startPage();
        List<BusRepairWork> list = mainTainService.selectBusRepairWorkList(busRepairWork);
        return getDataTable(list);
    }

    @GetMapping(value = "/{workId}")
    public AjaxResult getInfo(@PathVariable("workId") Long workId)
    {
        return AjaxResult.success(mainTainService.selectBusRepairWorkId(workId));
    }


    @Log(title = "维修工种新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusRepairWork busRepairWork)
    {
        return toAjax(mainTainService.insertBusRepairWork(busRepairWork));
    }


    @Log(title = "维修工种修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusRepairWork busRepairWork)
    {
        return toAjax(mainTainService.updateBusRepairWork(busRepairWork));
    }


    @Log(title = "维修工种删除", businessType = BusinessType.DELETE)
	@DeleteMapping("/{workIds}")
    public AjaxResult remove(@PathVariable Long[] workIds)
    {
        return toAjax(mainTainService.deleteBusRepairWorkIds(workIds));
    }

    @Log(title = "状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/workStatus")
    public AjaxResult workStatus(@RequestBody BusRepairWork busRepairWork)
    {

        int row = mainTainService.updateBusRepairWork(busRepairWork);
         return toAjax(row);
    }

}
