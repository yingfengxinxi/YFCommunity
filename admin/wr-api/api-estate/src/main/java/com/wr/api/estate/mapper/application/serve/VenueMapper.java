package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.application.serve.VenueAppoint;
import com.wr.api.estate.entity.vo.application.serve.VenueAppointApiVo;
import com.wr.api.estate.entity.vo.application.serve.VenueAppointVO;
import com.wr.api.estate.entity.vo.application.serve.VenueVO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 15:03:55
 * @Desc:
 */
public interface VenueMapper {

    /**
     * 查询 场馆
     *
     * @param communityId
     * @param state
     * @return
     */
    List<VenueVO> searchByCommunityId(@Param("communityId") Long communityId, @Param("state") String state);

    /**
     * 查询场馆的预约人
     *
     * @param venueId
     * @return
     */
    List<LinkedHashMap<String, Object>> searchAppoint(@Param("venueId") Long venueId, @Param("time") String time);

    /**
     * 今日场馆的预约人
     *
     * @param venueId
     * @return
     */
    List<LinkedHashMap<String, Object>> searchTodayAppoint(Long venueId);

    /**
     * 详情
     *
     * @param venueId
     * @return
     */
    VenueVO detailById(Long venueId);

    /**
     * 预约详情
     *
     * @param appointId
     * @return
     */
    VenueAppointVO detailByAppointId(Long appointId);

    /**
     * 活动类型
     *
     * @param communityId
     * @return
     */
    List<LinkedHashMap<String, Object>> searchActByCommunityId(Long communityId);

    /**
     * 场馆使用规则
     *
     * @param communityId
     * @return
     */
    List<LinkedHashMap<String, Object>> searchRuleByCommunityId(Long communityId);

    /**
     * 新增
     *
     * @param venueAppoint
     * @return
     */
    int save(VenueAppoint venueAppoint);

    /**
     * 更新场馆预约单状态
     *
     * @param appointId
     * @return
     */
    int updatePay(Long appointId);

    /**
     * 活动预约时间是否冲突
     *
     * @param communityId
     * @param venueId
     * @param date        YYYY-MM-DD
     * @param time        HH:mm:ss
     * @return
     */
    int countPlanStart(@Param("communityId") Long communityId, @Param("venueId") Long venueId, @Param("date") String date, @Param("time") String time);

    /**
     * 开始时间是否有效
     *
     * @param communityId
     * @param venueId
     * @param date
     * @return
     */
    int dateValid(@Param("communityId") Long communityId, @Param("venueId") Long venueId, @Param("date") String date);

    /**
     * 开始结束时间是否已预约
     *
     * @param communityId
     * @param venueId
     * @param start
     * @param end
     * @return
     */
    int dateValidCount(@Param("communityId") Long communityId, @Param("venueId") Long venueId, @Param("start") String start, @Param("end") String end);

    /**
     * 查询场馆工单
     *
     * @param communityId
     * @return
     */
    List<VenueAppointApiVo> searchVenueAppoint(Long communityId);

    /**
     * 查看场馆工单详情
     *
     * @param appointId
     * @return
     */
    VenueAppointApiVo detailVenueAppoint(Long appointId);

    /**
     * 场馆工单终止
     *
     * @param username
     * @param appointId
     * @return
     */
    int terVenueAppoint(@Param("username") String username, @Param("appointId") Long appointId);
}
