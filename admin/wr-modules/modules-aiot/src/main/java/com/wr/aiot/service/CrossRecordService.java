package com.wr.aiot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.aiot.CrossRecord;

/**
 * @Author lvzy
 * @Date 2024/4/8 18:16
 */
public interface CrossRecordService extends IService<CrossRecord> {

    /**
     *
     * @param type
     * @throws Exception
     */
    public void crossRecord(String type) throws Exception;
}
