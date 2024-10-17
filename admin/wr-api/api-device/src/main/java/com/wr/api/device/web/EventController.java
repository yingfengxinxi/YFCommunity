package com.wr.api.device.web;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.api.device.service.EventService;
import com.wr.common.core.exception.base.BaseException;
import com.wr.common.core.web.controller.BaseController;
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
 * @Date: 2022-02-11 11:07:28
 * @Desc: 预警联动事件订阅
 */
@RestController
@RequestMapping("/event")
public class EventController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private List<EventService> eventServices;

    /**
     * 事件
     * @param req
     * @return
     */
    @PostMapping
    public void linkageEvent(@RequestBody String req){
        log.info("**********联动事件执行********");
        log.info("req:{}", req);
        JSONObject reqJo = JsonUtil.parseObject(req);
        if(null == reqJo){
            log.warn("联动事件统一报文为空，不记录数据");
            return;
        }
        //事件消息
        JSONObject params = reqJo.getJSONObject("params");
        if(null == params){
            log.warn("事件参数信息为空，不记录数据");
            return;
        }
        String sendTime = params.getString("sendTime");
        JSONArray events = params.getJSONArray("events");
        if(events.isEmpty()){
            log.warn("事件消息为空，不记录数据");
            return;
        }

        for(int i=0;i<events.size();i++){
            JSONArray details = events.getJSONObject(i).getJSONArray("eventDetails");
            for (int j = 0; j < details.size(); j++) {
                int eventType = details.getJSONObject(j).getIntValue("eventType");
                EventService eventService = eventServices.stream()
                        .filter(service -> service.support(eventType))
                        .findAny()
                        .orElseThrow(() -> new BaseException("不记录的事件："+eventType));
                eventService.execute(sendTime, events.getJSONObject(i), details.getJSONObject(j));
            }
        }
    }
}
