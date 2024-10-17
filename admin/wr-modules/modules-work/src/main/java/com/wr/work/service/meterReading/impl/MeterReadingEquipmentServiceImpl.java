package com.wr.work.service.meterReading.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.MeterReadingEquipment;
import com.wr.remote.domain.MeterReadingWorkOrder;
import com.wr.work.mapper.meterReading.MeterReadingEquipmentMapper;
import com.wr.work.mapper.meterReading.MeterReadingWorkOrderMapper;
import com.wr.work.service.meterReading.MeterReadingEquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author lvzy
 * @Date 2024/3/4 11:25
 */
@Slf4j
@Service
public class MeterReadingEquipmentServiceImpl extends ServiceImpl<MeterReadingEquipmentMapper, MeterReadingEquipment> implements MeterReadingEquipmentService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private MeterReadingWorkOrderMapper meterReadingWorkOrderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRedisData(MeterReadingEquipment meterReadingEquipment, String flag) throws ParseException {
        String redisKey = CacheConstants.CB_WORK_ORDER + meterReadingEquipment.getEstateId() + ":" + meterReadingEquipment.getCommunityId() + ":" + meterReadingEquipment.getEnergyConsumptionType() + "-" + meterReadingEquipment.getMeterReadingType();
        MeterReadingEquipment mde = redisService.getCacheObject(redisKey);
        if (mde != null) {
            redisService.deleteObject(redisKey);
        }
        if (flag.equals("0")) {
            Long minute = 0L;
            Date firstExecutionTime = getMoneyDate(meterReadingEquipment);

            log.info("当前时间:{}", new Date());
            log.info("未来时间:{}", firstExecutionTime);
            meterReadingEquipment.setOrderExpirationTime(firstExecutionTime);
            //计算到期生成工单时间
            minute = DateUtils.getMinute(new Date(), firstExecutionTime);
            log.info("生成工单时间:{}", minute);

            //存入下次工单执行时间
            log.info("START存入下次抄表工单执行时间:{},{},{},{}", redisKey, meterReadingEquipment.toString(), minute, TimeUnit.MINUTES);
            redisService.setCacheObject(redisKey, meterReadingEquipment, minute, TimeUnit.MINUTES);
            LambdaQueryWrapper<MeterReadingEquipment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(MeterReadingEquipment::getCommunityId, meterReadingEquipment.getCommunityId());
            queryWrapper1.eq(MeterReadingEquipment::getEnergyConsumptionType, meterReadingEquipment.getEnergyConsumptionType());
            queryWrapper1.eq(MeterReadingEquipment::getMeterReadingType, meterReadingEquipment.getMeterReadingType());
            List<MeterReadingEquipment> meterReadingEquipmentList = baseMapper.selectList(queryWrapper1);
            if (CollectionUtils.isNotEmpty(meterReadingEquipmentList)) {
                meterReadingEquipmentList.stream().forEach(mre -> mre.setOrderExpirationTime(meterReadingEquipment.getOrderExpirationTime()));
            }
            super.updateBatchById(meterReadingEquipmentList);
            log.info("END存入下次抄表工单执行时间:{}", meterReadingEquipment.toString());
        }
    }

    /**
     * @return
     * @throws ParseException
     */
    private Date getMoneyDate(MeterReadingEquipment meterReadingEquipment) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //统计是否第一次执行
        LambdaQueryWrapper<MeterReadingWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeterReadingWorkOrder::getEnergyConsumptionType, meterReadingEquipment.getEnergyConsumptionType());
        queryWrapper.eq(MeterReadingWorkOrder::getMeterReadingType, meterReadingEquipment.getMeterReadingType());
        queryWrapper.eq(MeterReadingWorkOrder::getCommunityId, meterReadingEquipment.getCommunityId());
        int size = meterReadingWorkOrderMapper.selectList(queryWrapper).size();
        if (size <= 0) {
            //首次执行
            Date nextDate = DateUtils.getNextDate(1);
            String firstDate = sim.format(nextDate);
            firstDate = firstDate.substring(0, 10) + " 06:10:00";
            //TODO 抄表测试
//            Date afterDate = new Date(new Date().getTime() + 300000);
//            firstDate = sim.format(afterDate);
            log.info("首次执行:{}", firstDate);
            return sim.parse(firstDate);
        } else {
            String addMonth = DateUtils.getAddMonth(1);
            String firstDate = addMonth.substring(0, 8) + "01 06:10:00";
            log.info("非首次执行:{}", firstDate);
            return sim.parse(firstDate);
        }

    }
}
