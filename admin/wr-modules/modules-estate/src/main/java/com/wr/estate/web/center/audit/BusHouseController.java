package com.wr.estate.web.center.audit;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.OwneAuditVo;
import com.wr.estate.service.center.audit.BusHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 20:01:44
 * @Desc: 房产审核控制层
 */
@RequestMapping("/house")
@RestController
public class BusHouseController extends BaseController {
    @Autowired
    private BusHouseService busHouseService;

    /**
     * 查询房产审核集合
     * @param busOwner
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('center:audit:propertyReview:list')")
    public TableDataInfo list(BusOwner busOwner) {
        startPage();
        busOwner.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId().intValue());
        List<OwneAuditVo> owneAuditVos = busHouseService.listOwnerAuditQure(busOwner);
        return getDataTable(owneAuditVos);
    }


    /**
     * 删除房产审核信息
     */
    @DeleteMapping("/{houseIds}")
    @PreAuthorize("@ss.hasPermi('center:audit:propertyReview:del')")
    public AjaxResult remove(@PathVariable Long[] houseIds)
    {
        busHouseService.deleteConfigByIds(houseIds);
        return success();
    }


    /**
     * 根据参数键名查询单个房产审核信息
     */
    @PreAuthorize("@ss.hasPermi('center:audit:propertyReview:select')")
    @GetMapping(value = "/{houseId}")
    public AjaxResult getOwneAuditVo(@PathVariable Long houseId)
    {
        return AjaxResult.success(busHouseService.selectOneOwneAuditVo(houseId));
    }

    /**
     * 审核和驳回
     */
    @PreAuthorize("@ss.hasPermi('center:audit:propertyReview:passAndRejection')")
    @PutMapping(value = "/audit")
    public AjaxResult getConfigKey(@RequestBody OwneAuditVo auditVo)
    {
        return busHouseService.passAndRejection(auditVo);
    }
}
