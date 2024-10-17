package com.wr.work.service.cleaningWorkOrder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.CleaningWorkOrderSetting;

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
     * @param cleaningWorkOrderSetting
     * @param flag
     * @throws ParseException
     */
    public void saveRedisData(CleaningWorkOrderSetting cleaningWorkOrderSetting, String flag) throws ParseException;

}
