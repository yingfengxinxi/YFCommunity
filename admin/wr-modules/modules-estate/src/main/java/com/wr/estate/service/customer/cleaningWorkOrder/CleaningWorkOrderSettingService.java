package com.wr.estate.service.customer.cleaningWorkOrder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.CleaningWorkOrder;
import com.wr.remote.domain.CleaningWorkOrderSetting;
import com.wr.remote.domain.InspectionSetting;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/4 15:12
 */
public interface CleaningWorkOrderSettingService extends IService<CleaningWorkOrderSetting> {


    /**
     * 向redis中更新工单执行时间
     *
     * @param inspectionSetting
     * @param flag
     * @throws ParseException
     */
    public void saveRedisData(CleaningWorkOrderSetting cleaningWorkOrderSetting, String flag) throws ParseException;

    /**
     * 计算下一次执行时间
     *
     * @param firstExecutionTime
     * @param frequency
     * @param executionNumber
     * @return
     * @throws ParseException
     */
    public Date extractedDate(Date firstExecutionTime, String frequency, Integer executionNumber) throws ParseException;
}
