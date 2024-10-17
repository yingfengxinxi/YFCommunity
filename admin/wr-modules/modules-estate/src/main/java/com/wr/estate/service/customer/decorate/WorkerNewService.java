package com.wr.estate.service.customer.decorate;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.decorate.Worker;
import com.wr.remote.estate.customer.vo.decorate.WorkerVo;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 17:09:22
 * @Desc: 新增的装修工
 */
public interface WorkerNewService {

    /**
     * 获取新增的装修工列表
     *
     * @param workerVo
     * @return
     */
    List<WorkerVo> getWorkerNewList(WorkerVo workerVo);

    /**
     * 查看新增的装修工详情
     *
     * @param decorateId
     * @return
     */
    AjaxResult getWorkerNewDetail(Long decorateId);

    /**
     * 装修工审核通过
     *
     * @param workerIds
     * @return
     */
    int workerNewAdopt(Long[] workerIds);

    /**
     * 装修工审核驳回
     *
     * @param worker
     * @return
     */
    int workerNewReject(Worker worker);

    /**
     * 装修工审核单个驳回
     *
     * @param worker
     * @return
     */
    int workerNewRejectOne(Worker worker);
}
