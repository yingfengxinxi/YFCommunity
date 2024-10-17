package com.wr.system.ReportingWarranty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.system.ReportingWarranty.domain.ReportingWarranty;
import com.wr.system.business.domain.HomeScoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/1/31 21:06
 */
public interface SystemReportingWarrantyMapper extends BaseMapper<ReportingWarranty> {

    /**
     * @param reportingWarranty
     * @return
     */
    List<ReportingWarranty> getList(ReportingWarranty reportingWarranty);


    /**
     * @param id
     * @return
     */
    ReportingWarranty get(@Param("id") String id);

    HomeScoreVO getCount(@Param("communityId") Long communityId);

    List<Map<String, String>> getAllCommunity();

    /**
     * @param communityId   小区id
     * @param eventTypeList 表名
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return 个数
     */
    public List<Map<String, Object>> getUserNumListDay(@Param("communityId") Long communityId, @Param("eventTypeList") List<String> eventTypeList, @Param("startTime") String startTime, @Param("endTime") String endTime);

    public List<Map<String, Object>> getUserNumListMonth(@Param("communityId") Long communityId, @Param("eventTypeList") List<String> eventTypeList, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 每日车行
     *
     * @param communityId 小区id
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 个数
     */
    public List<Map<String, Object>> getVehicleNumListDay(@Param("communityId") Long communityId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    public List<Map<String, Object>> getVehicleNumListMonth(@Param("communityId") Long communityId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    public List<Map<String, Object>> findListBusDoor(@Param("communityId") Long communityId);
}
