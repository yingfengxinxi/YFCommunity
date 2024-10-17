package com.wr.estate.mapper.customer.decorate;

import com.wr.remote.estate.customer.decorate.Worker;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 13:32:49
 * @Desc: 装修台账
 */
@Mapper
public interface LedgerMapper {

    /**
     * 查询装修台账列表
     *
     * @param decorateVo
     * @return
     */
    List<DecorateVo> getDecorateLedgerList(DecorateVo decorateVo);

    /**
     * 查询装修台账详情
     *
     * @param decorateId
     * @return
     */
    DecorateVo getDecorateLedgerDetail(Long decorateId);

    /**
     * 查询装修工信息
     *
     * @param decorateId
     * @return
     */
    List<Worker> getWorkerList(Long decorateId);
}
