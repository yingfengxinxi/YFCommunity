package com.wr.estate.web.center.audit;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusTenant;
import com.wr.estate.entity.vo.TenantAuditVo;
import com.wr.estate.service.center.audit.BusTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-15 17:20:07
 * @Desc: 租客审核控制层
 */
@RestController
@RequestMapping("/tenant")
public class BusTenantController extends BaseController {
    @Autowired
    private BusTenantService busTenantService;

    /**
     * 查询租客审核集合
     * @param tenant
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('center:audit:tenantAudit:list')")
    public TableDataInfo list(BusTenant tenant) {
        startPage();
        tenant.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId().intValue());
        List<TenantAuditVo> owneAuditVos = busTenantService.listOwnerAuditQure(tenant);
        return getDataTable(owneAuditVos);
    }


    /**
     * 删除租客审核信息
     */
    @PreAuthorize("@ss.hasPermi('center:audit:tenantAudit:del')")
    @DeleteMapping("/{tenantIds}")
    public AjaxResult remove(@PathVariable Long[] tenantIds)
    {
        busTenantService.deleteConfigByIds(tenantIds);
        return success();
    }


    /**
     * 根据参数键名查询单个租客信息
     */
    @PreAuthorize("@ss.hasPermi('center:audit:tenantAudit:select')")
    @GetMapping(value = "/{tenantId}")
    public AjaxResult getOwneAuditVo(@PathVariable Long tenantId)
    {
        return AjaxResult.success(busTenantService.selectOneOwneAuditVo(tenantId));
    }

    /**
     * 租客审核通过或驳回
     */
    @PreAuthorize("@ss.hasPermi('center:audit:tenantAudit:passAndRejection')")
    @PutMapping(value = "/audit")
    public AjaxResult getConfigKey(@RequestBody TenantAuditVo tenantAuditVo)
    {
        return AjaxResult.success(busTenantService.passAndRejection(tenantAuditVo.getTenantId(),tenantAuditVo.getRoomId(), tenantAuditVo.getAuditStatus(),tenantAuditVo.getRejectReason()));
    }
}
