package com.wr.estate.web.decorate;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.decorate.WorkerNewService;
import com.wr.remote.estate.customer.decorate.Worker;
import com.wr.remote.estate.customer.vo.decorate.WorkerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 17:08:47
 * @Desc: 新增的装修工
 */
@RestController
@RequestMapping("/workerNew")
public class WorkerNewController extends BaseController {

    @Autowired
    private WorkerNewService workerNewService;

    /**
     * 获取新增的装修工列表
     *
     * @param workerVo
     * @return
     */
    @RequiresPermissions("customer:workerNew:list")
    @GetMapping("/list")
    public TableDataInfo list(WorkerVo workerVo) {
        startPage();
        List<WorkerVo> list = workerNewService.getWorkerNewList(workerVo);
        return getDataTable(list);
    }

    /**
     * 查看新增的装修工详情
     *
     * @param workerId
     * @return
     */
    @GetMapping(value = "/{workerId}")
    @RequiresPermissions("customer:workerNew:detail")
    public AjaxResult getWorkerNewDetail(@PathVariable Long workerId) {
        return workerNewService.getWorkerNewDetail(workerId);
    }

    /**
     * 装修工审核通过
     *
     * @param workerIds
     * @return
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/{workerIds}")
    @RequiresPermissions("customer:workerNew:adopt")
    public AjaxResult workerNewAdopt(@PathVariable Long[] workerIds) {
        return toAjax(workerNewService.workerNewAdopt(workerIds));
    }

    /**
     * 装修工审核驳回
     *
     * @param worker
     * @return
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("customer:workerNew:reject")
    @PostMapping
    public AjaxResult workerNewReject(@RequestBody Worker worker) {
        return toAjax(workerNewService.workerNewReject(worker));
    }

    /**
     * 装修工审核单个驳回
     *
     * @param worker
     * @return
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("customer:workerNew:reject")
    @PostMapping("/one")
    public AjaxResult workerNewRejectOne(@RequestBody Worker worker) {
        return toAjax(workerNewService.workerNewRejectOne(worker));
    }
}
