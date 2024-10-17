package com.wr.aiot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.aiot.VisualSecord;

/**
 * @Author lvzy
 * @Date 2024/4/14 23:20
 */
public interface VisualSecordService extends IService<VisualSecord> {

    /**
     *
     * @param type
     * @param startTime
     * @param endTime
     * @throws Exception
     */
    public void visualSecord(String type,String startTime,String endTime) throws Exception;
}
