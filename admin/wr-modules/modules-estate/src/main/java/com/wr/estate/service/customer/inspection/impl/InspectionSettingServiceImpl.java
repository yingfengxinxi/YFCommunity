package com.wr.estate.service.customer.inspection.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.estate.mapper.customer.inspection.InspectionSettingMapper;
import com.wr.estate.service.customer.inspection.InspectionSettingService;
import com.wr.remote.domain.InspectionSetting;
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
 * @Date 2024/2/1 21:45
 */
@Slf4j
@Service
public class InspectionSettingServiceImpl extends ServiceImpl<InspectionSettingMapper, InspectionSetting> implements InspectionSettingService {

    @Autowired
    private RedisService redisService;

    /**
     * @param inspectionSetting
     * @param flag              0=新增/修改1=删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRedisData(InspectionSetting inspectionSetting, String flag) throws ParseException {
        //向Redis中添加值
        String redisKey = CacheConstants.XC_WORK_ORDER + inspectionSetting.getEstateId() + ":" + inspectionSetting.getCommunityId() + ":" + inspectionSetting.getId();
        InspectionSetting is = redisService.getCacheObject(redisKey);
        if (is != null) {
            redisService.deleteObject(redisKey);
        }
        if (flag.equals("0")) {
            String frequency = inspectionSetting.getFrequency();
            //首次执行时间
            //TODO 测试
            Date firstExecutionTime = inspectionSetting.getFirstExecutionTime();
            Long minute = DateUtils.getMinute(new Date(), firstExecutionTime);
            System.out.println("首次执行时间>>" + minute);
            //执行次数
            Integer executionNumber = inspectionSetting.getExecutionNumber();
            //计算下次执行时间
            inspectionSetting.setFirstExecutionTime(extractedDate(firstExecutionTime, frequency, executionNumber));
            inspectionSetting.setOrderExpirationTime(inspectionSetting.getFirstExecutionTime());
            //存入下次工单执行时间
            log.info("START存入下次巡查工单执行时间:{},{},{},{}", redisKey, inspectionSetting.toString(), minute, TimeUnit.MINUTES);
            redisService.setCacheObject(redisKey, inspectionSetting, minute, TimeUnit.MINUTES);

            super.updateById(inspectionSetting);
            log.info("END存入下次巡查工单执行时间:{}", inspectionSetting.toString());
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