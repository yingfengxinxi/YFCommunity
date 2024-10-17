package com.wr.estate.service.customer.cleaningWorkOrder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.CleaningWorkOrder;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:43
 */
public interface CleaningWorkOrderService extends IService<CleaningWorkOrder> {

    /**
     *
     * @param id
     * @return
     */
    CleaningWorkOrder get(String id);

    List<CleaningWorkOrder> getList(LambdaQueryWrapper<CleaningWorkOrder> queryWrapper);
}
