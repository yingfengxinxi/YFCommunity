package com.wr.api.estate.service.application.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.CleaningWorkOrder;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:43
 */
public interface CleaningWorkOrderService extends IService<CleaningWorkOrder> {

    /**
     *
     * @param cleaningWorkOrder
     */
    void add(CleaningWorkOrder cleaningWorkOrder);

    /**
     *
     * @param id
     * @return
     */
    CleaningWorkOrder get(String id);
}
