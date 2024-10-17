package com.wr.aiot.callback.service;
import com.alibaba.fastjson2.JSONObject;

/**
 * @Author: RainCity
 * @Date: 2022-02-11 11:20:08
 * @Desc: 预警事件业务层
 */
public interface EventService {

    /**
     * 事件类型验证
     * @param eventType 事件类型
     * @return java.lang.Boolean
     */
    Boolean support(Integer eventType);

    /**
     * 执行
     * @param params 事件从接收者（程序处理后）发出的时间
     * @param event 事件信息
     * @param data 事件详情
     */
    void execute(JSONObject params,JSONObject event, JSONObject data);
}
