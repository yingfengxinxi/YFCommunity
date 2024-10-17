package com.wr.api.estate.service.application.worktable;

import com.wr.common.core.web.domain.AjaxResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DIANWEI
 * @Description 工作台 service
 * @date 2022/11/15 9:42
 */
public interface WorkTableService {

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 数据统计  出行车辆数 出行人数 出行访客数
     * @Param [communityId] 当前登录人小区
     * @Date 2022/11/15 11:25
     * @Author DIANWEI
     **/
    public Map<String, Object> getDataStatistics(Long communityId);

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 工单统计 好评率
     * @Param [communityId]  小区id
     * @Date 2022/11/15 13:35
     * @Author DIANWEI
     **/
    public Map<String, Object> getAppraiseNum(Long communityId,String bxecutorBy);

    public Map<String, Object> getMonthDbOrder(Long communityId);

    /**
     * @param communityId
     * @param energyConsumptionType
     * @param meterReadingTime
     * @return
     */
    BigDecimal getCommunityIdMeterReadingTotal( Long communityId, String energyConsumptionType, String meterReadingTime);

    /**
     * @return java.lang.Integer
     * @Description 工单统计 执行状态个数
     * @Param [communityId] 小区id
     * @Date 2022/11/15 13:30
     * @Author DIANWEI
     **/
    public Map<String, Object> getExecuteNum(Long communityId);

    /**
     * @return java.lang.Integer
     * @Description 待办任务 待办任务是创建了没有接单、接单未执行的工单
     * @Param [communityId] 小区id
     * @Date 2022/11/15 13:30
     * @Author DIANWEI
     **/
    public Map<String, Object> getToDoTaskNum(Long communityId);


    /**
     * @return java.util.Map
     * @Description 待办任务  投诉建议 待办审核
     * @Param [communityId] 小区id
     * @Date 2022/11/15 14:56
     * @Author DIANWEI
     **/
    Map<String, Object> getOrToBeReNum(Long communityId);


    /**
     * @return java.util.Map
     * @Description 小区水电总和
     * @Param [communityId, meterType, meterTime] 小区id 、 0 水 1 电 2 燃气 、 抄表时间
     * @Date 2022/11/15 19:03
     * @Author DIANWEI
     **/
    Map<String, Object> getTotalMeterTypeSum(Long communityId,
                                             Long meterType, String meterTime);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Description 楼栋水电统计表
     * @Param [communityId, meterType, meterTime] 小区id 、 0 水 1 电 2 燃气 、 抄表时间
     * @Date 2022/11/15 19:03
     * @Author DIANWEI
     **/
    List<Map<String, Object>> getMeterTypeSum(Long communityId,
                                              Long meterType, String meterTime);

    /**
     * @return java.util.Map
     * @Description 小区收益
     * @Param [ communityId, payTime]  小区id 、缴费年
     * @Date 2022/11/16 10:18
     * @Author DIANWEI
     **/
    Map<String, Object> getBenefitCommunity(Long communityId, String payTime);

    AjaxResult getFoundData(Long communityId,String date);
}
