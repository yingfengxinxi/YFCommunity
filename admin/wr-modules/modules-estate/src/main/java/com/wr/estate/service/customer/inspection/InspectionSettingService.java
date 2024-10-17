package com.wr.estate.service.customer.inspection;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.InspectionSetting;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:44
 */
public interface InspectionSettingService extends IService<InspectionSetting> {

    /**
     * 向redis中更新工单执行时间
     *
     * @param inspectionSetting
     * @param flag
     * @throws ParseException
     */
    public void saveRedisData(InspectionSetting inspectionSetting, String flag) throws ParseException;

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
