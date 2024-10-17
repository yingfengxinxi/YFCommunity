package com.wr.api.base.web;

import com.wr.api.base.service.OperateLogService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.ApiOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:43:36
 * @Desc: API 日志交互层
 */
@RestController
@RequestMapping("/operate_log")
public class OperateLogController extends BaseController {
    @Autowired
    private OperateLogService operateLogService;

    @PostMapping
    public AjaxResult add(@RequestBody ApiOperLog operLog) {

        return toAjax(operateLogService.insertOperateLog(operLog));
    }
}
