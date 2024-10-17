package com.wr.govern.web.security;

import com.github.pagehelper.PageInfo;
import com.wr.common.core.constant.HttpStatus;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.security.BulletinService;
import com.wr.remote.govern.security.Bulletin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-26 10:36:43
 * @Desc: 警民互动控制层
 */
@RestController
@RequestMapping("/bulletin")
public class BulletinController extends BaseController {

    @Autowired
    private BulletinService bulletinService;

    /**
     * 获取警民互动列表
     * @param bulletin
     * @return com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("govern:bulletin:list")
    @GetMapping("/list")
    public TableDataInfo list(Bulletin bulletin) {
        startPage();
        List<Bulletin> list = bulletinService.selectBulletinList(bulletin);
        Boolean userType = bulletinService.getUserType();
        List<Map<String,Object>> resultList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("resultList",list);
        map.put("isManage",userType);
        resultList.add(map);

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(resultList);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 获取警民互动详细信息
     * @param bulletinId 警民互动id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:bulletin:list")
    @GetMapping(value = "/{bulletinId}")
    public AjaxResult getInfo(@PathVariable Long bulletinId) {
        return AjaxResult.success(bulletinService.selectBulletinById(bulletinId));
    }

    /**
     * 新增警民互动
     * @param bulletin
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:bulletin:add")
    @Log(title = "新增警民互动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult /**/add(@Validated @RequestBody Bulletin bulletin) {
        Integer result=bulletinService.checkBulletinExsist(bulletin.getCardNo());
        if(result>0){
            return AjaxResult.error("该人员已存在");
        }
        bulletin.setCreateBy(SecurityUtils.getUsername());
        bulletin.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return toAjax(bulletinService.insertBulletin(bulletin));
    }

    /**
     * 修改警民互动
     * @param bulletin
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:bulletin:edit")
    @Log(title = "修改警民互动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Bulletin bulletin) {
        bulletin.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(bulletinService.updateBulletin(bulletin));
    }

    /**
     *  删除警民互动
     * @param bulletinId
     * @return  com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:bulletin:remove")
    @Log(title = "删除警民互动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bulletinId}")
    public AjaxResult delPrevent(@PathVariable Long bulletinId) {
        return toAjax(bulletinService.deleteBulletinByIds(bulletinId));
    }

    /**
     * 修改归档
     * @param bulletinId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:bulletin:edit")
    @GetMapping("/updateArchive/{bulletinId}")
    public AjaxResult edit(@PathVariable Long bulletinId) {
        //是否是管理员
        Boolean result = bulletinService.getUserType();
        if(!result){
            return AjaxResult.error("不是管理员无法修改归档");
        }
        return toAjax(bulletinService.updateArchive(bulletinId));
    }

    /**
     * 查看归档人详情
     * @param bulletinId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:bulletin:list")
    @GetMapping("/getArchiveDetail/{bulletinId}")
    public AjaxResult getArchiveDetail(@PathVariable Long bulletinId) {
        return AjaxResult.success(bulletinService.getArchiveDetail(bulletinId));
    }

    /**
     * 查看用户类型
     * @param
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:bulletin:list")
    @GetMapping("/getUserType")
    public AjaxResult getUserType() {
        return AjaxResult.success(bulletinService.getUserType());
    }

}
