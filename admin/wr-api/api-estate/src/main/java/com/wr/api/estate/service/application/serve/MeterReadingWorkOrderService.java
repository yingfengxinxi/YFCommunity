package com.wr.api.estate.service.application.serve;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.MeterReadingEquipment;
import com.wr.remote.domain.MeterReadingWorkOrder;
import com.wr.remote.domain.PatrolWorkOrder;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/5 12:28
 */
public interface MeterReadingWorkOrderService extends IService<MeterReadingWorkOrder> {

    /**
     * @param meterReadingWorkOrder
     * @return
     */
    List<MeterReadingWorkOrder> getList(MeterReadingWorkOrder meterReadingWorkOrder);

    /**
     * @param id
     * @return
     */
    MeterReadingWorkOrder get(String id);

    /**
     * @param meterReadingWorkOrder
     */
    public void saveMeterReadingWorkOrder(MeterReadingWorkOrder meterReadingWorkOrder);


    /**
     * @param communityId
     * @param energyConsumptionType
     * @param meterReadingType
     * @return
     */
    public List<MeterReadingEquipment> meterReadingEquipmentList(Long communityId, String energyConsumptionType, String meterReadingType);

}
