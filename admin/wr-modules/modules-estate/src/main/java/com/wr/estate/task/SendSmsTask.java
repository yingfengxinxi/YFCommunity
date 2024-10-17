package com.wr.estate.task;

import cn.hutool.json.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.IdcardValidatorUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.sms.SendMsgRequest;
import com.wr.common.core.utils.sms.SendSmsUtils;
import com.wr.estate.service.manage.contract.OwnerService;
import com.wr.estate.service.send.SendSmsRecordService;
import com.wr.estate.service.send.SendSmsTaskService;
import com.wr.remote.domain.SendSmsRecord;
import com.wr.remote.estate.manage.vo.contract.SendOwnerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/1 11:01
 */
@Slf4j
@Component
public class SendSmsTask {

    @Autowired
    private SendSmsTaskService sendSmsTaskService;

    /**
     * 发送业主生日短信
     */
    @Scheduled(cron = "0 0 7 * * ?")
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void sendBirthdayMessages() {
        try {
            log.info("=======================START执行发送生日祝福短信定时任务=======================");
            sendSmsTaskService.sendBirthdayMessages();
            log.info("=======================END执行发送生日祝福短信定时任务=======================");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                log.info("=======================START执行发送节日祝福短信定时任务=======================");
                sendSmsTaskService.sendFestivalMessages();
                log.info("=======================END执行发送节日祝福短信定时任务=======================");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
