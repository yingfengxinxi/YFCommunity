package com.wr.estate.service.customer.cleaningWorkOrder.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.estate.mapper.customer.cleaningWorkOrder.CleaningWorkOrderSettingMapper;
import com.wr.estate.service.customer.cleaningWorkOrder.CleaningWorkOrderSettingService;
import com.wr.remote.domain.CleaningWorkOrderSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author lvzy
 * @Date 2024/2/4 15:12
 */
@Slf4j
@Service
public class CleaningWorkOrderSettingServiceImpl extends ServiceImpl<CleaningWorkOrderSettingMapper, CleaningWorkOrderSetting> implements CleaningWorkOrderSettingService {


    @Autowired
    private RedisService redisService;

    /**
     * @param cleaningWorkOrderSetting
     * @param flag                     0=新增/修改1=删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRedisData(CleaningWorkOrderSetting cleaningWorkOrderSetting, String flag) throws ParseException {
        //向Redis中添加值
        String redisKey = CacheConstants.QJ_WORK_ORDER + cleaningWorkOrderSetting.getEstateId() + ":" + cleaningWorkOrderSetting.getCommunityId() + ":" + cleaningWorkOrderSetting.getId();
        CleaningWorkOrderSetting cwos = redisService.getCacheObject(redisKey);
        if (cwos != null) {
            redisService.deleteObject(redisKey);
        }
        if (flag.equals("0")) {
            String frequency = cleaningWorkOrderSetting.getFrequency();
            //首次执行时间
            Date firstExecutionTime = cleaningWorkOrderSetting.getFirstExecutionTime();
            Long minute = DateUtils.getMinute(new Date(), firstExecutionTime);
            if (minute < 0) {
                minute = DateUtils.getMinute(new Date(), cleaningWorkOrderSetting.getOrderExpirationTime());
            }
            //执行次数
            Integer executionNumber = cleaningWorkOrderSetting.getExecutionNumber();
            //计算下次执行时间
            cleaningWorkOrderSetting.setFirstExecutionTime(extractedDate(firstExecutionTime, frequency, executionNumber));
            cleaningWorkOrderSetting.setOrderExpirationTime(cleaningWorkOrderSetting.getFirstExecutionTime());
            //存入下次工单执行时间
            log.info("START存入下次清洁工单执行时间:{},{},{},{}", redisKey, cleaningWorkOrderSetting.toString(), minute, TimeUnit.MINUTES);
            redisService.setCacheObject(redisKey, cleaningWorkOrderSetting, minute, TimeUnit.MINUTES);

            super.updateById(cleaningWorkOrderSetting);
            log.info("END存入下次清洁工单执行时间:{}", cleaningWorkOrderSetting.toString());
        }
    }

    /**
     * 计算下次执行时长
     *
     * @param executionNumber
     * @throws ParseException
     */
    @Override
    public Date extractedDate(Date firstExecutionTime, String frequency, Integer executionNumber) throws ParseException {
        Integer num = 0;
        if (StringUtils.equals(frequency, "0")) {
            //每日
            //计算下次执行时间(小时)
            num = 7 / executionNumber;
        }

        if (StringUtils.equals(frequency, "1")) {
            //每半月
            //计算下次执行时间(小时)
            num = 360 / executionNumber;
        }
        //每日
        if (StringUtils.equals(frequency, "2")) {
            //计算下次执行时间(小时)
            num = 720 / executionNumber;
        }
        String addedHour = DateUtils.addHour(firstExecutionTime, new BigDecimal(num));
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sim.parse(addedHour);
    }
}
