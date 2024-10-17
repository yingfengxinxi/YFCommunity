package com.wr.aiot.callback.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.aiot.callback.mapper.EventVideoMapper;
import com.wr.aiot.callback.service.EventService;
import com.wr.aiot.callback.util.HikEventUtil;
import com.wr.aiot.mapper.CameraMapper;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.WarnEventMapper;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.JsonUtil;
import com.wr.remote.aiot.Camera;
import com.wr.remote.aiot.VideoData;
import com.wr.remote.aiot.VideoResult;
import com.wr.remote.aiot.WarnEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-12-17 13:26:46
 * @Desc:
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true)
public class EventVideoServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventVideoServiceImpl.class);

    private final EventVideoMapper eventVideoMapper;
    private final WarnEventMapper warnEventMapper;


    @Override
    public Boolean support(Integer eventType) {

        return Arrays.asList(HikConstants.EVENT_VIDEO).contains(eventType);
    }

    /**
     * 视频事件
     *
     * @param params 事件从接收者（程序处理后）发出的时间
     * @param event  事件信息
     * @param data   事件详情
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(JSONObject params, JSONObject event, JSONObject data) {
        try {
            //添加联动事件主体数据
            String indexCode = event.getString("srcIndex");
            Long communityId = eventVideoMapper.selectCommunityIdByIndexCode(indexCode);
            WarnEvent warnEvent = HikEventUtil.packageWarnEvent(params, event, data, communityId);
            Long warnId = warnEventMapper.getEventIdInfo(warnEvent.getEventId());
            if (warnId == null) {
                warnEventMapper.insertWarnEvent(warnEvent);
                warnId = warnEventMapper.getEventIdInfo(warnEvent.getEventId());

                Integer eventType = Integer.valueOf(String.valueOf(event.get("eventType")));

                if (null != data) {
                    VideoData videoData = HikEventUtil.packageVideoData(warnId, data);
                    eventVideoMapper.insertVideoData(videoData);
                    Long dataId = videoData.getDataId();
                    //越界侦测
                    if (131585 == eventType) {
                        System.out.println("越界侦测事件" + data);
                        String linkageResultText = String.valueOf(event.get("linkageResult"));
                        if (StringUtils.isNotEmpty(linkageResultText)) {
                            List<VideoResult> results = packageVideoResult(warnId, dataId, communityId, event);
                            if (CollectionUtils.isNotEmpty(results)) {
                                eventVideoMapper.batchInsertVideoResult(results);
                            }
                        }
                    }
                    //高空抛物
                    else if (930335 == eventType) {
                        System.out.println("高空抛物事件" + data);
                        List<VideoResult> results = Lists.newArrayList();
                        //通道名称
                        String channelName = data.getString("channelName");
                        //透传字段
                        JSONObject targetAttrs = data.getJSONObject("targetAttrs");
                        //高空抛物信息
                        JSONArray detections = data.getJSONArray("ObjectsThrownDetection");
                        if (null != detections && !detections.isEmpty()) {
                            for (int j = 0; j < detections.size(); j++) {
                                VideoResult videoResult = new VideoResult();
                                videoResult.setChannelName(channelName);
                                String imageServerCode = "";
                                if (null != targetAttrs) {
                                    videoResult.setDeviceIndexCode(targetAttrs.getString("deviceIndexCode"));
                                    videoResult.setCameraIndexCode(targetAttrs.getString("cameraIndexCode"));
                                    imageServerCode = targetAttrs.getString("imageServerCode");
                                    if (StringUtils.isNotEmpty(imageServerCode)) {
                                        videoResult.setImageServerCode(imageServerCode);
                                    }
                                }

                                JSONObject image = detections.getJSONObject(j).getJSONObject("Image");
                                String resourcesContent = image.getString("resourcesContent");
                                videoResult.setImageUrl(resourcesContent);
                                videoResult.setDataId(dataId);
                                videoResult.setCommunityId(communityId);
                                videoResult.setWarnId(warnId);
                                results.add(videoResult);
                            }
                            if (!results.isEmpty()) {
                                eventVideoMapper.batchInsertVideoResult(results);
                            }
                        }
                    } else if (131588 == eventType) {
                        //区域入侵
                        System.out.println("区域入侵事件" + data);
                        String linkageResultText = String.valueOf(event.get("linkageResult"));
                        if (StringUtils.isNotEmpty(linkageResultText)) {
                            List<VideoResult> results = packageVideoResult(warnId, dataId, communityId, event);
                            if (CollectionUtils.isNotEmpty(results)) {
                                eventVideoMapper.batchInsertVideoResult(results);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param warnId
     * @param dataId
     * @param communityId
     * @param event
     * @return
     */
    private static List<VideoResult> packageVideoResult(Long warnId, Long dataId, Long communityId, JSONObject event) {
        JSONArray linkageResultArray = event.getJSONArray("linkageResult");
        List<VideoResult> results = Lists.newArrayList();
        for (int i = 0; i < linkageResultArray.size(); i++) {
            JSONObject linkageResultObject = linkageResultArray.getJSONObject(i);
            String linkageType = String.valueOf(linkageResultObject.get("linkageType"));
            if (StringUtils.equals(linkageType, "recordEventVideo")) {

                JSONArray jsonArray = linkageResultObject.getJSONArray("content");
                for (int j = 0; j < jsonArray.size(); j++) {
                    VideoResult videoResult = new VideoResult();
                    videoResult.setCommunityId(communityId);
                    videoResult.setWarnId(warnId);
                    videoResult.setDataId(dataId);
                    JSONObject result = jsonArray.getJSONObject(j);
                    //透传字段
                    videoResult.setCameraIndexCode(result.getString("cameraIndexCode"));
                    videoResult.setChannelName(result.getString("cameraName"));
                    //videoResult.setCameraType(result.getString("cameraName"));
                    //videoResult.setTreatyType(result.getString("cameraName"));
                    //videoResult.setStreamType(result.getString("cameraName"));
                    //videoResult.setRecordType(result.getString("cameraName"));
                    //videoResult.setDecodeTag(targetAttrs.getString("channelName"));

                    //JSONObject targetAttrs = result.getJSONObject("targetAttrs");
                    //videoResult.setDeviceIndexCode(targetAttrs.getString("deviceIndexCode"));


                    // String cameraAddress = targetAttrs.getString("cameraAddress");
                    //if (StringUtils.isNotEmpty(cameraAddress)) {
                    //    videoResult.setCameraAddress(cameraAddress);
                    //}
                    // float longitude = targetAttrs.getFloatValue("longitude");
                    // videoResult.setLongitude(String.valueOf(longitude));
                    //float latitude = targetAttrs.getFloatValue("latitude");
                    // videoResult.setLatitude(String.valueOf(latitude));
                    // String imageServerCode = targetAttrs.getString("imageServerCode");
                    // videoResult.setImageServerCode(imageServerCode);
                    // String imageUrl = result.getString("imageUrl");
                    // videoResult.setImageUrl(imageUrl);
                    results.add(videoResult);
                }
            }
        }

        return results;
    }


}
