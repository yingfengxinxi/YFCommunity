package com.wr.api.device.mapper;

import com.wr.remote.aiot.WarnEvent;

/**
 * @Author: RainCity
 * @Date: 2022-02-11 15:02:43
 * @Desc:
 */
public interface WarnEventMapper {

    /**
     * 添加联动事件
     * @param warnEvent
     * @return
     */
    Integer insertWarnEvent(WarnEvent warnEvent);
}
