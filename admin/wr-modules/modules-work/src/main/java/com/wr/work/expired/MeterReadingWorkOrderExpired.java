package com.wr.work.expired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.MeterReadingEquipment;
import com.wr.remote.domain.MeterReadingWorkOrder;
import com.wr.work.service.meterReading.MeterReadingEquipmentService;
import com.wr.work.service.meterReading.MeterReadingWorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author lvzy
 * @Date 2024/3/5 12:37
 * 抄表工单redis到期执行
 */
@Component
@Slf4j
public class MeterReadingWorkOrderExpired extends KeyExpirationEventMessageListener {


    @Autowired
    private MeterReadingEquipmentService meterReadingEquipmentService;

    @Autowired
    private MeterReadingWorkOrderService meterReadingWorkOrderService;

    @Autowired
    private RedisService redisService;


    public MeterReadingWorkOrderExpired(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 过期redisKey
     *
     * @param message Key
     * @param pattern 不知道是个什么东东 父类也没有使用这个参数
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        super.onMessage(message, pattern);
        String redisKey = message.toString();
        String[] redisKeys = redisKey.split(":");
        if (redisKey.contains(CacheConstants.CB_WORK_ORDER)) {
            log.info("START生成抄表工单....");
            //物业id
            String estateId = redisKeys[1];
            //小区id
            String communityId = redisKeys[2];
            //能耗类型-类型
            String type = redisKeys[3];
            //能耗类型
            String energyConsumptionType = type.split("-")[0];
            //类型
            String meterReadingType = type.split("-")[1];

            LambdaQueryWrapper<MeterReadingEquipment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MeterReadingEquipment::getEstateId, estateId);
            queryWrapper.eq(MeterReadingEquipment::getCommunityId, communityId);
            queryWrapper.eq(MeterReadingEquipment::getEnergyConsumptionType, energyConsumptionType);
            queryWrapper.eq(MeterReadingEquipment::getMeterReadingType, meterReadingType);
            queryWrapper.eq(MeterReadingEquipment::getStatus, "1");
            List<MeterReadingEquipment> meterReadingEquipmentList = meterReadingEquipmentService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(meterReadingEquipmentList)) {
                try {
                    MeterReadingEquipment meterReadingEquipment = meterReadingEquipmentList.get(0);
                    //向redis中更新结果
                    meterReadingEquipmentService.saveRedisData(meterReadingEquipment, "0");
                    MeterReadingWorkOrder meterReadingWorkOrder = new MeterReadingWorkOrder();

                    meterReadingWorkOrder.setEstateId(meterReadingEquipment.getEstateId());
                    meterReadingWorkOrder.setCommunityId(meterReadingEquipment.getCommunityId());
                    meterReadingWorkOrder.setNo("CB" + IdWorker.getIdStr());
                    meterReadingWorkOrder.setOrderExpirationTime(meterReadingEquipment.getOrderExpirationTime());
                    meterReadingWorkOrder.setEnergyConsumptionType(meterReadingEquipment.getEnergyConsumptionType());
                    meterReadingWorkOrder.setMeterReadingType(meterReadingEquipment.getMeterReadingType());
                    meterReadingWorkOrder.setMeterReadingStatus("0");
                    String lessMonth = DateUtils.getLessMonth(1);
                    String meterReadingTime = lessMonth.substring(0, 7);
                    meterReadingWorkOrder.setMeterReadingTime(meterReadingTime);
                    meterReadingWorkOrder.setCreateTime(new Date());
                    meterReadingWorkOrder.setDelFlag("0");
                    meterReadingWorkOrderService.save(meterReadingWorkOrder);
                    //设置工单到期时间
                    Long minute = DateUtils.getMinute(new Date(), meterReadingWorkOrder.getOrderExpirationTime());
                    String redisInspectionKey = CacheConstants.CB_WORK_ORDER_EXP + meterReadingWorkOrder.getId();
                    log.info("设置抄表工单到期时间:{},{}", meterReadingWorkOrder.getOrderExpirationTime(), minute);
                    redisService.setCacheObject(redisInspectionKey, meterReadingWorkOrder, minute, TimeUnit.MINUTES);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("END生成抄表工单....");
        } else if (redisKey.contains(CacheConstants.CB_WORK_ORDER_EXP)) {
            log.info("START抄表工单到期....");
            //工单到期
            String inspectionId = redisKeys[1];
            MeterReadingWorkOrder meterReadingWorkOrder = meterReadingWorkOrderService.getById(inspectionId);
            if (meterReadingWorkOrder.getMeterReadingStatus().equals("0")) {
                meterReadingWorkOrder.setMeterReadingStatus("2");
                meterReadingWorkOrderService.updateById(meterReadingWorkOrder);
            }
            log.info("END抄表工单到期....");
        }
    }
}
