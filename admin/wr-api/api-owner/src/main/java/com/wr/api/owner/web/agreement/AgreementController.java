package com.wr.api.owner.web.agreement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.owner.service.agreement.AgreementService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.Agreement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author lvzy
 * @Date 2024/2/7 20:43
 */
@RestController
@RequestMapping("/v2/agreementApp")
@Api(tags = "业主平台-移动端协议")
public class AgreementController extends BaseController {

    @Resource
    private AgreementService agreementService;


    /**
     * @return
     */
    @ApiOperation("协议")
    @GetMapping("getAgreement")
    public AjaxResult getAgreement() {
        LambdaQueryWrapper<Agreement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( Agreement::getDelFlag, "0" );
        return AjaxResult.success( agreementService.getOne( queryWrapper ) );
    }

    @ApiOperation("保存/修改")
    @PostMapping("saveAgreement")
    public AjaxResult saveAgreement(@RequestBody Agreement agreement) {
        LambdaQueryWrapper<Agreement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( Agreement::getDelFlag, "0" );
        Agreement a = agreementService.getOne( queryWrapper );
        if (a != null) {
            agreement.setId( a.getId() );
            agreementService.updateById( agreement );
        } else {
            agreementService.save( agreement );
        }

        return AjaxResult.success();
    }
}
