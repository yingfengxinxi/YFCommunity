package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.service.application.serve.AgreementService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.Agreement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/2/7 20:43
 */
@RestController
@RequestMapping("v2/agreementApp")
@Api(tags = "物业移动端-移动端协议")
public class AgreementController extends BaseController {

    @Autowired
    private AgreementService agreementService;


    /**
     * @return
     */
    @ApiOperation("协议")
    @GetMapping("getAgreement")
    public AjaxResult getAgreement() {
        LambdaQueryWrapper<Agreement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Agreement.class, r -> !r.getColumn().equals("owner_privacy_policy"));
        queryWrapper.select(Agreement.class, r -> !r.getColumn().equals("owner_thumbnail"));
        queryWrapper.eq(Agreement::getDelFlag, "0");
        return AjaxResult.success(agreementService.getOne(queryWrapper));
    }
}
