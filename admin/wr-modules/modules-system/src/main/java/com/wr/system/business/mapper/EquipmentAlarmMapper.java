package com.wr.system.business.mapper;


import com.wr.remote.aiot.WarnEvent;
import com.wr.system.business.domain.screen.AnnualMaintenanceEquipmentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备告警mapper层
 */
public interface EquipmentAlarmMapper {

    Long getAcsNum(Long communityId );

    Long getFireNum(Long communityId);

    Long getVideoNum(Long communityId);

    Long getVisitNum(Long communityId);

    /**
     * 拿到一年每个月对应的维修次数
     * @param year:年份
     * @return 月份-维修次数集合
     */
    List<AnnualMaintenanceEquipmentVo> getMonthlyMaintenanceStatistics(@Param("year")Integer year, @Param("communityId") Long communityId);

    /**
     * 查询七天的设备预警
     * @param warnEvent
     * @return
     */
    List<WarnEvent> selectWarnEvent(WarnEvent warnEvent);
}
