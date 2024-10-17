//package com.wr.estate.expired;
//
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import com.wr.common.core.constant.CacheConstants;
//import com.wr.common.core.utils.DateUtils;
//import com.wr.common.redis.service.RedisService;
//import com.wr.estate.service.work.patrol.PatrolService;
//import com.wr.estate.service.work.patrol.PatrolSettingService;
//import com.wr.remote.domain.Patrol;
//import com.wr.remote.domain.PatrolSetting;
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
// * @Author lvzy
// * @Date 2024/4/17 14:27
// * 巡更工单
// */
//@Component
//@Slf4j
//public class PatrolWorkSettingExpired extends KeyExpirationEventMessageListener {
//
//
//    @Autowired
//    private PatrolSettingService patrolSettingService;
//
//    @Autowired
//    private PatrolService patrolService;
//
//    @Autowired
//    private RedisService redisService;
//
//
//    public PatrolWorkSettingExpired(RedisMessageListenerContainer listenerContainer) {
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
//        if (redisKey.contains(CacheConstants.XG_WORK_ORDER)) {
//            String patrolSettingId = redisKeys[3];
//            PatrolSetting patrolSetting = patrolSettingService.getById(patrolSettingId);
//            try {
//                Integer executionNumber = patrolSetting.getExecutionNumber();
//                Integer endExecutionNumber = patrolSetting.getEndExecutionNumber();
//
//                if (executionNumber == endExecutionNumber) {
//                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date nextDate = DateUtils.getNextDate(1);
//                    String format = sim.format(nextDate);
//                    String firstExecutionTime = format.substring(0, 10) + " 07:00:00";
//                    patrolSetting.setFirstExecutionTime(sim.parse(firstExecutionTime));
//                    patrolSetting.setEndExecutionNumber(0);
//                } else {
//                    patrolSetting.setEndExecutionNumber(endExecutionNumber + 1);
//                }
//
//                //向redis中更新结果
//                patrolSettingService.saveRedisData(patrolSetting, "0");
//                String[] executorBys = patrolSetting.getExecutorBy().split(",");
//                for (String executorBy : executorBys) {
//                    Patrol patrol = new Patrol();
//                    patrol.setEstateId(patrolSetting.getEstateId());
//                    patrol.setCommunityId(patrolSetting.getCommunityId());
//                    patrol.setNo("XG" + IdWorker.getIdStr());
//                    patrol.setName(executorBy);
//                    patrol.setPatrolName(patrolSetting.getPatrolName());
//                    patrol.setOrderExpirationTime(patrolSetting.getOrderExpirationTime());
//                    patrol.setPatrolStatus("0");
//                    patrol.setCreateTime(new Date());
//                    patrol.setDelFlag("0");
//                    patrolService.save(patrol);
//
//                    //设置工单到期时间
//                    Long minute = DateUtils.getMinute(new Date(), patrol.getOrderExpirationTime());
//                    String redisPatrolKey = CacheConstants.XG_WORK_ORDER_EXP + patrol.getId();
//                    log.info("设置巡更工单到期时间:{},{}", patrol.getOrderExpirationTime(), minute);
//                    redisService.setCacheObject(redisPatrolKey, patrol, minute, TimeUnit.MINUTES);
//                }
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        } else if (redisKey.contains(CacheConstants.XG_WORK_ORDER_EXP)) {
//            //工单到期
//            String inspectionId = redisKeys[1];
//            Patrol patrol = patrolService.getById(inspectionId);
//            if (patrol.getPatrolStatus().equals("0")) {
//                patrol.setPatrolStatus("4");
//                patrolService.updateById(patrol);
//            }
//
//
//        }
//    }
//}