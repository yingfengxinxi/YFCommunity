package com.wr.api.owner.web.task;

import com.wr.api.owner.entity.dto.FitmentDTO;
import com.wr.api.owner.entity.vo.serve.FitmentVo;
import com.wr.api.owner.mapper.serve.FitmentMapper;
import com.wr.api.owner.service.serve.FitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-12-17 11:27:53
 * @Desc: 装修定时任务
 */
@Configuration
@EnableScheduling
public class DecorateTask {
    @Autowired
    private FitmentMapper fitmentMapper;

    //每天晚上十二点执行
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(readOnly = false)
    public void getDecorateStatus(){
        //查询装修得结束时间
       List<FitmentDTO> fitmentVos = fitmentMapper.getEndTime();
       if (fitmentVos!=null && fitmentVos.size()>0){
           for (FitmentDTO fitmentVo : fitmentVos) {
               if (new Date().compareTo(fitmentVo.getPlanEnd()) >= 0) {
                   //如果当前时间大于结束使劲,修改装修状态为已完成
                   fitmentMapper.updateEndStatus(fitmentVo.getDecorateId());
               }
           }
       }

    }
}
