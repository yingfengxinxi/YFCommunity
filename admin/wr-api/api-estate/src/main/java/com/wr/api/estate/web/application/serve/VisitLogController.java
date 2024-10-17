package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.application.serve.VisitLog;
import com.wr.api.estate.service.application.serve.VisitLogService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 09:18:03
 * @Desc: 人行登记控制层
 */
@RestController
@RequestMapping("/v2/visitLog")
public class VisitLogController extends BaseController {

    @Autowired
    private VisitLogService visitLogService;

    /**
     * 添加访客信息
     * @param visitLog
     * @return
     */
    @ApiLog(title="添加访客信息",businessType= BusinessType.INSERT)
    @AuthRequired
    @PostMapping("insertVisitLog")
    public AjaxResult insertVisitLog(@RequestBody VisitLog visitLog){
        return toAjax(visitLogService.insertVisitLog(visitLog));
    }
}
