package com.wr.estate.web.activity;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.FunctionUtil;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.activity.ActiviteManageService;
import com.wr.remote.estate.activity.ActiviteManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-11 15:15:07
 * @Desc:
 */

@RestController
@RequestMapping("/activiteCom/manage")
@Api(tags = "物管平台-活动管理")
public class ActiviteManageController extends BaseController {

    @Autowired
    private ActiviteManageService activiteManageService;

    /**
     * 列表
     *
     * @param
     * @return
     */
//    @RequiresPermissions("business:activite:list")
    @GetMapping("/list")
    @ApiOperation("活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityName", value = "活动名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "actState", value = "活动状态0=未开始1=进行中2=已结束", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "活动时间", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(ActiviteManage activiteManage) {
        activiteManage.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        startPage();
        return getDataTable(activiteManageService.selectList(activiteManage));
    }




    /**
     * 不分页列表
     *
     * @param
     * @return
     */
//    @RequiresPermissions("business:activite:list")
    @GetMapping("/allList")
    public AjaxResult allList(ActiviteManage activiteManage) {
        activiteManage.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return AjaxResult.success(activiteManageService.selectList(activiteManage));
    }

    /**
     * 活动类型列表
     *
     * @param
     * @return
     */

    @GetMapping("/typelist")
    public AjaxResult typelist(ActiviteManage activiteManage) {
        activiteManage.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        List<ActiviteManage> list = activiteManageService.selectTypeList(activiteManage);
        return AjaxResult.success(list);
    }

    /**
     * 楼栋列表
     *
     * @param
     * @return
     */
    @GetMapping("/Buildinglist")
    public AjaxResult Buildinglist(ActiviteManage activiteManage) {
        List<ActiviteManage> list = activiteManageService.selectBuildingListById(activiteManage);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param activiteManage 实体
     * @return 新增结果
     */

    @Log(title = "活动新增", businessType = BusinessType.INSERT)
//    @RequiresPermissions("business:activite:insert")
    @PostMapping("/insert")
    @ApiOperation("活动新增")
    public AjaxResult insert(@RequestBody ActiviteManage activiteManage) {
        String username = SecurityUtils.getUsername();
        activiteManage.setCreateBy(username);
        int i1 = activiteManageService.selectActivityName(activiteManage.getActivityName(), activiteManage.getCommunityId(), null);
        if (i1 > 0) {
            return AjaxResult.error("活动名重复");
        }
        activiteManage.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        activiteManageService.insertActivite(activiteManage);
        if ("0".equals(activiteManage.getActRang())) {
            List<ActiviteManage> buildingList = activiteManageService.selectBuildingListById1(activiteManage);
            for (ActiviteManage building : buildingList) {
                activiteManage.setBuildingId(building.getBuildingId());
                activiteManage.setBuildingName(building.getBuildingName());
                activiteManageService.insertBuilding(activiteManage);
            }
        } else {
            List<Long> idsvo = activiteManage.getBuildingIds();
            for (Long s : idsvo) {
                activiteManage.setBuildingId(s);
                activiteManageService.insertBuilding(activiteManage);
            }
        }
        return AjaxResult.success("添加成功!");
    }

    /**
     * 修改数据
     *
     * @param activiteManage 实体
     * @return 修改结果
     */
    @Log(title = "活动修改", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("business:activite:update")
    @PutMapping("/update")
    @ApiOperation("活动修改")
    public AjaxResult update(@RequestBody ActiviteManage activiteManage) {
        String username = SecurityUtils.getUsername();
        activiteManage.setCreateBy(username);
        int i1 = activiteManageService.selectActivityName(activiteManage.getActivityName(), activiteManage.getCommunityId(), activiteManage.getActivityId());
        if (i1 > 1) {
            return AjaxResult.error("活动名重复");
        }
        //删除活动-楼栋关联表
        activiteManageService.deleteAct(activiteManage.getActivityId());

        if ("0".equals(activiteManage.getActRang())) {

            List<ActiviteManage> buildingList = activiteManageService.selectBuildingListById1(activiteManage);
            for (ActiviteManage building : buildingList) {
                activiteManage.setBuildingId(building.getBuildingId());
                activiteManage.setBuildingName(building.getBuildingName());
                activiteManageService.insertBuilding(activiteManage);
            }
        } else {
            List<Long> idsvo = activiteManage.getBuildingIds();
            for (Long s : idsvo) {
                activiteManage.setBuildingId(s);
                activiteManageService.insertBuilding(activiteManage);
            }
        }
        return toAjax(activiteManageService.updateActivity(activiteManage));
    }

    /**
     * 通过主键查询单条数据
     */
    @ApiOperation("活动详情")
    @GetMapping(value = "/getInfoById/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId) {
        //获取楼栋
        ActiviteManage activiteManage = new ActiviteManage();
        activiteManage.setActivityId(activityId);
        //获取所有楼栋得id
        List<ActiviteManage> bu = activiteManageService.selectBuildingListBy(activityId);
        List<Long> buildingIds = bu.stream().map(ActiviteManage::getBuildingId).collect(Collectors.toList());
        ActiviteManage vo = activiteManageService.getActivityById(activityId);

        //根据活动id查询物资关联表
        List<ActiviteManage> distributionLists = activiteManageService.getDistributionLists(activityId);
        vo.setDistributionLists(distributionLists);
        vo.setBuildingIds(buildingIds);
        Date startTime = vo.getStartTime();
        Date endTime = vo.getEndTime();
        Date date = new Date(System.currentTimeMillis());
        if (date.compareTo(startTime) < 0) {
            vo.setActState("未开始");
        } else if (date.compareTo(endTime) > 0) {
            vo.setActState("已结束");
        } else {
            vo.setActState("进行中");
        }
        return AjaxResult.success(vo);
    }

    /**
     * 根据活动查询参与的楼栋
     *
     * @param activiteManage 实体
     * @return 修改结果
     */
    @GetMapping(value = "/activiteManage")
    public AjaxResult getInfo(ActiviteManage activiteManage) {
        List<ActiviteManage> activiteManages = activiteManageService.selectJoinBuildingList(activiteManage);
        String[] size = null;
        for (ActiviteManage list : activiteManages) {
            String buildingName = list.getBuildingName();
        }
        return AjaxResult.success(activiteManages);
    }

    /**
     * 活动基本详情信息
     *
     * @param activityId 实体
     * @return 修改结果
     */
    @ApiOperation("活动基本详情信息")
    @GetMapping(value = "/selectJoinPeople/{activityId}")
    public AjaxResult selectJoinPeople(@PathVariable("activityId") Long activityId) {
        int i = activiteManageService.selectOwnerList(activityId);
        ActiviteManage activiteManage = activiteManageService.selectJoinPeople(activityId);
        activiteManage.setJoinNum(Long.valueOf(i));
        return AjaxResult.success(activiteManage);
    }

    /**
     * 参与人员列表
     *
     * @param
     * @return
     */
    @ApiOperation("活动参与人员列表")
    @GetMapping("/joinPeopleList")
    public TableDataInfo selectJoinPeopleList(ActiviteManage activiteManage) {
        startPage();
        List<ActiviteManage> list = activiteManageService.selectJoinPeopleList(activiteManage);
        return getDataTable(list);
    }

    /**
     * 删除数据
     *
     * @param activiteId 主键
     * @return 是否删除成功
     */
//    @RequiresPermissions("business:activite:delete")
    @Log(title = "活动删除", businessType = BusinessType.DELETE)
    @ApiOperation("活动删除")
    @DeleteMapping("/delete/{activiteId}")
    public AjaxResult delete(@PathVariable Long activiteId) {
        return AjaxResult.success(activiteManageService.deleteById(activiteId));
    }

}
