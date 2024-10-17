package com.wr.work.expired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.FacilitieEquipment;
import com.wr.remote.domain.PatrolWorkOrder;
import com.wr.remote.domain.PatrolWorkOrderSetting;
import com.wr.remote.domain.SysDictData;
import com.wr.work.service.patrol.FacilitieEquipmentService;
import com.wr.work.service.patrol.PatrolWorkOrderService;
import com.wr.work.service.patrol.PatrolWorkOrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName PatrolSettingExpired
 * @Author lvzy
 * @Date 2023/6/10 0010 18:25
 * 巡检工单redis到期执行
 **/

@Component
@Slf4j
public class PatrolSettingExpired extends KeyExpirationEventMessageListener {


    @Autowired
    private PatrolWorkOrderSettingService patrolWorkOrderSettingService;

    @Autowired
    private FacilitieEquipmentService facilitieEquipmentService;

    @Autowired
    private PatrolWorkOrderService patrolWorkOrderService;

    @Autowired
    private RedisService redisService;


    public PatrolSettingExpired(RedisMessageListenerContainer listenerContainer) {
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
        if (redisKey.contains(CacheConstants.XJ_WORK_ORDER)) {
            log.info("START生成巡检工单....");
            String patrolSettingId = redisKeys[3];
            PatrolWorkOrderSetting patrolWorkOrderSetting = patrolWorkOrderSettingService.getById(patrolSettingId);
            try {
                if (patrolWorkOrderSetting.getFrequency().equals("1")) {
                    patrolWorkOrderSetting.setIsOrder("1");
                }
                //向redis中更新结果
                patrolWorkOrderSettingService.saveRedisData(patrolWorkOrderSetting, "0");
                List<SysDictData> dictCache = DictUtils.getDictCache("patrol_content");
                LambdaQueryWrapper<FacilitieEquipment> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(FacilitieEquipment::getCommunityId, patrolWorkOrderSetting.getCommunityId());
                queryWrapper.eq(FacilitieEquipment::getFacilitieEquipmentType, patrolWorkOrderSetting.getFacilitieEquipmentType());
                List<FacilitieEquipment> equipmentList = facilitieEquipmentService.list(queryWrapper);
                log.info("查询到" + patrolWorkOrderSetting.getFacilitieEquipmentType() + "类型设备数量:{}", equipmentList.size());
                if (CollectionUtils.isNotEmpty(equipmentList)) {
                    for (FacilitieEquipment facilitieEquipment : equipmentList) {
                        log.info("START添加巡检" + facilitieEquipment.getFacilitieEquipmentType() + "类型工单");
                        PatrolWorkOrder patrolWorkOrder = new PatrolWorkOrder();
                        List<SysDictData> dictDataList = dictCache.stream().filter(sysDictData -> sysDictData.getDictValue().equals(patrolWorkOrderSetting.getFacilitieEquipmentType())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(dictDataList)) {
                            String remark = dictDataList.get(0).getRemark();
                            patrolWorkOrder.setPatrolContent(remark);
                        }
                        patrolWorkOrder.setEstateId(patrolWorkOrderSetting.getEstateId());
                        patrolWorkOrder.setCommunityId(patrolWorkOrderSetting.getCommunityId());
                        patrolWorkOrder.setFacilitieEquipmentId(facilitieEquipment.getId());
                        patrolWorkOrder.setNo("XJ" + IdWorker.getIdStr());
                        patrolWorkOrder.setFacilitieEquipmentType(facilitieEquipment.getFacilitieEquipmentType());
                        patrolWorkOrder.setPatrolTime(new Date());
                        //计算工单到期时间
                        patrolWorkOrder.setOrderExpirationTime(patrolWorkOrderSetting.getOrderExpirationTime());
                        patrolWorkOrder.setPatrolStatus("0");
                        patrolWorkOrder.setCreateTime(new Date());
                        patrolWorkOrder.setDelFlag("0");
                        patrolWorkOrderService.save(patrolWorkOrder);
                        log.info("END添加巡检" + patrolWorkOrder.getFacilitieEquipmentType() + "类型工单");
                        //设置工单到期时间
                        Long minute = DateUtils.getMinute(new Date(), patrolWorkOrder.getOrderExpirationTime());
                        String redisInspectionKey = CacheConstants.XJ_WORK_ORDER_EXP + patrolWorkOrder.getId();
                        log.info("设置巡检工单到期时间:{},{}", patrolWorkOrder.getOrderExpirationTime(), minute);
                        redisService.setCacheObject(redisInspectionKey, patrolWorkOrderSetting, minute, TimeUnit.MINUTES);
                    }

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("END生成巡检工单....");
        } else if (redisKey.contains(CacheConstants.XJ_WORK_ORDER_EXP)) {
            //工单到期
            log.info("START巡检工单到期....");
            String inspectionId = redisKeys[1];
            PatrolWorkOrder patrolWorkOrder = patrolWorkOrderService.getById(inspectionId);
            if (patrolWorkOrder.getPatrolStatus().equals("0")) {
                patrolWorkOrder.setPatrolStatus("2");
                patrolWorkOrderService.updateById(patrolWorkOrder);
            }
            log.info("END巡检工单到期....");
        }
    }
}