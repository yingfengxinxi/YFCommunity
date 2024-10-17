package com.wr.estate.web.work.parkmatter;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.parkmatter.ParkAuditService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 18:33:17
 * @Desc:
 */
@RequestMapping("parkaudit")
@RestController
public class ParkAuditController extends BaseController {

    @Resource
    private ParkAuditService parkAuditService;

    @GetMapping("search")
    @RequiresPermissions("work:parkaudit:search")
    public TableDataInfo search(@RequestParam Map<String,Object> params) {
        startPage();
        return getDataTable(parkAuditService.search(params));
    }
    @RequiresPermissions("work:parkaudit:pass")
    @Log(title = "园区业主工单审核通过", businessType = BusinessType.UPDATE)
    @PostMapping("pass")
    public AjaxResult passState(@RequestBody Map<String,Object> params) {
        return  parkAuditService.changeState(params);
    }
    @RequiresPermissions("work:parkaudit:rebut")
    @Log(title = "园区业主工单审核驳回", businessType = BusinessType.UPDATE)
    @PostMapping("rebut")
    public AjaxResult rebutState(@RequestBody Map<String,Object> params) {
        return  parkAuditService.changeState(params);
    }

    @GetMapping("detail")
    public AjaxResult detail(Long auditId) {

        return AjaxResult.success(parkAuditService.detail(auditId));
    }
}
