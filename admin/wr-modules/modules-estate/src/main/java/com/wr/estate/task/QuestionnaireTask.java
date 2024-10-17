package com.wr.estate.task;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.estate.entity.BusQuestionnaire;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:59
 */
@Component
@Slf4j
public class QuestionnaireTask {

    @Autowired
    private BusQuestionnaireService busQuestionnaireService;


    /**
     * 每天凌晨执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void updateStatus() {
        log.info("=======================START执行调查问卷状态修改定时任务=======================");
        LambdaQueryWrapper<BusQuestionnaire> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("del_flag='0' AND status != '3' AND DATE_FORMAT( deadline, '%Y-%m-%d' )= DATE_FORMAT( NOW(), '%Y-%m-%d' )");
        List<BusQuestionnaire> list = busQuestionnaireService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(busQuestionnaire -> {
                busQuestionnaire.setStatus("3");
                busQuestionnaireService.updateById(busQuestionnaire);
            });
        }
        log.info("=======================END执行调查问卷状态修改定时任务=======================");
    }
}
