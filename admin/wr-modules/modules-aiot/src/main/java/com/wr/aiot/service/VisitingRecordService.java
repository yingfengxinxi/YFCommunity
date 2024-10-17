package com.wr.aiot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.aiot.VisitingRecord;

/**
 * @Author lvzy
 * @Date 2024/4/10 16:48
 */
public interface VisitingRecordService extends IService<VisitingRecord> {


    /**
     *
     * @param type
     * @throws Exception
     */
    public void visitingRecord(String type) throws Exception;
}
