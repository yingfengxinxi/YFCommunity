package com.wr.system.web;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.annotation.InnerAuth;
import com.wr.system.service.EstateOperateService;
import com.wr.remote.domain.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:39:00
 * @Desc: 记录操作
 */
@RestController
@RequestMapping("/operate")
public class EstateOperateController extends BaseController {
    @Autowired
    private EstateOperateService estateOperateService;

    @InnerAuth(userType = UserConstants.USER_ESTATE)
    @PostMapping
    public AjaxResult add(@RequestBody SysOperLog operLog) {
        return toAjax(estateOperateService.insertOperateLog(operLog));
    }
}
