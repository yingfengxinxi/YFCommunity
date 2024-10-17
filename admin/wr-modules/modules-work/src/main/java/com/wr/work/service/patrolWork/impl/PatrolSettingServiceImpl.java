package com.wr.work.service.patrolWork.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.InspectionSetting;
import com.wr.remote.domain.PatrolSetting;
import com.wr.work.mapper.patorlWork.PatrolSettingMapper;
import com.wr.work.service.patrolWork.PatrolSettingService;
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
 * @Date 2024/4/16 16:51
 */
@Service
@Slf4j
public class PatrolSettingServiceImpl extends ServiceImpl<PatrolSettingMapper, PatrolSetting> implements PatrolSettingService {

    @Autowired
    private RedisService redisService;

    /**
     * @param patrolSetting
     * @param flag              0=新增/修改1=删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRedisData(PatrolSetting patrolSetting, String flag) throws ParseException {
        //向Redis中添加值
        String redisKey = CacheConstants.XG_WORK_ORDER + patrolSetting.getEstateId() + ":" + patrolSetting.getCommunityId() + ":" + patrolSetting.getId();
        InspectionSetting is = redisService.getCacheObject(redisKey);
        if (is != null) {
            redisService.deleteObject(redisKey);
        }
        if (flag.equals("0")) {
            String frequency = patrolSetting.getFrequency();
            //首次执行时间
            //TODO 测试
            Date firstExecutionTime = patrolSetting.getFirstExecutionTime();
            Long minute = DateUtils.getMinute(new Date(), firstExecutionTime);
            System.out.println("首次执行时间>>" + minute);
            //执行次数
            Integer executionNumber = patrolSetting.getExecutionNumber();
            //计算下次执行时间
            patrolSetting.setFirstExecutionTime(extractedDate(firstExecutionTime, frequency, executionNumber));
            patrolSetting.setOrderExpirationTime(patrolSetting.getFirstExecutionTime());
            //存入下次工单执行时间
            log.info("START存入下次巡更工单执行时间:{},{},{},{}", redisKey, patrolSetting.toString(), minute, TimeUnit.MINUTES);
            redisService.setCacheObject(redisKey, patrolSetting, minute, TimeUnit.MINUTES);

            super.updateById(patrolSetting);
            log.info("END存入下次巡更工单执行时间:{}", patrolSetting.toString());
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
