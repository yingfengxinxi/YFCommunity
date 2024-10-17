package com.wr.aiot.task;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.aiot.service.*;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.constant.ProtocolConstants;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.*;
import com.wr.remote.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author lvzy
 * @Date 2024/3/15 16:43
 */
@Component
@Slf4j
public class HaiKTask {

    @Autowired
    private DoorEventService doorEventService;

    @Autowired
    private VisitingRecordService visitingRecordService;

    @Autowired
    private CrossRecordService crossRecordService;

    @Autowired
    private VisualSecordService visualSecordService;


    /**
     * 查询出进小区数据
     *
     * @throws Exception
     */
    //@Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "0 0/30 * * * ?")
    public void doorEvents() {

        try {
            log.info("=========================START执行门禁点事件接口=========================");
            doorEventService.doorEvents("1", "", "");
            log.info("=========================END执行门禁点事件接口=========================");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //查询访客记录
            try {
                log.info("=========================START执行查询来访记录接口=========================");
                visitingRecordService.visitingRecord("1");
                log.info("=========================END执行查询来访记录接口=========================");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    log.info("=========================START执行查询车辆出行记录接口=========================");
                     crossRecordService.crossRecord("1");
                    log.info("=========================END执行查询车辆出行记录接口=========================");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        log.info("=========================START执行查询单元楼人员出行记录接口=========================");
                          visualSecordService.visualSecord("1", "", "");
                        log.info("=========================END执行查询单元楼人员出行记录接口=========================");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        CommunityHik communityHik = new CommunityHik();
        communityHik.setHikIp(HikConstants.HIK_IP);
        communityHik.setHikPort(HikConstants.HIK_HOST);
        communityHik.setHikAk(HikConstants.AK);
        communityHik.setHikSk(HikConstants.SK);

        String url = HaiKUtils.camerasPreviewURLs(communityHik,"95e257593ecb4cce86ade8cac6be94f0", ProtocolConstants.PROTOCOL_WSS);
        System.out.println(url);

    }

}