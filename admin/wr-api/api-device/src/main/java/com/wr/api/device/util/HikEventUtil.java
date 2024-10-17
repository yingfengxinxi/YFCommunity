package com.wr.api.device.util;

import com.alibaba.fastjson2.JSONObject;
import com.wr.remote.aiot.VideoData;
import com.wr.remote.aiot.WarnEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: RainCity
 * @Date: 2021-11-25 20:50:05
 * @Desc: 事件工具类
 */
public class HikEventUtil {
    private static final Logger log = LoggerFactory.getLogger(HikEventUtil.class);

    /**
     * 封装联动事件主体信息
     * @param sendTime
     * @param detail 事件详情
     * @param communityId 小区id
     * @return
     */
    public static WarnEvent packageWarnEvent(String sendTime, JSONObject event, JSONObject detail, Long communityId){
        WarnEvent warnEvent = new WarnEvent();
        warnEvent.setSendTime(sendTime);
        warnEvent.setAbility(detail.getString("ability"));

        warnEvent.setEventId(event.getString("eventId"));
        warnEvent.setSrcIndex(detail.getString("srcIndex"));
        warnEvent.setSrcType(detail.getString("srcType"));
        String srcName = detail.getString("srcName");
        if(StringUtils.isNotEmpty(srcName)){
            warnEvent.setSrcName(srcName);
        }
        warnEvent.setEventType(detail.getIntValue("eventType"));
        warnEvent.setStatus(event.getInteger("status"));
        warnEvent.setTimeout(event.getInteger("timeout"));
        warnEvent.setEventName(event.getString("eventName"));
        warnEvent.setHappenTime(event.getString("happenTime"));
        warnEvent.setStopTime(event.getString("stopTime"));
        warnEvent.setRemark(event.getString("remark"));
        if(null != communityId && communityId != 0){
            warnEvent.setCommunityId(communityId);
        }
        return warnEvent;
    }

    /**
     * 封装联动事件主体信息
     * @param sendTime
     * @param event 事件详情
     * @param communityId 小区id
     * @return
     */
    public static WarnEvent packageWarnEvent(String sendTime,String ability, JSONObject event, Long communityId){
        WarnEvent warnEvent = new WarnEvent();
        warnEvent.setSendTime(sendTime);
        warnEvent.setAbility(ability);

        warnEvent.setEventId(event.getString("eventId"));
        warnEvent.setSrcIndex(event.getString("srcIndex"));
        warnEvent.setSrcType(event.getString("srcType"));
        String srcName = event.getString("srcName");
        if(StringUtils.isNotEmpty(srcName)){
            warnEvent.setSrcName(srcName);
        }
        warnEvent.setEventType(event.getIntValue("eventType"));
        warnEvent.setStatus(event.getInteger("status"));
        warnEvent.setTimeout(event.getInteger("timeout"));
        // warnEvent.setEventName(event.getString("eventName"));
        warnEvent.setHappenTime(event.getString("happenTime"));
        // warnEvent.setStopTime(event.getString("stopTime"));
        // warnEvent.setRemark(event.getString("remark"));
        if(null != communityId && communityId != 0){
            warnEvent.setCommunityId(communityId);
        }
        return warnEvent;
    }

    /**
     * 封装视频相关事件扩展信息
     * @param warnId 预警主键
     * @param data 事件扩展信息
     * @return
     */
    public static VideoData packageVideoData(Long warnId, JSONObject data){
        VideoData videoData = new VideoData();
        videoData.setWarnId(warnId);
        videoData.setDataType(data.getString("dataType"));
        videoData.setRecvTime(data.getString("recvTime"));
        videoData.setSendTime(data.getString("sendTime"));
        videoData.setDateTime(data.getString("dateTime"));
        videoData.setIpAddress(data.getString("ipAddress"));
        videoData.setPortNo(data.getString("portNo"));
        videoData.setChannelId(data.getString("channelID"));
        videoData.setEventType(data.getString("eventType"));
        videoData.setEventDescription(data.getString("eventDescription"));
        return videoData;
    }

}
