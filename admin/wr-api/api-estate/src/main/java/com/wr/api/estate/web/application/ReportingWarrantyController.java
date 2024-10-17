package com.wr.api.estate.web.application;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.manage.StaffService;
import com.wr.api.estate.service.application.serve.CleaningWorkOrderService;
import com.wr.api.estate.service.application.serve.InspectionService;
import com.wr.api.estate.service.application.serve.PatrolService;
import com.wr.api.estate.service.application.serve.ReportingWarrantyService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.*;
import com.wr.remote.estate.manage.Staff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:57
 */
@RestController
@RequestMapping("v2/reportingWarranty")
@Api(tags = "物业移动端-报事报修")
public class ReportingWarrantyController extends BaseController {

    @Autowired
    private ReportingWarrantyService reportingWarrantyService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private CleaningWorkOrderService cleaningWorkOrderService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private PatrolService patrolService;


    @RequestMapping("list")
    @ApiOperation("报事报修列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "当前登录账号loginName", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(ReportingWarranty reportingWarranty) {
        LambdaQueryWrapper<ReportingWarranty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReportingWarranty::getCommunityId, reportingWarranty.getCommunityId());
        SysUser sysUser = loginService.getUserNameNikeName(reportingWarranty.getExecutorBy());
        String userType = sysUser.getUserType();
        Set<String> workPostList = new HashSet<>();
        if (!userType.equals("10")) {
            Staff staff = staffService.getByUserIdStaff(String.valueOf(sysUser.getUserId()), "");
            String workPost = staff.getWorkPost();
//            人员---工单类型
//            秩序部人员----报事报修工单
//            保洁人员----保洁工单
//            维修人员----工程维修工单
//            物管平台管理人员---投诉建议与其他两种工单
            String[] workPosts = workPost.split(",");
            for (String wp : workPosts) {
                if ("9".equals(wp)) {
                    //秩序查询报事报修类型工单
                    workPostList.add("0");
                } else if ("4".equals(wp)) {
                    //保洁查询保洁类型工单
                    workPostList.add("1");
                } else if ("1".equals(wp)) {
                    //工程维修查询工程维修类型工单
                    workPostList.add("2");
                }
            }
        } else {
            //投诉建议
            workPostList.add("3");
            //其他
            workPostList.add("4");
        }
        if (CollectionUtils.isNotEmpty(workPostList)) {
            queryWrapper.in(ReportingWarranty::getReportType, workPostList);
        } else {
            List<ReportingWarranty> reportingWarrantyList = Lists.newArrayList();
            return getDataTable(reportingWarrantyList);
        }

        if (StringUtils.isNotEmpty(reportingWarranty.getReportStatus())) {
            queryWrapper.eq(ReportingWarranty::getReportStatus, reportingWarranty.getReportStatus());
        }
        queryWrapper.apply(" (executor_by='" + reportingWarranty.getExecutorBy() + "' or executor_by='' or executor_by IS NULL) ");
        queryWrapper.orderByDesc(ReportingWarranty::getCreateTime);
        startPage();
        return getDataTable(reportingWarrantyService.list(queryWrapper));
    }

    public static void main(String[] args) {
        Set<String> workPostList = new HashSet<>();
        String workPost = "";
        String[] workPosts = workPost.split(",");
        for (String wp : workPosts) {
            if ("9".equals(wp)) {
                //秩序查询报事报修类型工单
                workPostList.add("0");
            } else if ("4".equals(wp)) {
                //保洁查询保洁类型工单
                workPostList.add("1");
                workPostList.add("2");
            } else if ("6".equals(wp)) {
                //工程维修查询工程维修类型工单
                workPostList.add("2");
            }
        }

        System.out.println(workPostList.toString());
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation("报事报修详情")
    public AjaxResult get(@PathVariable("id") String id) {

        ReportingWarranty warranty = reportingWarrantyService.getById(id);
        if (warranty != null) {
            String reportImage = warranty.getReportImage();
            if (StringUtils.isNotEmpty(reportImage)) {
                warranty.setReportImages(Arrays.asList(reportImage.split(",")));
            }
            String orderImage = warranty.getOrderImage();
            if (StringUtils.isNotEmpty(orderImage)) {
                warranty.setOrderImages(Arrays.asList(orderImage.split(",")));
            }
            if (StringUtils.isNotEmpty(warranty.getExecutorBy())) {
                SysUser sysUser = loginService.getUserNameNikeName(warranty.getExecutorBy());
                if (sysUser != null) {
                    warranty.setExecutorBy(sysUser.getNickName());
                    warranty.setExecutorPhone(sysUser.getPhonenumber());
                }
            }
        }
        return AjaxResult.success(warranty);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("执行按钮")
    @GetMapping("execute")
    public AjaxResult execute(@RequestParam("id") String id, @RequestParam("executorBy") String executorBy) {

        ReportingWarranty warranty = reportingWarrantyService.getById(id);
        if (StringUtils.equals(warranty.getReportStatus(), "1")) {
            return AjaxResult.error("当前工单已被执行");
        }
        warranty.setExecutorBy(executorBy);
        warranty.setUpdateBy(SecurityUtils.getUsername());
        warranty.setUpdateTime(new Date());
        //执行中
        warranty.setReportStatus("1");
        reportingWarrantyService.updateById(warranty);
        //修改巡查工单状态
        String inspectionId = warranty.getInspectionId();
        if (StringUtils.isNotEmpty(inspectionId)) {
            Inspection inspection = inspectionService.getById(inspectionId);
            inspection.setInspectionStatus("2");
            inspectionService.updateById(inspection);
        }
        //修改巡更工单状态
        String patrolWorkOrderId = warranty.getPatrolWorkOrderId();
        if (StringUtils.isNotEmpty(patrolWorkOrderId)) {
            Patrol patrol = patrolService.getById(patrolWorkOrderId);
            patrol.setPatrolStatus("2");
            patrolService.updateById(patrol);
        }
        //修改清洁工单状态
        String cleaningWorkOrderId = warranty.getCleaningWorkOrderId();
        if (StringUtils.isNotEmpty(cleaningWorkOrderId)) {
            CleaningWorkOrder cleaningWorkOrder = cleaningWorkOrderService.getById(cleaningWorkOrderId);
            cleaningWorkOrder.setCleaningStatus("2");
            cleaningWorkOrderService.updateById(cleaningWorkOrder);
        }

        return AjaxResult.success();
    }


    /**
     * @param warranty
     * @return
     */
    @ApiOperation("上传执行结果")
    @PostMapping("addExecuteReult")
    public AjaxResult addExecuteReult(@RequestBody ReportingWarranty warranty) {

        warranty.setUpdateBy(warranty.getExecutorBy());
        warranty.setUpdateTime(new Date());
        //待评价
        warranty.setReportStatus("2");
        //修改巡查工单状态
        getInspection(warranty);
        //修改巡更工单状态
        getPatrol(warranty);
        //修改清洁工单状态
        cleaningWorkOrder(warranty);
        reportingWarrantyService.updateById(warranty);

        return AjaxResult.success();
    }

    /**
     * 修改清洁工单状态
     *
     * @param warranty
     */
    private void cleaningWorkOrder(ReportingWarranty warranty) {
        //修改清洁工单状态
        String cleaningWorkOrderId = warranty.getCleaningWorkOrderId();
        if (StringUtils.isNotEmpty(cleaningWorkOrderId)) {
            CleaningWorkOrder cleaningWorkOrder = cleaningWorkOrderService.getById(cleaningWorkOrderId);
            if(cleaningWorkOrder!=null){
                cleaningWorkOrder.setCleaningStatus("3");
                cleaningWorkOrder.setOrderFeedback(warranty.getOrderFeedback());
                cleaningWorkOrder.setOrderImage(warranty.getOrderImage());
                cleaningWorkOrder.setExecutorBy(warranty.getExecutorBy());
                cleaningWorkOrder.setUpdateTime(new Date());
                cleaningWorkOrderService.updateById(cleaningWorkOrder);
            }

            //已完成
            warranty.setReportStatus("3");
        }
    }

    /**
     * 修改巡查工单状态
     *
     * @param warranty
     * @return
     */
    private String getInspection(ReportingWarranty warranty) {
        String inspectionId = warranty.getInspectionId();
        if (StringUtils.isNotEmpty(inspectionId)) {
            Inspection inspection = inspectionService.getById(inspectionId);
            if(inspection!=null){
                inspection.setInspectionStatus("3");
                inspection.setOrderFeedback(warranty.getOrderFeedback());
                inspection.setOrderImage(warranty.getOrderImage());
                inspection.setExecutorBy(warranty.getExecutorBy());
                inspection.setUpdateTime(new Date());
                inspectionService.updateById(inspection);
            }

            //已完成
            warranty.setReportStatus("3");
        }
        return inspectionId;
    }

    /**
     * 修改巡更工单状态
     *
     * @param warranty
     * @return
     */
    private String getPatrol(ReportingWarranty warranty) {
        String patrolWorkOrderId = warranty.getPatrolWorkOrderId();
        if (StringUtils.isNotEmpty(patrolWorkOrderId)) {
            Patrol patrol = patrolService.getById(patrolWorkOrderId);
            if(patrol!=null){
                patrol.setPatrolStatus("3");
                patrol.setOrderFeedback(warranty.getOrderFeedback());
                patrol.setOrderImage(warranty.getOrderImage());
                patrol.setExecutorBy(warranty.getExecutorBy());
                patrol.setUpdateTime(new Date());
                patrolService.updateById(patrol);
            }

            //已完成
            warranty.setReportStatus("3");
        }
        return patrolWorkOrderId;
    }
}
