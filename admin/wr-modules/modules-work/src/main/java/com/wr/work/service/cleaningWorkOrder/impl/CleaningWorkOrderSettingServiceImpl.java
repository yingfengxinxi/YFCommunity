package com.wr.work.service.cleaningWorkOrder.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.CleaningWorkOrderSetting;
import com.wr.work.mapper.cleaningWorkOrder.CleaningWorkOrderSettingMapper;
import com.wr.work.service.cleaningWorkOrder.CleaningWorkOrderSettingService;
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
            //执行次数
            Integer executionNumber = cleaningWorkOrderSetting.getExecutionNumber();
            //计算下次执行时间
            cleaningWorkOrderSetting.setFirstExecutionTime(extractedDate(firstExecutionTime, frequency, executionNumber));
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("计算下次执行时间,{}", redisKey, sim.format(cleaningWorkOrderSetting.getFirstExecutionTime()));
            //工单结束时间
            cleaningWorkOrderSetting.setOrderExpirationTime(cleaningWorkOrderSetting.getFirstExecutionTime());
            log.info("工单结束时间,{}", redisKey, sim.format(cleaningWorkOrderSetting.getOrderExpirationTime()));
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

    public static Date extractedDate(Date firstExecutionTime, String frequency, Integer executionNumber) throws ParseException {
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
        //每月
        if (StringUtils.equals(frequency, "2")) {
            //计算下次执行时间(小时)
            num = 720 / executionNumber;
        }
        String addedHour = DateUtils.addHour(firstExecutionTime, new BigDecimal(num));
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sim.parse(addedHour);
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstExecutionTime = sim.parse("2024-05-25 14:00:00");
        String frequency="0";
        Integer executionNumber=1;
        Date date = extractedDate(firstExecutionTime, frequency, executionNumber);
        System.out.println(sim.format(date));
    }
}
