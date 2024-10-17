package com.wr.estate.service.customer.patrol;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.InspectionSetting;
import com.wr.remote.domain.PatrolWorkOrderSetting;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/3/2 9:56
 */
public interface PatrolWorkOrderSettingService extends IService<PatrolWorkOrderSetting> {

    /**
     * 向redis中更新工单执行时间
     *
     * @param patrolWorkOrderSetting
     * @param flag
     * @throws ParseException
     */
    public void saveRedisData(PatrolWorkOrderSetting patrolWorkOrderSetting, String flag) throws ParseException;
}
