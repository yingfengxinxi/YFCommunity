package com.wr.estate.web.center.audit;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;

import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.OwneAuditVo;
import com.wr.estate.service.center.audit.BusOwnerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-13 10:35:57
 * @Desc: 业主审核控制层
 */
@RestController
@RequestMapping("/owner")
@Api(tags = "物管平台-业主/房屋审核")
public class BusOwnerContorller extends BaseController {

    @Autowired
    private BusOwnerService busOwnerService;

    @ApiOperation("审核列表")
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('center:audit:owmer:list')")
    public TableDataInfo list(BusOwner busOwner) {
        startPage();
        busOwner.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId().intValue());
        List<OwneAuditVo> owneAuditVos = busOwnerService.listOwnerAuditQure(busOwner);
        return getDataTable(owneAuditVos);
    }


    /**
     * 删除业主审核信息
     */
    @ApiOperation("删除业主审核信息")
    @PreAuthorize("@ss.hasPermi('center:audit:owmer:del')")
    @DeleteMapping("/{ownerIds}")
    public AjaxResult remove(@PathVariable Long[] ownerIds) {
        busOwnerService.deleteConfigByIds(ownerIds);
        return success();
    }


    /**
     * 查询单个业主审核信息
     */
    @ApiOperation("查询单个业主审核信息")
    @PreAuthorize("@ss.hasPermi('center:audit:owmer:select')")
    @GetMapping(value = "/{ownerId}/{roomId}")
    public AjaxResult getOwneAuditVo(@PathVariable("ownerId") Long ownerId, @PathVariable("roomId") Long roomId) {
        return AjaxResult.success(busOwnerService.selectOneOwneAuditVo(ownerId, roomId));
    }

    /**
     * 审核通过或驳回
     */
    @ApiOperation("审核通过或驳回")
    @PreAuthorize("@ss.hasPermi('center:audit:owmer:passAndRejection')")
    @PutMapping(value = "/audit")
    public AjaxResult passAndRejection(@RequestBody OwneAuditVo auditVo) {
        busOwnerService.passAndRejection(auditVo.getRoomId(), auditVo.getOwnerId(), auditVo.getAuditStatus(), auditVo.getRejectReason());
        return AjaxResult.success();
    }
}
