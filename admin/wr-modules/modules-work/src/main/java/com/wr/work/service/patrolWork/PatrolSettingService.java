package com.wr.work.service.patrolWork;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.PatrolSetting;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/4/16 16:51
 */
public interface PatrolSettingService extends IService<PatrolSetting> {

    /**
     *
     * @param patrolSetting
     * @param flag
     * @throws ParseException
     */
    public void saveRedisData(PatrolSetting patrolSetting, String flag) throws ParseException;

    /**
     *
     * @param firstExecutionTime
     * @param frequency
     * @param executionNumber
     * @return
     * @throws ParseException
     */
    public Date extractedDate(Date firstExecutionTime, String frequency, Integer executionNumber) throws ParseException;
}
