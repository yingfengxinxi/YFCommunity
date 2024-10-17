package com.wr.estate.mapper;

import com.wr.remote.estate.complaint.report.BusIllegal;

import java.util.List;

/**
 * @description 违法举报mapper
 * @author 荀彧
 * @date 2022-10-13
 */
public interface BusIllegalMapper {

    /**
     * 查询 根据主键 id 查询
     **/
    BusIllegal load(int illegalId);

    /**
     * 查询 分页查询
     **/
    List<BusIllegal> pageList(BusIllegal busIllegal);



}
