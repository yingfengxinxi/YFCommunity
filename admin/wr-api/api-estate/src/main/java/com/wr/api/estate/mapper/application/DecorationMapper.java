package com.wr.api.estate.mapper.application;

import com.wr.remote.estate.customer.decorate.*;
import com.wr.remote.estate.customer.vo.decorate.DecorateInfoVo;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:36:24
 * @Desc: 装修列表
 */
@Mapper
public interface DecorationMapper {

    /**
     * 查询装修列表
     *
     * @param decorateVo
     * @return
     */
    List<DecorateVo> getDecorateVoList(DecorateVo decorateVo);

    /**
     * 查询装修详情
     *
     * @param id
     * @return
     */
    DecorateInfoVo getDecorationDetail(@Param("id") String id);

    /**
     * 查询装修工信息
     *
     * @param decorateId
     * @return
     */
    Worker getWorkerList(Long decorateId);

    /**
     * 根据装修id查询首次的装修人员
     *
     * @param decorateId
     * @return
     */
    Worker getWorkerListByDecorateId(Long decorateId);

    /**
     * 给每个装修工添加账号
     *
     * @param account
     * @return
     */
    Integer insertAccount(Account account);

    /**
     * 查询装修信息
     *
     * @param decorateId
     * @return
     */
    Decorate getDecorateByDecorateId(Long decorateId);


    /**
     * 将装修工审核通过
     *
     * @param worker
     */
    void workerAdopt(Worker worker);

    /**
     * 将装修审核通过
     *
     * @param decorate
     * @return
     */
    int decorateAdopt(Decorate decorate);

    /**
     * 装修驳回
     *
     * @param decorateVo
     * @return
     */
    Integer decorateReject(DecorateVo decorateVo);

    /**
     * 装修工驳回
     *
     * @param decorateVo
     */
    void workerReject(DecorateVo decorateVo);

    /**
     * 装修延期
     *
     * @param decorate
     */
    Integer decorateDelay(Decorate decorate);




    /**
     * @param decorateId
     * @return
     */
    List<DecorateViolation> getViolationList(Long decorateId);

    void updateByRenovationAuditStatus(BusDecorate busDecorate);
}
