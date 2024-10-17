//package com.wr.estate.expired;
//
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import com.wr.common.core.constant.CacheConstants;
//import com.wr.common.core.utils.DateUtils;
//import com.wr.common.redis.service.RedisService;
//import com.wr.estate.service.customer.cleaningWorkOrder.CleaningWorkOrderService;
//import com.wr.estate.service.customer.cleaningWorkOrder.CleaningWorkOrderSettingService;
//import com.wr.remote.domain.CleaningWorkOrder;
//import com.wr.remote.domain.CleaningWorkOrderSetting;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
///**
// * @ClassName InspectionSettingExpired
// * @Author lvzy
// * @Date 2023/6/10 0010 18:25
// * 清洁工单redis到期执行
// **/
//
//@Component
//@Slf4j
//public class CleaningWorkOrderSettingExpired extends KeyExpirationEventMessageListener {
//
//
//    @Autowired
//    private CleaningWorkOrderSettingService cleaningWorkOrderSettingService;
//
//    @Autowired
//    private CleaningWorkOrderService cleaningWorkOrderService;
//
//    @Autowired
//    private RedisService redisService;
//
//
//    public CleaningWorkOrderSettingExpired(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    /**
//     * 过期redisKey
//     *
//     * @param message Key
//     * @param pattern 不知道是个什么东东 父类也没有使用这个参数
//     */
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        super.onMessage(message, pattern);
//        String redisKey = message.toString();
//        String[] redisKeys = redisKey.split(":");
//        if (redisKey.contains(CacheConstants.QJ_WORK_ORDER)) {
//            String inspectionSettingId = redisKeys[3];
//            CleaningWorkOrderSetting cleaningWorkOrderSetting = cleaningWorkOrderSettingService.getById(inspectionSettingId);
//            try {
//                Integer executionNumber = cleaningWorkOrderSetting.getExecutionNumber();
//                Integer endExecutionNumber = cleaningWorkOrderSetting.getEndExecutionNumber();
//
//                if (executionNumber == endExecutionNumber) {
//                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date nextDate = DateUtils.getNextDate(1);
//                    String format = sim.format(nextDate);
//                    String firstExecutionTime = format.substring(0, 10) + " 07:00:00";
//                    cleaningWorkOrderSetting.setFirstExecutionTime(sim.parse(firstExecutionTime));
//                    cleaningWorkOrderSetting.setEndExecutionNumber(0);
//                } else {
//                    cleaningWorkOrderSetting.setEndExecutionNumber(endExecutionNumber + 1);
//                }
//                //向redis中更新结果
//                cleaningWorkOrderSettingService.saveRedisData(cleaningWorkOrderSetting, "0");
//                String[] executorBys = cleaningWorkOrderSetting.getExecutorBy().split(",");
//                for (String executorBy : executorBys) {
//                    CleaningWorkOrder cleaningWorkOrder = new CleaningWorkOrder();
//                    cleaningWorkOrder.setEstateId(cleaningWorkOrderSetting.getEstateId());
//                    cleaningWorkOrder.setCommunityId(cleaningWorkOrderSetting.getCommunityId());
//                    cleaningWorkOrder.setNo("QJ" + IdWorker.getIdStr());
//                    cleaningWorkOrder.setName(executorBy);
//                    cleaningWorkOrder.setCleaningName(cleaningWorkOrderSetting.getCleaningName());
//                    cleaningWorkOrder.setOrderExpirationTime(cleaningWorkOrderSetting.getOrderExpirationTime());
//                    cleaningWorkOrder.setCleaningStatus("0");
//                    cleaningWorkOrder.setCreateTime(new Date());
//                    cleaningWorkOrder.setDelFlag("0");
//                    cleaningWorkOrderService.save(cleaningWorkOrder);
//
//                    //设置工单到期时间
//                    Long minute = DateUtils.getMinute(new Date(), cleaningWorkOrder.getOrderExpirationTime());
//                    String redisInspectionKey = CacheConstants.QJ_WORK_ORDER_EXP + cleaningWorkOrder.getId();
//                    log.info("设置清洁工单到期时间:{},{}", cleaningWorkOrder.getOrderExpirationTime(), minute);
//                    redisService.setCacheObject(redisInspectionKey, cleaningWorkOrder, minute, TimeUnit.MINUTES);
//                }
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        } else if (redisKey.contains(CacheConstants.QJ_WORK_ORDER_EXP)) {
//            //工单到期
//            String inspectionId = redisKeys[1];
//            CleaningWorkOrder cleaningWorkOrder = cleaningWorkOrderService.getById(inspectionId);
//            if (cleaningWorkOrder.getCleaningStatus().equals("0")) {
//                cleaningWorkOrder.setCleaningStatus("4");
//                cleaningWorkOrderService.updateById(cleaningWorkOrder);
//            }
//
//        }
//    }
//}
