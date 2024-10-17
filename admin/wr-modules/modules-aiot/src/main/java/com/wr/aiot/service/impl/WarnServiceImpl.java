package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.aiot.entity.WarnEventStatus;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.WarnEventMapper;
import com.wr.aiot.mapper.WarnEventStatusMapper;
import com.wr.aiot.service.WarnEventStatusService;
import com.wr.aiot.service.WarnService;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.*;
import com.wr.common.customize.util.CommunityHik;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wr.common.customize.util.HaiKUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-27 13:55:43
 * @Desc: 预警事件业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class WarnServiceImpl implements WarnService {
    private static final Logger log = LoggerFactory.getLogger(WarnServiceImpl.class);

    private final CommunityMapper communityMapper;
    private final WarnEventMapper warnEventMapper;
    private final WarnEventStatusMapper warnEventStatusMapper;

    private final RedisService redisService;

    @Override
    public List<WarnEvent> selectWarnEventList(WarnEvent warnEvent) {

        return warnEventMapper.selectWarnEventList(warnEvent);
    }

    @Override
    public WarnEvent selectWarnEventById(Long warnId) {

        return warnEventMapper.selectWarnEventById(warnId);
    }

    @Override
    public List<AcsData> selectAcsDataList(Long warnId, boolean getImg) {
        List<AcsData> acsDataList = warnEventMapper.selectAcsDataList(warnId);
        if (getImg) {
            acsDataList.forEach(acs -> {
                String picture = HikUtils.acsEventPictures(acs.getSvrIndexCode(), acs.getExtEventPictureUrl());
                acs.setExtEventPictureUrl(picture);
            });
        }
        return acsDataList;
    }

    @Override
    public List<VisData> selectVisDataList(Long warnId, boolean getImg) {
        List<VisData> visDataList = warnEventMapper.selectVisDataList(warnId);
        if (getImg) {
            visDataList.forEach(vis -> {
                String picture = HikUtils.visEventPictures(vis.getSvrIndexCode(), vis.getExtEventPictureUrl());
                vis.setExtEventPictureUrl(picture);
            });
        }
        return visDataList;
    }

    @Override
    public List<VideoData> selectVideoDataList(Long warnId) {

        return warnEventMapper.selectVideoDataList(warnId);
    }

    /**
     * 查询视频预警分析结果
     *
     * @param dataId
     * @param communityId
     * @return
     */
    @Override
    public List<VideoResult> selectVideoResultList(Long dataId, Long communityId) {
        List<VideoResult> videoResultList = warnEventMapper.selectVideoResultList(dataId);
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null != hik) {
            videoResultList.forEach(video -> {
                if (StringUtils.isNotEmpty(video.getImageServerCode())) {
                    String picture = null;
                    try {
                        picture = HaiKUtils.videoEventPictures(hik, video.getImageServerCode(), video.getImageUrl());
                        URL url = new URL(picture);
                        int port = url.getPort();
                        picture=picture.replace(String.valueOf(port), hik.getHikPort());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    video.setImageUrl(picture);
                }
            });
        }
        return videoResultList;
    }



    @Override
    @Transactional(readOnly = false)
    public Integer removeWarnEventByIds(Long[] ids) {

        return warnEventMapper.removeWarnEventByIds(ids);
    }

    /**
     * 订阅事件接口
     *
     * @param communityId 小区id
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public AjaxResult subscribeWarnEvent(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        String aiiotServerApp=redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "AIIOT_SERVER_APP");
        String eventDestY = aiiotServerApp + "/event/originalEvent";

        //原始事件
        boolean resultY = HaiKUtils.eventSubscription(hik, HikConstants.EVENT_WARN_Y, eventDestY, 0);
        //联动事件
        String eventDestL = aiiotServerApp + "/event/linkageEvent";
        boolean resultL = HaiKUtils.eventSubscription(hik, HikConstants.EVENT_WARN_L, eventDestL, 1);
        if (resultY && resultL) {
            LambdaQueryWrapper<WarnEventStatus> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WarnEventStatus::getCommunityId, communityId);
            WarnEventStatus warnEventStatus = warnEventStatusMapper.selectOne(queryWrapper);
            if (warnEventStatus != null) {
                warnEventStatus.setStatus("1");
                warnEventStatusMapper.updateById(warnEventStatus);
            } else {
                warnEventStatus = new WarnEventStatus();
                warnEventStatus.setStatus("1");
                warnEventStatus.setCommunityId(communityId);
                warnEventStatus.setCreateTime(new Date());
                warnEventStatus.setCreateBy(SecurityUtils.getUsername());
                warnEventStatusMapper.insert(warnEventStatus);
            }

            return AjaxResult.success("订阅成功！");
        } else {
            return AjaxResult.error("订阅失败！");
        }
    }

    /**
     * 取消事件订阅接口
     *
     * @param communityId 小区id
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult unsubscribeWarnEvent(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        // HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        boolean resultY = HaiKUtils.eventUnSubscription(hik, HikConstants.EVENT_WARN_Y);
        boolean resultL = HaiKUtils.eventUnSubscription(hik, HikConstants.EVENT_WARN_L);
        if (resultY && resultL) {
            LambdaQueryWrapper<WarnEventStatus> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WarnEventStatus::getCommunityId, communityId);
            WarnEventStatus warnEventStatus = warnEventStatusMapper.selectOne(queryWrapper);
            warnEventStatus.setStatus("0");
            warnEventStatusMapper.updateById(warnEventStatus);
            return AjaxResult.success("退订成功！");
        } else {
            return AjaxResult.error("退订失败！");
        }
    }


    @Override
    public AjaxResult elevatorSearch(Long communityId, String startTime, String endTime, Integer pageNo, Integer pageSize, String eventTypes) {

        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDate() + "T00:00:00+08:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDate() + "T23:59:59+08:00";
        }
        pageNo = 1;
        pageSize = 1000;
        Integer total = HaiKUtils.elevatorSearchTotal(hik, startTime, endTime, pageNo, pageSize, eventTypes, null, null, null, null, null);
        pageNo = (int) Math.ceil((double) total / pageSize);
        for (int i = 0; i < pageNo; i++) {
            JSONArray jsonArray = HaiKUtils.elevatorSearch(hik, startTime, endTime, pageNo, pageSize, eventTypes, null, null, null, null, null);
            log.info("梯控数据同步：第{}页，共：{}条数据", (i + 1), jsonArray.size());
            if (jsonArray.size() > 0) {
                for (int j = 0; j < jsonArray.size(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    WarnEvent warnEvent = new WarnEvent();
                    warnEvent.setEventId(String.valueOf(jsonObject.get("eventId")));
                    warnEvent.setEventType((Integer) jsonObject.get("eventType"));
                    warnEvent.setEventName(String.valueOf(jsonObject.get("eventName")));

                }
            }
        }

        return null;
    }
}
