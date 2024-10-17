package com.wr.api.estate.web.application.info;

import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.api.estate.service.application.info.TenantService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:07:17
 * @Desc:
 */
@RestController
@RequestMapping("/v2/tenant")
public class TenantController extends BaseController {
    @Autowired
    private TenantService tenantService;

    /**
     * 列表
     * @param tenantVo
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(TenantVo tenantVo) {
        startPage();
        List<TenantVo> list = tenantService.getList(tenantVo);
        return getDataTable(list);
    }

    /**
     * 详情
     * @param tenantVo
     * @return
     */
    @GetMapping("/detail")
    public AjaxResult getInfo(TenantVo tenantVo) {
        return AjaxResult.success(tenantService.getDetail(tenantVo));
    }

    /**
     * 租客总数
     * @param communityId
     * @return
     */
    @GetMapping("/count/{communityId}")
    public AjaxResult getCount(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(tenantService.getTenantCount(communityId));
    }
}
