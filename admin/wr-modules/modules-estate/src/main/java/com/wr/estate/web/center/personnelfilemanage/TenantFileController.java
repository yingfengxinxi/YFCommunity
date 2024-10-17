package com.wr.estate.web.center.personnelfilemanage;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.dto.AuthDto;
import com.wr.estate.entity.dto.TenantFileDto;
import com.wr.estate.service.center.personnelfilemanage.TenantFileService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.center.vo.TenantFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author: lvzy
 * @Date: 2022-10-17 16:20:19
 * @Desc: 租客档案
 */
@RestController
@RequestMapping("tenantfile")
@Api(tags = "物管平台-租客档案")
public class TenantFileController extends BaseController {

    @Resource
    private TenantFileService tenantFileService;

    /**
     * 查询 租客
     *
     * @param
     * @return
     */
    @PostMapping("query")
    @ApiOperation("分页列表")
    public TableDataInfo query(@RequestBody TenantFileDto tenantFileDto) {
        PageUtils.startPage(tenantFileDto.getPageNum(),tenantFileDto.getPageSize());
        List<TenantFileDto> tenantFileDtos = tenantFileService.queryByCommunityId(tenantFileDto);
        if(CollectionUtils.isNotEmpty(tenantFileDtos)){
            tenantFileDtos.stream().forEach(tenantFileDto1 -> {
                tenantFileDto1.setOwnerName(DesensitizationUtils.name(tenantFileDto1.getOwnerName()));
                tenantFileDto1.setOwnerPhone(DesensitizationUtils.phone(tenantFileDto1.getOwnerPhone()));
                tenantFileDto1.setTenantName(DesensitizationUtils.name(tenantFileDto1.getTenantName()));
                tenantFileDto1.setTenantPhone(DesensitizationUtils.phone(tenantFileDto1.getTenantPhone()));
            });
        }
        return getDataTable(tenantFileDtos);
    }

    /**
     * 查询 租客 合同
     *
     * @param
     * @return
     */
    @GetMapping("queryContract")
    public AjaxResult queryContract(Long id) {


        return AjaxResult.success(tenantFileService.queryContract(id));
    }

    /**
     * 查询 租客 合同
     *
     * @param
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("getById")
    public AjaxResult getById(@RequestParam("tenantId") Long tenantId) {


        return AjaxResult.success(tenantFileService.getById(tenantId));
    }

    /**
     * 修改
     *
     * @param
     * @return
     */
    @Log(title = "修改租客", businessType = BusinessType.UPDATE)
    @ApiOperation("修改租客信息")
    @PostMapping("update")
    public AjaxResult update(@RequestBody TenantFileDto tenantFileDto) {

        return AjaxResult.success(tenantFileService.update(tenantFileDto));
    }

    /**
     * 修改
     *
     * @param
     * @return
     */

    @Log(title = "添加租客", businessType = BusinessType.INSERT)
    @ApiOperation("添加租客信息")
    @PostMapping("save")
    public AjaxResult save(@RequestBody TenantFileDto tenantFileDto) {

        return AjaxResult.success(tenantFileService.save(tenantFileDto));
    }

    /**
     * 解约
     *
     * @param
     * @return
     */
    @Log(title = "解约租客", businessType = BusinessType.DELETE)
    @ApiOperation("解约租客")
    @PostMapping("deleted")
    public AjaxResult deleted(@RequestBody Long[] ids) {

        return AjaxResult.success(tenantFileService.deleted(ids));
    }

    /**
     * 续约
     *
     * @param
     * @return
     */
    @Log(title = "续约租客", businessType = BusinessType.UPDATE)
    @ApiOperation("续约租客")
    @GetMapping("updateContract")
    public AjaxResult updateContract(
            @RequestParam("contractStart") Date contractStart,
            @RequestParam("contractEnd") Date contractEnd,
            @RequestParam("ids") String ids
    ) {

        return AjaxResult.success(tenantFileService.updateContract(contractStart, contractEnd, ids));
    }


    /**
     * 查出设备及人员列表
     *
     * @param communityId 小区id
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-05-18 16:15:14
     * @ver v1.0.0
     */
    @GetMapping("/getDevice/{communityId}")
    public AjaxResult getDevice(@PathVariable("communityId") Long communityId) {

        return tenantFileService.selectDeviceList(communityId);
    }

    /**
     * 人员授权
     *
     * @param authDto
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-05-18 16:44:30
     * @ver v1.0.0
     */
    @PostMapping("/auth")
    @RequiresPermissions("center:tenantfile:auth")
    public AjaxResult authTenant(@RequestBody AuthDto authDto) {

        return tenantFileService.authTenant(authDto);
    }
}
