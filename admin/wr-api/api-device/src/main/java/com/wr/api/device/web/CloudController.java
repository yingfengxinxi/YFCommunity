package com.wr.api.device.web;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.device.service.CloudService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.customize.constant.CloudConstants;
import com.wr.common.customize.util.JsonUtil;
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
 * @Date: 2021-09-23 19:35:15
 * @Desc: 云+控制器数据记录
 */
@RestController
@RequestMapping("/cloud")
public class CloudController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CloudController.class);
    @Autowired
    private List<CloudService> cloudServices;

    @PostMapping("/cardEvent")
    public String cardEvent(@RequestBody String req) {
        log.info("**********cardEvent*************");
        log.info("req:{}", req);
        JSONObject cloudData = JsonUtil.parseObject(req);
        if(null == cloudData){
            return String.format(CloudConstants.RESPONSE_FAILED, "社保卡识别错误");
        }
        CloudService cloudService = cloudServices.stream()
                .filter(service -> service.support(cloudData.getString("Card")))
                .findAny()
                .orElseThrow(() -> new RuntimeException("模式不支持"));
       return cloudService.execute(cloudData);
    }

    @PostMapping("/cardHeart")
    public String cardHeart(@RequestBody String req) {
        log.info("**********cardHeart*************");
        log.info("req:{}", req);
        return String.format(CloudConstants.RESPONSE_SUCCESS, "欢迎光临");
    }
}
