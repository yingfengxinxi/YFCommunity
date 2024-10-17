package com.wr.api.estate.mapper.application.worktable;

import com.wr.remote.domain.vo.GetFoundDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author DIANWEI
 * @Description 工作台 mapper层
 * @date 2022/11/15 9:42
 */
@Mapper
public interface WorkTableMapper {
    /**
     * 检查是否存在表
     *
     * @param tableName 表名
     * @return
     */
    Integer existTable(@Param("tableName") String tableName);

    /**
     * 查询今日访客数
     *
     * @param communityId
     **/
    public Integer getDayVisitorNum(@Param("communityId") Long communityId);

    /**
     * @param communityId
     * @param reportStatus
     * @param executorBy
     * @return
     */
    public Integer getReportingOrderCount(@Param("communityId") Long communityId, @Param("reportStatus") String reportStatus, @Param("executorBy") String executorBy, @Param("workPostList") List<String> workPostList);


    public Integer getCleaningWorkOrderCount(@Param("communityId") Long communityId);


    /**
     * 查询今日出入人数
     *
     * @param communityId
     * @param eventTypes
     * @return
     */
    Integer getUserInOutNum(@Param("communityId") Long communityId, @Param("eventTypes") List<String> eventTypes);

    /**
     * 查询今日出行车辆数
     *
     * @param communityId
     * @return
     */
    Integer getVehicleNum(@Param("communityId") Long communityId);


    /**
     * @param communityId
     * @param executorBy
     * @param workPostList
     * @return
     */
    Integer getAppraiseNum(@Param("evaluate") Long evaluate, @Param("communityId") Long communityId, @Param("executorBy") String executorBy, @Param("workPostList") List<String> workPostList);

    /**
     * @param communityId
     * @return
     */
    Integer getPatrolWorkOrderCount(@Param("communityId") Long communityId);

    /**
     * 投诉建议
     *
     * @param communityId
     * @return
     */
    Integer getComplaintSuggestionCount(@Param("communityId") Long communityId);

    /**
     * @param communityId
     * @return
     */
    Integer getDecorateCount(@Param("communityId") Long communityId);

    /**
     * @param communityId
     * @param energyConsumptionType
     * @param meterReadingTime
     * @return
     */
    BigDecimal getCommunityIdMeterReadingTotal(@Param("communityId") Long communityId, @Param("energyConsumptionType") String energyConsumptionType, @Param("meterReadingTime") String meterReadingTime);

    /**
     * @param communityId
     * @return
     */
    Integer getInspectionWorkOrderCount(@Param("communityId") Long communityId);

    /**
     * @param communityId
     * @return
     */
    Integer getMeterReadingWorkOrderCount(@Param("communityId") Long communityId);

    /**
     * @return java.lang.Integer
     * @Description 工单统计 执行状态个数
     * @Param [communityId, allotStatus, acceptStatus, executeStatus] 小区id 、 分配状态：0=待分配，1=已分配 、接单状态：0=待接单，1=已接单、执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     * @Date 2022/11/15 14:56
     * @Author DIANWEI
     **/
    Integer getExecuteNum(@Param("communityId") Long communityId, @Param("allotStatus") Long allotStatus, @Param("acceptStatus") Long acceptStatus, @Param("executeStatus") Long executeStatus);

    /**
     * @return java.lang.Integer
     * @Description 待办任务  投诉建议
     * @Param [communityId] 小区id
     * @Date 2022/11/15 14:56
     * @Author DIANWEI
     **/
    Integer getOrComplaintNum(@Param("communityId") Long communityId);

    /**
     * @return java.lang.Integer
     * @Description 待办任务  待办审核
     * @Param [communityId] 小区id
     * @Date 2022/11/15 14:56
     * @Author DIANWEI
     **/
    Integer getOrToBeReNum(@Param("communityId") Long communityId);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Description 小区水电总和
     * @Param [communityId, meterType, meterTime] 小区id 、 0 水 1 电 2 燃气 、 抄表时间
     * @Date 2022/11/15 19:03
     * @Author DIANWEI
     **/
    Map<String, Object> getTotalMeterTypeSum(@Param("communityId") Long communityId,
                                             @Param("meterType") Long meterType, @Param("meterTime") String meterTime);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Description 楼栋水电统计表
     * @Param [communityId, meterType, meterTime] 小区id 、 0 水 1 电 2 燃气 、 抄表时间
     * @Date 2022/11/15 19:03
     * @Author DIANWEI
     **/
    List<Map<String, Object>> getMeterTypeSum(@Param("communityId") Long communityId,
                                              @Param("meterType") Long meterType, @Param("meterTime") String meterTime);

    /**
     * @return java.math.BigDecimal
     * @Description 缴费金额
     * @Param [tableName, communityId, payType, payTime] 动态表名 、 小区id 、 缴费类型：0=停车缴费，2=水费，3=电费，4=其他 、缴费年
     * @Date 2022/11/16 10:18
     * @Author DIANWEI
     **/
    BigDecimal getAmountPaid(@Param("tableName") String tableName, @Param("communityId") Long communityId,
                             @Param("payType") Long payType, @Param("payTime") String payTime);

    /**
     * @return java.math.BigDecimal
     * @Description 其他缴费金额
     * @Param [tableName, communityId, payType, payTime] 动态表名 、 小区id 、 缴费类型：0=停车缴费，2=水费，3=电费，4=其他 、缴费年
     * @Date 2022/11/16 10:18
     * @Author DIANWEI
     **/
    BigDecimal getOtherAmountPaid(@Param("tableName") String tableName, @Param("communityId") Long communityId,
                                  @Param("payType") Long payType, @Param("payTime") String payTime);

    /**
     * @return java.math.BigDecimal
     * @Description 物业缴费金额
     * @Param [tableName, communityId, payType, payTime]  小区id  、缴费年
     * @Date 2022/11/16 10:18
     * @Author DIANWEI
     **/
    BigDecimal getProAmountPaid(@Param("communityId") Long communityId,
                                @Param("payTime") String payTime);

    /**
     * @param communityId
     * @return
     */
    List<GetFoundDataVo> getFoundData(@Param("communityId") Long communityId, @Param("date") String date);

    /**
     *
     * @param communityId
     * @return
     */
    Integer getPatrolCount(@Param("communityId") Long communityId);
}
