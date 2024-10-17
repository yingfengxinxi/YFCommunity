package com.wr.work.service.patrol.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.PatrolWorkOrderSetting;
import com.wr.work.mapper.patrol.PatrolWorkOrderSettingMapper;
import com.wr.work.service.patrol.PatrolWorkOrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author lvzy
 * @Date 2024/3/2 9:56
 */
@Slf4j
@Service
public class PatrolWorkOrderSettingServiceImpl extends ServiceImpl<PatrolWorkOrderSettingMapper, PatrolWorkOrderSetting> implements PatrolWorkOrderSettingService {

    @Autowired
    private RedisService redisService;

    /**
     * @param patrolWorkOrderSetting
     * @param flag                   0=新增/修改1=删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRedisData(PatrolWorkOrderSetting patrolWorkOrderSetting, String flag) throws ParseException {
        //向Redis中添加值
        String redisKey = CacheConstants.XJ_WORK_ORDER + patrolWorkOrderSetting.getEstateId() + ":" + patrolWorkOrderSetting.getCommunityId() + ":" + patrolWorkOrderSetting.getId();
        PatrolWorkOrderSetting is = redisService.getCacheObject(redisKey);
        if (is != null) {
            redisService.deleteObject(redisKey);
        }
        if (flag.equals("0")) {
            //执行频率0=每日=1每月
            String frequency = patrolWorkOrderSetting.getFrequency();
            Long minute = 0L;
            Date firstExecutionTime = null;
            if (frequency.equals("0")) {
                //每日
                //首次执行时间
                firstExecutionTime = getDayDate();

            } else {
                //每月
                firstExecutionTime = getMoneyDate(patrolWorkOrderSetting.getId());
            }
            log.info("当前时间:{}", new Date());
            log.info("未来时间:{}", firstExecutionTime);
            patrolWorkOrderSetting.setOrderExpirationTime(firstExecutionTime);
            //计算到期生成工单时间
            minute = DateUtils.getMinute(new Date(), firstExecutionTime);
            log.info("生成工单时间:{}", minute);

            //存入下次工单执行时间
            log.info("START存入下次巡检工单执行时间:{},{},{},{}", redisKey, patrolWorkOrderSetting.toString(), minute, TimeUnit.MINUTES);
            redisService.setCacheObject(redisKey, patrolWorkOrderSetting, minute, TimeUnit.MINUTES);
            super.updateById(patrolWorkOrderSetting);
            log.info("END存入下次巡检工单执行时间:{}", patrolWorkOrderSetting.toString());
        }
    }

    /**
     * @return
     * @throws ParseException
     */
    private Date getMoneyDate(String id) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String isOrder = baseMapper.selectById(id).getIsOrder();
        if (StringUtils.equals(isOrder, "0")) {
            //首次执行
            Date nextDate = DateUtils.getNextDate(1);
            String firstDate = sim.format(nextDate);
            firstDate = firstDate.substring(0, 10) + " 07:10:00";
            log.info("首次执行:{}", firstDate);
            return sim.parse(firstDate);
        } else {
            String addMonth = DateUtils.getAddMonth(1);
            String firstDate = addMonth.substring(0, 8) + "01 07:10:00";
            log.info("非首次执行:{}", firstDate);
            return sim.parse(firstDate);
        }

    }

    /**
     * @return
     * @throws ParseException
     */
    private Date getDayDate() throws ParseException {
        Date nextDate = DateUtils.getNextDate(1);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstDate = sim.format(nextDate);
        firstDate = firstDate.substring(0, 10) + " 07:00:00";
        //TODO 巡检测试
        //Date afterDate = new Date( + 300000);
        //firstDate=sim.format(afterDate);

        return sim.parse(firstDate);
    }
}
