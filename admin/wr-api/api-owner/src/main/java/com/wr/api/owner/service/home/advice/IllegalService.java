package com.wr.api.owner.service.home.advice;

import com.wr.api.owner.entity.home.advice.Illegal;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 15:49:52
 * @Desc: 违法举报
 */
public interface IllegalService {
    /**
     * 查询列表
     *
     * @param illegal
     * @return
     */
    List<Illegal> selectIllegalList(Illegal illegal);


    /**
     * 根据id查详情
     *
     * @param illegalId
     * @return
     */
    Illegal getIllegalById(Long illegalId);

    /**
     * 新增
     *
     * @param illegal
     * @return
     */
    Integer insertIllegal(Illegal illegal);


}
