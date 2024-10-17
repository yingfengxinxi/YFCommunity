package com.wr.api.estate.mapper.application;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.domain.PatrolWorkOrder;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/2 14:00
 */
public interface PatrolWorkOrderMapper extends BaseMapper<PatrolWorkOrder> {


    /**
     *
     * @param patrolWorkOrder
     * @return
     */
    List<PatrolWorkOrder> getList(PatrolWorkOrder patrolWorkOrder);
}
