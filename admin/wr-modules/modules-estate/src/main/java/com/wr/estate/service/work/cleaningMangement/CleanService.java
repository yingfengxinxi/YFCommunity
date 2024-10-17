package com.wr.estate.service.work.cleaningMangement;

import com.wr.estate.entity.vo.CleanExecuteNameAndnumbersVO;
import com.wr.estate.entity.vo.CleanExecuteNameVO;
import com.wr.estate.entity.vo.CleanVo;
import com.wr.estate.entity.vo.Executor;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.cleaningManagement.Clean;
import com.wr.remote.work.inspect.OrderRecord;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-31 15:57:40
 * @Desc:
 */
public interface CleanService {

    /**
     * 根据计划ID查询生成的工单
     * @param planId
     * @return
     */
    List<CleanVo> selectListByPlanId(Long planId, Long communityId);

    /**
     * 查询保洁工单集合
     * @param clean
     * @return
     */
    CleanExecuteNameAndnumbersVO selectCleanList(Clean clean);

    /**
     * 根据工单ID查询单个工单信息
     * @param cleanId
     * @return
     */
    CleanVo selectCleanOne(Long cleanId);

    /**
     * 修改或新增工单信息
     * @param clean
     * @return
     */
    int insertAndupdataCleanOneand(Clean clean);

    /**
     * 删除
     * @param cleanIds
     * @return
     */
    void delectClean(Long[] cleanIds);

    /**
     * 查询所有为保洁员工
     * @param clean
     * @return
     */
    List<Staff> getsafftName(Clean clean);

    /**
     * 分配负责人
     * @param clean
     * @return
     */
    int updataEexecuteIdAndstaffId(Clean clean);

    /**
     * 查询出工单执行人列表
     * @param cleanId
     * @return
     */
    List<Executor> getExecutorList(Long cleanId);


    /**
     * 查询工单的执行流程过程
     * @param cleanId
     * @return
     */
    List<OrderRecord>  selectOrderRecordList(Long cleanId);


    /**
     * 保洁计划定时任务
     */
    void  scheduledCleaningTasks();

}
