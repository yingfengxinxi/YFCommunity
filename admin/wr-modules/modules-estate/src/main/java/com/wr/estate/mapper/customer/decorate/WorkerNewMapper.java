package com.wr.estate.mapper.customer.decorate;

import com.wr.remote.estate.customer.decorate.Account;
import com.wr.remote.estate.customer.decorate.Decorate;
import com.wr.remote.estate.customer.decorate.Liaison;
import com.wr.remote.estate.customer.decorate.Worker;
import com.wr.remote.estate.customer.vo.decorate.WorkerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 17:10:07
 * @Desc: 新增的装修工
 */
@Mapper
public interface WorkerNewMapper {

    /**
     * 获取新增的装修工列表
     *
     * @param workerVo
     * @return
     */
    List<WorkerVo> getWorkerNewList(WorkerVo workerVo);

    /**
     * 查询新增的装修工详情
     *
     * @param workerId
     * @return
     */
    WorkerVo getWorkerNewDetail(Long workerId);

    /**
     * 根据workerId查询装修工人信息
     *
     * @param workerId
     * @return
     */
    Worker getWorker(Long workerId);

    /**
     * 查询装修信息
     *
     * @param decorateId
     * @return
     */
    Decorate getDecorateByDecorateId(Long decorateId);

    /**
     * 给每个装修工添加账号
     *
     * @param account
     * @return
     */
    Integer insertAccount(Account account);

    /**
     * 给装修工设置通行证
     *
     * @param liaison
     */
    void insertLiaison(Liaison liaison);

    /**
     * 将装修工审核通过
     *
     * @param worker
     */
    void workerAdopt(Worker worker);

    /**
     * 装修工审核驳回
     *
     * @param worker
     */
    Integer workerNewReject(Worker worker);

}
