package com.wr.aiot.callback.util;

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
     * 封装事件主体信息
     *
     * @param params
     * @param data        事件详情
     * @param communityId 小区id
     * @return
     */
    public static WarnEvent packageWarnEvent(JSONObject params, JSONObject event, JSONObject data, Long communityId) {
        WarnEvent warnEvent = new WarnEvent();
        String sendTime = String.valueOf(params.get("sendTime"));
        if (sendTime.contains("T")) {
            sendTime = sendTime.replace("T", " ").replace("+08:00", "");
            sendTime = sendTime.substring(0, 19);
        }

        warnEvent.setSendTime(sendTime);
        String ability = String.valueOf(params.get("ability"));
        warnEvent.setAbility(ability);
        warnEvent.setEventId(event.getString("eventId"));
        warnEvent.setSrcIndex(event.getString("srcIndex"));
        warnEvent.setSrcType(event.getString("srcType"));
        String srcName = String.valueOf(data.get("srcName")).replace("null","");
        if (StringUtils.isNotEmpty(srcName)) {
            warnEvent.setSrcName(srcName);
        } else {
            warnEvent.setSrcName("");
        }

        warnEvent.setEventType(event.getIntValue("eventType"));
        warnEvent.setStatus(event.getInteger("status"));
        warnEvent.setTimeout(event.getInteger("timeout"));
        String eventName = String.valueOf(event.get("eventName")).replace("null","");
        if (StringUtils.isNotEmpty(eventName)) {
            warnEvent.setEventName(eventName);
        } else {
            warnEvent.setEventName("");
        }

        String happenTime = String.valueOf(event.get("happenTime"));
        if (happenTime.contains("T")) {
            happenTime = happenTime.replace("T", " ").replace("+08:00", "");
            happenTime = happenTime.substring(0, 19);
        }

        warnEvent.setHappenTime(happenTime);
        String stopTime = String.valueOf(event.get("stopTime")).replace("null","");
        if (StringUtils.isNotEmpty(stopTime)) {
            if (stopTime.contains("T")) {
                stopTime = stopTime.replace("T", " ").replace("+08:00", "");
                stopTime = stopTime.substring(0, 19);
            }
            warnEvent.setStopTime(stopTime);
        } else {
            warnEvent.setStopTime("");
        }

        String remark = String.valueOf(event.get("remark")).replace("null","");
        if (StringUtils.isNotEmpty(remark)) {
            warnEvent.setRemark(remark);
        } else {
            warnEvent.setRemark("");
        }

        if (null != communityId && communityId != 0) {
            warnEvent.setCommunityId(communityId);
        }
        return warnEvent;
    }


    /**
     * 封装视频相关事件扩展信息
     *
     * @param warnId 预警主键
     * @param data   事件扩展信息
     * @return
     */
    public static VideoData packageVideoData(Long warnId, JSONObject data) {
        VideoData videoData = new VideoData();
        videoData.setWarnId(warnId);
        videoData.setDataType(data.getString("dataType"));

        String recvTime = String.valueOf(data.get("recvTime"));
        if (StringUtils.isNotEmpty(recvTime)) {
            if (recvTime.contains("T")) {
                recvTime = recvTime.replace("T", " ").replace("+08:00", "");
                recvTime = recvTime.substring(0, 19);
            }
        }else{
            recvTime="";
        }
        videoData.setRecvTime(recvTime);

        String sendTime = String.valueOf(data.get("sendTime"));
        if (StringUtils.isNotEmpty(sendTime)) {
            if (sendTime.contains("T")) {
                sendTime = sendTime.replace("T", " ").replace("+08:00", "");
                sendTime = sendTime.substring(0, 19);
            }
        }else{
            sendTime="";
        }
        videoData.setSendTime(sendTime);

        String dateTime = String.valueOf(data.get("dateTime"));
        if (StringUtils.isNotEmpty(dateTime)) {
            if (dateTime.contains("T")) {
                dateTime = dateTime.replace("T", " ").replace("+08:00", "");
                dateTime = dateTime.substring(0, 19);
            }
        }else{
            dateTime="";
        }
        videoData.setDateTime(dateTime);

        videoData.setIpAddress(data.getString("ipAddress"));
        videoData.setPortNo(data.getString("portNo"));
        videoData.setChannelId(data.getString("channelID"));
        videoData.setEventType(data.getString("eventType"));
        videoData.setEventDescription(data.getString("eventDescription"));
        return videoData;
    }

}
