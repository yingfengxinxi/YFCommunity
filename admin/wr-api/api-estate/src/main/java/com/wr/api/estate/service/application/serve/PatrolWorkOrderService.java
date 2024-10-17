package com.wr.api.estate.service.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.PatrolWorkOrder;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/2 14:49
 */
public interface PatrolWorkOrderService extends IService<PatrolWorkOrder> {

    /**
     *
     * @param patrolWorkOrder
     * @return
     */
    List<PatrolWorkOrder> getList(PatrolWorkOrder patrolWorkOrder);

    /**
     *
     * @param id
     * @return
     */
    PatrolWorkOrder get(String id);

    /**
     *
     * @param patrolWorkOrder
     */
    void savePatrolWorkOrder(PatrolWorkOrder patrolWorkOrder);

    /**
     *
     * @param patrolWorkOrder
     */
    void updatePatrolContent(PatrolWorkOrder patrolWorkOrder);
}
