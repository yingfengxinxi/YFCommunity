package com.wr.api.device.web;

import com.wr.api.device.service.AccessService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.Identify;
import com.wr.common.customize.util.IdentifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-07-19 14:53:30
 * @Desc: 门禁人脸/安康码识别
 */
@RestController
@RequestMapping("/access")
public class AccessController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AccessController.class);

    @Autowired
    private List<AccessService> accessServices;

    @PostMapping("/health_code")
    public AjaxResult healthCode(@RequestBody Identify identify){
        log.info("********** healthCode *************");
        log.info("identify:{}",identify);

        //温度异常
        if(1==identify.getTemptureAbnormal()){
            log.info("********** 温度异常==> *************");
            return IdentifyUtils.abnormalTemperature(identify);
        }

        AccessService accessService = accessServices.stream()
                .filter(service -> service.support(identify.getAuthType(),identify.getAuthMsg()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("识别模式不支持"));
        return accessService.execute(identify);
    }

    @PostMapping("/heartbeat")
    public void heartbeat(String deviceId) {
        log.info("**********heartbeat*************");
        log.info("deviceId:{}", deviceId);
    }
}
