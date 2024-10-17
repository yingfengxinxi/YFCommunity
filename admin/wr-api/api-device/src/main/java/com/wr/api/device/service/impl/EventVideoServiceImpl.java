package com.wr.api.device.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.api.device.mapper.CommunityMapper;
import com.wr.api.device.mapper.EventVideoMapper;
import com.wr.api.device.mapper.WarnEventMapper;
import com.wr.api.device.service.EventService;
import com.wr.api.device.util.HikEventUtil;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.HikUtils;
import com.wr.remote.aiot.VideoData;
import com.wr.remote.aiot.VideoResult;
import com.wr.remote.aiot.WarnEvent;
import com.wr.common.customize.util.CommunityHik;
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
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EventVideoServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventVideoServiceImpl.class);

    private final EventVideoMapper eventVideoMapper;
    private final WarnEventMapper warnEventMapper;
    private final CommunityMapper communityMapper;

    @Override
    public Boolean support(Integer eventType) {

        return Arrays.asList(HikConstants.EVENT_VIDEO).contains(eventType);
    }

    @Override
    @Transactional(readOnly = false)
    public void execute(String sendTime, JSONObject event, JSONObject detail) {
        //添加联动事件主体数据
        String indexCode = detail.getString("srcIndex");
        Long communityId = eventVideoMapper.selectCommunityIdByIndexCode(indexCode);
        WarnEvent warnEvent  = HikEventUtil.packageWarnEvent(sendTime, event, detail, communityId);
        warnEventMapper.insertWarnEvent(warnEvent);
        Long warnId = warnEvent.getWarnId();
        int eventType = detail.getIntValue("eventType");
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        //事件扩展信息
        JSONObject data = detail.getJSONObject("data");
        if(null != data){
            VideoData videoData = HikEventUtil.packageVideoData(warnId, data);
            eventVideoMapper.insertVideoData(videoData);
            Long dataId = videoData.getDataId();
            //越界侦测
            if(131585==eventType){
                JSONArray detections = data.getJSONArray("linedetection");
                if(null != detections && !detections.isEmpty()){
                    List<VideoResult> results = packageVideoResult(dataId, detections, hik);
                    if(!results.isEmpty()){
                        eventVideoMapper.batchInsertVideoResult(results);
                    }
                }
            }
            //攀高
            else if(131597==eventType){
                JSONArray reachHight = data.getJSONArray("reachHight");
                if(null != reachHight && !reachHight.isEmpty()){
                    List<VideoResult> results = packageVideoResult(dataId, reachHight, hik);
                    if(!results.isEmpty()){
                        eventVideoMapper.batchInsertVideoResult(results);
                    }
                }
            }
            //倒地
            else if(131605==eventType){
                JSONArray fallDown = data.getJSONArray("fallDown");
                if(null != fallDown && !fallDown.isEmpty()){
                    List<VideoResult> results = packageVideoResult(dataId, fallDown, hik);
                    if(!results.isEmpty()){
                        eventVideoMapper.batchInsertVideoResult(results);
                    }
                }
            }
            //高空抛物
            else if(930335==eventType){
                List<VideoResult> results = Lists.newArrayList();
                //通道名称
                String channelName = data.getString("channelName");
                //透传字段
                JSONObject targetAttrs = data.getJSONObject("targetAttrs");
                //高空抛物信息
                JSONArray detections = data.getJSONArray("ObjectsThrownDetection");
                if(null != detections && !detections.isEmpty()){
                    for (int j = 0; j < detections.size(); j++) {
                        VideoResult videoResult =  new VideoResult();
                        videoResult.setChannelName(channelName);
                        String imageServerCode = "";
                        if(null != targetAttrs){
                            videoResult.setDeviceIndexCode(targetAttrs.getString("deviceIndexCode"));
                            videoResult.setCameraIndexCode(targetAttrs.getString("cameraIndexCode"));
                            imageServerCode = targetAttrs.getString("imageServerCode");
                            if(StringUtils.isNotEmpty(imageServerCode)){
                                videoResult.setImageServerCode(imageServerCode);
                            }
                        }

                        JSONObject image = detections.getJSONObject(j).getJSONObject("Image");
                        String resourcesContent = image.getString("resourcesContent");
                        videoResult.setImageUrl(resourcesContent);
                        results.add(videoResult);
                    }
                    if(!results.isEmpty()){
                        eventVideoMapper.batchInsertVideoResult(results);
                    }
                }
            }
        }
    }

    /**
     * 封装事件分析数据
     * @param dataId 扩展信息主键
     * @param jsonArray 分析结果
     * @return
     */
    private static List<VideoResult> packageVideoResult(Long dataId, JSONArray jsonArray,CommunityHik hik){
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        List<VideoResult> results = Lists.newArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            VideoResult videoResult =  new VideoResult();
            videoResult.setDataId(dataId);
            JSONObject result = jsonArray.getJSONObject(i);
            //透传字段
            JSONObject targetAttrs = result.getJSONObject("targetAttrs");
            videoResult.setDeviceIndexCode(targetAttrs.getString("deviceIndexCode"));
            videoResult.setCameraIndexCode(targetAttrs.getString("cameraIndexCode"));
            videoResult.setChannelName(targetAttrs.getString("channelName"));
            String cameraAddress = targetAttrs.getString("cameraAddress");
            if(StringUtils.isNotEmpty(cameraAddress)){
                videoResult.setCameraAddress(cameraAddress);
            }
            float longitude = targetAttrs.getFloatValue("longitude");
            videoResult.setLongitude(String.valueOf(longitude));
            float latitude = targetAttrs.getFloatValue("latitude");
            videoResult.setLatitude(String.valueOf(latitude));
            String imageServerCode = targetAttrs.getString("imageServerCode");
            videoResult.setImageServerCode(imageServerCode);
            String imageUrl = result.getString("imageUrl");
            videoResult.setImageUrl(imageUrl);
            results.add(videoResult);
        }
        return results;
    }


}
