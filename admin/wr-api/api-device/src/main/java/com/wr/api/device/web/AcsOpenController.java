package com.wr.api.device.web;

import com.wr.api.device.service.AcsOpenService;
import com.wr.common.core.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: RainCity
 * @Date: 2021-12-29 15:31:06
 * @Desc: 门禁事件-->合法卡比对事件
 */
@RestController
@RequestMapping("/acsOpen")
public class AcsOpenController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AcsOpenController.class);

    @Autowired
    private AcsOpenService acsDeviceService;

    /**
     * 合法卡比对通过 198914
     * @param req 请求数据
     */
    @PostMapping
    public void acsDeviceOpen(@RequestBody String req){
        log.info("*************合法卡比对通过开门事件********");
        log.info("req:{}", req);
        acsDeviceService.acsDeviceOpen(req);
    }
}
