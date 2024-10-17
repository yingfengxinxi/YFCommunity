package com.wr.api.owner.service.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.api.owner.entity.serve.BusStaffEvaluation;
import com.wr.remote.estate.manage.Staff;

/**
 * @Author: xuShu
 * @Date: 2022-11-11 15:14:10
 * @Desc:
 */
public interface BusStaffEvaluationService extends IService<BusStaffEvaluation> {

    /**
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @Author SUNk
     * @Description 根据员工ID查询员工信息
     * @Date 14:53 2024/3/17
     * @Param [id]
     **/
    Staff findOneBusStaff(Long staffId);
}
