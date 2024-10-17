package com.wr.estate.service;

import com.wr.remote.estate.complaint.report.BusIllegal;

import java.util.List;

/**
 * @description 违法举报service
 * @author 荀彧
 * @date 2022-10-13
 */
public interface IllegalService {

    /**
     * 分页查询
     * @param busIllegal
     * @return
     */
    List<BusIllegal> pageList(BusIllegal busIllegal);


    /**
     * 查询 根据主键 id 查询
     **/
    BusIllegal load(int illegalId);
}
