package com.wr.estate.web.activity;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.activity.ActiviteTypeService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.activity.ActiviteType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-12 14:46:07
 * @Desc:
 */

@RestController
@RequestMapping("/activiteType")
public class ActiviteTypeContorller extends BaseController {

    @Autowired
    private ActiviteTypeService activiteTypeService;

    /**
     * 列表
     * @param
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("business:activitetype:list")
    public TableDataInfo list(ActiviteType activiteType){
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        activiteType.setEstateId(estateId);
        startPage();
        List<ActiviteType> list = activiteTypeService.selectList(activiteType);
        return getDataTable(list);
    }

    /**
     * 新增数据
     *
     * @param activiteType 实体
     * @return 新增结果
     */
    @Log(title = "活动类型新增", businessType = BusinessType.INSERT)
    @RequiresPermissions("business:activitetype:insert")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody ActiviteType activiteType) {
        if(null ==  activiteType.getCommunityId()){
            return AjaxResult.error("请选择小区");
        }
        int i1 = activiteTypeService.selectTypeName(activiteType.getTypeName(),activiteType.getCommunityId());
        if (i1 > 0){
            return AjaxResult.error("类型名重复");

        }
        String username = SecurityUtils.getUsername();
        activiteType.setCreateBy(username);
        return toAjax(activiteTypeService.insertActivite(activiteType));
    }

    /**
     * 修改数据
     *
     * @param activiteType 实体
     * @return 修改结果
     */
    @Log(title = "活动类型修改", businessType = BusinessType.UPDATE)
    @RequiresPermissions("business:activitetype:update")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody ActiviteType activiteType) {
        String username = SecurityUtils.getUsername();
        activiteType.setUpdateBy(username);
        int i1 = activiteTypeService.selectTypeName(activiteType.getTypeName(),activiteType.getCommunityId());
        if (i1 > 0){
            return AjaxResult.error("类型名重复");
        }

        return toAjax(activiteTypeService.updateActiviteById(activiteType));
    }

    /**
     * 删除数据
     *
     * @param ids []
     * @return 修改结果
     */
    @Log(title = "活动类型删除", businessType = BusinessType.DELETE)
    @RequiresPermissions("business:activitetype:delete")
    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody Long[] ids) {
        return toAjax(activiteTypeService.removeActiviteById(ids));
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping(value = "/getInfoById/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId) {
        return AjaxResult.success(activiteTypeService.selectInfoById(typeId));
    }



}
