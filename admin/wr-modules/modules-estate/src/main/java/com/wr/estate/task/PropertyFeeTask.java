package com.wr.estate.task;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.estate.service.financial.PropertyFeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/26 16:51
 */
@Slf4j
@Component
public class PropertyFeeTask {

    @Autowired
    private PropertyFeeService propertyFeeService;

    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void deletePropertyFee() {
        log.info("=======================START执行清空物业缴费配置楼栋=======================");
        List<String> feeIdList = propertyFeeService.getEntTimeFeeIdList();
        if (CollectionUtils.isNotEmpty(feeIdList)) {
            for (String id : feeIdList) {
                propertyFeeService.clearByPropertyFeeIdTime(id);
                propertyFeeService.deleteByPropertyFeeBuilding(id);
            }
        }
        log.info("=======================START执行清空物业缴费配置楼栋=======================");
    }

}
