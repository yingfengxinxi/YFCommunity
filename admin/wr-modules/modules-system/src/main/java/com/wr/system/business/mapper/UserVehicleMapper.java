package com.wr.system.business.mapper;

import com.wr.system.business.domain.screen.MonthVisit;
import com.wr.system.business.domain.screen.UserVehicle;

import java.util.List;

/**
 * @Author: BaoYu
 * @Date: 2021-12-31 17:23:39
 * @Desc:
 */
public interface UserVehicleMapper {

    List<UserVehicle> getUserCount(String tableName);

    List<UserVehicle> getVehicleCount(String tableName);

    /**
     * 按月份查询访客次数
     */
    List<MonthVisit> selectMonthVisitCount(String tableName);

    /**
     * 按年查询每月访客次数
     *
     */
    Integer selectYearVisitCount(String tableName);
}
