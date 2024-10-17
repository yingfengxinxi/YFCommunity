package com.wr.api.device.web;

import com.wr.api.device.service.BarrierService;
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
 * @Date: 2021-10-25 15:48:00
 * @Desc: 车场事件
 */
@RestController
@RequestMapping("/barrier")
public class BarrierController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BarrierController.class);

    @Autowired
    private BarrierService barrierService;

    /**
     * 入场压线事件
     * @param req
     * @return
     */
    @PostMapping("/in_crimping")
    public void inCrimp(@RequestBody String req){
        log.info("***************入场压线事件********");
        log.info("req:{}", req);
        barrierService.entryPressureLine(req);
    }

    /**
     * 出场压线事件
     * @param req
     * @return
     */
    @PostMapping("/out_crimping")
    public void outCrimp(@RequestBody String req){
        log.info("***************出场压线事件********");
        log.info("req:{}", req);
        barrierService.outPressureLine(req);
    }


}
