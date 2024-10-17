package com.wr.remote.govern.manage.vo;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.govern.manage.VehicleLog;

import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-02 09:22:15
 * @Desc: 车辆进出记录视图类
 */
public class VehicleLogVO extends VehicleLog {

    /**
     * 查询时间
     */
    private String queryTime;


    public VehicleLogVO() {

    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

}
