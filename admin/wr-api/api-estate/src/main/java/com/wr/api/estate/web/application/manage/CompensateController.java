package com.wr.api.estate.web.application.manage;

import com.wr.api.estate.entity.vo.application.manage.CompensateVo;
import com.wr.api.estate.service.application.manage.CompensateService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lvzy
 * @Date: 2022-11-17 16:35:04
 * @Desc:
 */
@RestController
@RequestMapping("/v2/compensate")
public class CompensateController extends BaseController {
    @Autowired
    private CompensateService compensateService;

    /**
     * 添加赔偿信息
     * @param compensateVo
     * @return
     */
    @AuthRequired
    @PostMapping("/insert")
    @ApiLog(title="赔偿记录-添加", businessType = BusinessType.INSERT)
    public AjaxResult addCompensate(@RequestBody CompensateVo compensateVo) {
        return AjaxResult.success(compensateService.insertCompensate(compensateVo));
    }
}
