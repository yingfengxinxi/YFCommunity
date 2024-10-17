package com.wr.api.device.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.device.service.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: RainCity
 * @Date: 2021-10-13 15:48:40
 * @Desc: 社保卡电子卡业务逻辑
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class CloudElcCardServiceImpl implements CloudService {
    private static final Logger log = LoggerFactory.getLogger(CloudElcCardServiceImpl.class);

    @Override
    public Boolean support(String card) {
        return false;
    }

    @Override
    public String execute(JSONObject cloudData) {
        log.info("********** 电子社保卡进门==> *************");
        return null;
    }
}
