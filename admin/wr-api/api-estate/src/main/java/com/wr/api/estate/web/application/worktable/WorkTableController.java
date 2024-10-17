package com.wr.api.estate.web.application.worktable;


import com.wr.api.estate.service.application.worktable.WorkTableService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DIANWEI
 * @Description 工作台   Controller
 * @date 2022/11/15 9:42
 */
@RestController
@RequestMapping("v2/wrokTable")
@Api(tags = "物业移动端-工作台")
public class WorkTableController {

    @Autowired
    private WorkTableService workTableService;


    /**
     * 查询基金投票金额
     *
     * @return 查询基金投票金额
     */
    @ApiOperation("小区收益")
    @GetMapping("/getFoundData")
    public AjaxResult getFoundData(@RequestParam("communityId") Long communityId, @RequestParam("date") String date) {

        return workTableService.getFoundData(communityId,date);
    }

    /**
     * @Description 数据统计  出行车辆数 出行人数 出行访客数
     * @Date 2022/11/15 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/dataSta")
    public AjaxResult getDataStatistics(@RequestParam(value = "communityId", required = false) Long communityId) {
        if (StringUtils.objectNotNull(communityId)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getDataStatistics(communityId));
    }

    /**
     * 工单统计
     *
     * @param communityId
     * @param executorBy
     * @return
     */
    @ApiOperation("报事报修工单统计")
    @GetMapping("/orderSta/appraise")
    public AjaxResult getAppraiseNum(
            @RequestParam(value = "communityId") Long communityId,
            @RequestParam(value = "executorBy") String executorBy
    ) {
        if (StringUtils.objectNotNull(communityId)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getAppraiseNum(communityId, executorBy));
    }

    /**
     * 工单统计
     *
     * @param communityId
     * @return
     */
    @ApiOperation("本月代办任务")
    @GetMapping("/getMonthDbOrder")
    public AjaxResult getMonthDbOrder(
            @RequestParam(value = "communityId") Long communityId
    ) {
        if (StringUtils.objectNotNull(communityId)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getMonthDbOrder(communityId));
    }

    /**
     * 能耗统计
     *
     * @param communityId
     * @return
     */
    @ApiOperation("能耗统计")
    @GetMapping("/getCommunityIdMeterReadingTotal")
    public AjaxResult getCommunityIdMeterReadingTotal(
            @RequestParam("communityId") Long communityId,
            @RequestParam("energyConsumptionType") String energyConsumptionType,
            @RequestParam("meterReadingTime") String meterReadingTime
    ) {
        if (StringUtils.objectNotNull(communityId)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getCommunityIdMeterReadingTotal(communityId, energyConsumptionType, meterReadingTime));
    }

    /**
     * @Description 工单统计 执行状态个数
     * @Date 2022/11/15 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/orderSta/execute")
    public AjaxResult getExecuteNum(@RequestParam(value = "communityId", required = false) Long communityId) {
        if (StringUtils.objectNotNull(communityId)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getExecuteNum(communityId));
    }

    /**
     * @Description 待办任务 待办任务是创建了没有接单、接单未执行的工单
     * @Date 2022/11/15 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/toDoTask/toDoTask")
    public AjaxResult getToDoTaskNum(@RequestParam(value = "communityId", required = false) Long communityId) {
        if (StringUtils.objectNotNull(communityId)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getToDoTaskNum(communityId));
    }

    /**
     * @Description 待办任务  投诉建议 待办审核
     * @Date 2022/11/15 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/toDoTask/orToBeRe")
    public AjaxResult getOrToBeReNum(@RequestParam(value = "communityId", required = false) Long communityId) {
        if (StringUtils.objectNotNull(communityId)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getOrToBeReNum(communityId));
    }

    /**
     * @Description 小区水电总和 [communityId, meterType, meterTime] 小区id 、 0 水 1 电 2 燃气 、 抄表时间
     * @Date 2022/11/15 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/waterEle/total")
    public AjaxResult getTotalMeterTypeSum(@RequestParam(value = "communityId", required = false) Long communityId,
                                           @RequestParam(value = "meterType", required = false) Long meterType,
                                           @RequestParam(value = "meterTime", required = false) String meterTime) {
        if (StringUtils.objectNotNull(communityId, meterType, meterTime)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getTotalMeterTypeSum(communityId, meterType, meterTime));
    }

    /**
     * @Description 楼栋水电统计表 [communityId, meterType, meterTime] 小区id 、 0 水 1 电 2 燃气 、 抄表时间
     * @Date 2022/11/15 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/waterEle")
    public AjaxResult getMeterTypeSum(@RequestParam(value = "communityId", required = false) Long communityId,
                                      @RequestParam(value = "meterType", required = false) Long meterType,
                                      @RequestParam(value = "meterTime", required = false) String meterTime) {
        if (StringUtils.objectNotNull(communityId, meterTime, meterType)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getMeterTypeSum(communityId, meterType, meterTime));
    }

    /**
     * @Description 小区收益
     * [communityId, payTime] 小区id 、 缴费年 yyyy
     * @Date 2022/11/15 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/benefitCom")
    public AjaxResult getBenefitCommunity(@RequestParam(value = "communityId", required = false) Long communityId,
                                          @RequestParam(value = "payTime", required = false) String payTime) {
        if (StringUtils.objectNotNull(communityId, payTime)) {
            return AjaxResult.error("参数不能为空！");
        }
        return AjaxResult.success(workTableService.getBenefitCommunity(communityId, payTime));
    }
}
