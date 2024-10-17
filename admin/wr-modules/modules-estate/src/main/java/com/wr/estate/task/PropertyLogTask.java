package com.wr.estate.task;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.estate.service.financial.PropertyFeeService;
import com.wr.estate.service.financial.PropertyLogService;
import com.wr.remote.domain.PropertyFeeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/11 13:15
 */
@Slf4j
@Component
public class PropertyLogTask {


    @Autowired
    private PropertyFeeService propertyFeeService;


    /**
     * 1、查询正在使用和未到期的缴费规则和缴费范围
     * 2、根据缴费范围查询房号
     * 3、根据查询到的房号查询账单表中是否存在数据
     * 4、存在数据
     * 4.1、判断结束时间是否等于当天时间
     * 4.1.1成立生成账单不成立不生成账单
     * 5、不存在数据
     * 5.1 生成第一次账单
     */
    @Scheduled(cron = "0 0 3 * * ?")
    //@Scheduled(cron = "0/5 * * * * *")
    public void generatePropertyBill() {
        log.info("=======================START执行生成物业缴费账单定时任务=======================");
        propertyFeeService.generatePropertyBillTask();
        log.info("=======================END执行生成物业缴费账单定时任务=======================");
    }
}
