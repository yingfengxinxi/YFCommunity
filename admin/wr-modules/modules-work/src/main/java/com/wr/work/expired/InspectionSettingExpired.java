package com.wr.work.expired;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.Inspection;
import com.wr.remote.domain.InspectionSetting;
import com.wr.work.service.inspection.InspectionService;
import com.wr.work.service.inspection.InspectionSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName InspectionSettingExpired
 * @Author lvzy
 * @Date 2023/6/10 0010 18:25
 * 巡查工单redis到期执行
 **/

@Component
@Slf4j
public class InspectionSettingExpired extends KeyExpirationEventMessageListener {


    @Autowired
    private InspectionSettingService inspectionSettingService;

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private RedisService redisService;


    public InspectionSettingExpired(RedisMessageListenerContainer listenerContainer) {
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
        if (redisKey.contains(CacheConstants.XC_WORK_ORDER)) {
            log.info("START生成巡查工单....");
            String inspectionSettingId = redisKeys[3];
            InspectionSetting inspectionSetting = inspectionSettingService.getById(inspectionSettingId);
            try {
                Integer executionNumber = inspectionSetting.getExecutionNumber();
                Integer endExecutionNumber = inspectionSetting.getEndExecutionNumber();

                if (executionNumber == endExecutionNumber) {
                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date nextDate = DateUtils.getNextDate(1);
                    String format = sim.format(nextDate);
                    String firstExecutionTime = format.substring(0, 10) + " 07:00:00";
                    inspectionSetting.setFirstExecutionTime(sim.parse(firstExecutionTime));
                    inspectionSetting.setEndExecutionNumber(0);
                } else {
                    inspectionSetting.setEndExecutionNumber(endExecutionNumber + 1);
                }

                //向redis中更新结果
                inspectionSettingService.saveRedisData(inspectionSetting, "0");
                String[] executorBys = inspectionSetting.getExecutorBy().split(",");
                for (String executorBy : executorBys) {
                    Inspection inspection = new Inspection();
                    inspection.setEstateId(inspectionSetting.getEstateId());
                    inspection.setCommunityId(inspectionSetting.getCommunityId());
                    inspection.setNo("XC" + IdWorker.getIdStr());
                    inspection.setName(executorBy);
                    inspection.setInspectionName(inspectionSetting.getInspectName());
                    inspection.setInspectionAddress(inspectionSetting.getInspectionAddress());
                    inspection.setOrderExpirationTime(inspectionSetting.getOrderExpirationTime());
                    inspection.setInspectionStatus("0");
                    inspection.setCreateTime(new Date());
                    inspection.setDelFlag("0");
                    inspectionService.save(inspection);

                    //设置工单到期时间
                    Long minute = DateUtils.getMinute(new Date(), inspection.getOrderExpirationTime());
                    String redisInspectionKey = CacheConstants.XC_WORK_ORDER_EXP + inspection.getId();
                    log.info("设置巡查工单到期时间:{},{}", inspection.getOrderExpirationTime(), minute);
                    redisService.setCacheObject(redisInspectionKey, inspection, minute, TimeUnit.MINUTES);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            log.info("END生成巡查工单....");
        } else if (redisKey.contains(CacheConstants.XC_WORK_ORDER_EXP)) {
            //工单到期
            log.info("START巡查工单到期....");
            String inspectionId = redisKeys[1];
            Inspection inspection = inspectionService.getById(inspectionId);
            if(inspection.getInspectionStatus().equals("0")){
                inspection.setInspectionStatus("4");
                inspectionService.updateById(inspection);
            }
            log.info("END巡查工单到期....");

        }
    }
}
