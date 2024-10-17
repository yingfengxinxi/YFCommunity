package com.wr.estate.web.customer.patrol;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.customer.patrol.PatrolWorkOrderService;
import com.wr.estate.service.facilitie.FacilitieEquipmentService;
import com.wr.remote.domain.FacilitieEquipment;
import com.wr.remote.domain.PatrolWorkOrder;
import com.wr.remote.domain.SysUser;
import com.wr.system.service.EstateLoginService;
import com.wr.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/2 14:02
 */
@RestController
@RequestMapping("patrolWorkOrder")
@Api(tags = "物管平台-巡检工单")
public class PatrolWorkOrderController extends BaseController {

    @Autowired
    private PatrolWorkOrderService patrolWorkOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacilitieEquipmentService facilitieEquipmentService;


    @PostMapping("list")
    @ApiOperation("巡检工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(@RequestBody PatrolWorkOrder patrolWorkOrder) {

        LambdaQueryWrapper<PatrolWorkOrder> queryWrapper = getPatrolLambdaQueryWrapper(patrolWorkOrder);

        PageUtils.startPage(patrolWorkOrder.getPageNum(), patrolWorkOrder.getPageSize());
        List<PatrolWorkOrder> list = patrolWorkOrderService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(patrolWorkOrder1 -> {
                if (StringUtils.isNotEmpty(patrolWorkOrder1.getPatrolImages())) {
                    patrolWorkOrder1.setPatrolImagesList(Arrays.asList(patrolWorkOrder1.getPatrolImages().split(",")));
                }

                FacilitieEquipment facilitieEquipment = facilitieEquipmentService.getById(patrolWorkOrder1.getFacilitieEquipmentId());
                if (facilitieEquipment != null) {
                    patrolWorkOrder1.setFacilitieEquipmentNo(facilitieEquipment.getProductNumber());
                    patrolWorkOrder1.setPatroltAddress(facilitieEquipment.getInstallationLocation());
                }


                if (StringUtils.isNotEmpty(patrolWorkOrder1.getName())) {
                    SysUser user = userService.selectUserById(Long.valueOf(patrolWorkOrder1.getName()));
                    if (user != null) {
                        patrolWorkOrder1.setName(user.getNickName());
                    }
                }
            });
        }
        return getDataTable(list);
    }

    @NotNull
    private static LambdaQueryWrapper<PatrolWorkOrder> getPatrolLambdaQueryWrapper(PatrolWorkOrder patrolWorkOrder) {
        LambdaQueryWrapper<PatrolWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        if (patrolWorkOrder.getCommunityId() != null) {
            queryWrapper.eq(PatrolWorkOrder::getCommunityId, patrolWorkOrder.getCommunityId());
        }
        if (StringUtils.isNotEmpty(patrolWorkOrder.getFacilitieEquipmentId())) {
            queryWrapper.eq(PatrolWorkOrder::getFacilitieEquipmentId, patrolWorkOrder.getFacilitieEquipmentId());
        }
        if (StringUtils.isNotEmpty(patrolWorkOrder.getName())) {
            queryWrapper.eq(PatrolWorkOrder::getName, patrolWorkOrder.getName());
        }
        if (StringUtils.isNotEmpty(patrolWorkOrder.getPatrolStatus())) {
            queryWrapper.eq(PatrolWorkOrder::getPatrolStatus, patrolWorkOrder.getPatrolStatus());
        }
        if (StringUtils.isNotEmpty(patrolWorkOrder.getFacilitieEquipmentType())) {
            queryWrapper.eq(PatrolWorkOrder::getFacilitieEquipmentType, patrolWorkOrder.getFacilitieEquipmentType());
        }
        if (StringUtils.isNotEmpty(patrolWorkOrder.getStartTime()) && StringUtils.isNotEmpty(patrolWorkOrder.getEndTime())) {
            queryWrapper.apply(" date_format(create_time,'%Y-%m-%d') between date_format('" + patrolWorkOrder.getStartTime() + "','%Y-%m-%d') and date_format('" + patrolWorkOrder.getEndTime() + "','%Y-%m-%d')");
        }
        queryWrapper.orderByDesc(PatrolWorkOrder::getCreateTime);
        return queryWrapper;
    }

    /**
     * @return
     */
    @ApiOperation("巡检工单详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        PatrolWorkOrder patrolWorkOrder = patrolWorkOrderService.getById(id);

        if (StringUtils.isNotEmpty(patrolWorkOrder.getPatrolImages())) {
            patrolWorkOrder.setPatrolImagesList(Arrays.asList(patrolWorkOrder.getPatrolImages().split(",")));
        }

        FacilitieEquipment facilitieEquipment = facilitieEquipmentService.getById(patrolWorkOrder.getFacilitieEquipmentId());
        patrolWorkOrder.setFacilitieEquipmentNo(facilitieEquipment.getProductNumber());
        patrolWorkOrder.setPatroltAddress(facilitieEquipment.getInstallationLocation());

        if (StringUtils.isNotEmpty(patrolWorkOrder.getName())) {
            SysUser user = userService.selectUserById(Long.valueOf(patrolWorkOrder.getName()));
            if (user != null) {
                patrolWorkOrder.setName(user.getNickName());
                patrolWorkOrder.setPhone(user.getPhonenumber());
            }
        }

        return AjaxResult.success(patrolWorkOrder);
    }
}
