package com.wr.aiot.callback.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;

import com.wr.aiot.callback.mapper.EventVideoMapper;
import com.wr.aiot.callback.service.EventService;
import com.wr.aiot.callback.util.HikEventUtil;
import com.wr.aiot.mapper.WarnEventMapper;
import com.wr.common.customize.constant.HikConstants;
import com.wr.remote.aiot.SmokeFire;
import com.wr.remote.aiot.VideoData;
import com.wr.remote.aiot.WarnEvent;
import lombok.RequiredArgsConstructor;
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
 * @Date: 2021-12-17 11:57:47
 * @Desc:
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EventFireServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(EventFireServiceImpl.class);

    private final EventVideoMapper eventVideoMapper;
    private final WarnEventMapper warnEventMapper;

//    @Override
//    public Boolean support(Integer eventType) {
//
//        return Arrays.asList(HikConstants.EVENT_FIRE).contains(eventType);
//    }

//
//    @Override
//    @Transactional(readOnly = false)
//    public void execute(String sendTime, JSONObject event, JSONObject detail) {
//        //添加联动事件主体数据
//        String indexCode = detail.getString("srcIndex");
//        Long communityId = eventVideoMapper.selectCommunityIdByIndexCode(indexCode);
//        WarnEvent warnEvent  = HikEventUtil.packageWarnEvent(sendTime, event, detail, communityId);
//        warnEventMapper.insertWarnEvent(warnEvent);
//        Long warnId = warnEvent.getWarnId();
//        int eventType = detail.getIntValue("eventType");
//        //事件扩展信息
//        JSONObject data = detail.getJSONObject("data");
//        if(null != data){
//            VideoData videoData = HikEventUtil.packageVideoData(warnId, data);
//            eventVideoMapper.insertVideoData(videoData);
//            Long dataId = videoData.getDataId();
//            //烟雾
//            if(192513 == eventType){
//                //分析结果
//                JSONArray smokeDetection = data.getJSONArray("smokeDetection");
//                if(null != smokeDetection && !smokeDetection.isEmpty()){
//                    List<SmokeFire> results = packageSmokeData(dataId, smokeDetection);
//                    if(!results.isEmpty()){
//                        eventVideoMapper.insertSmokeFire(results);
//                    }
//                }
//            }
//            //火点
//            else if(192515 == eventType){
//                //分析结果
//                JSONArray fireDetection = data.getJSONArray("fireDetection");
//                if(null != fireDetection && !fireDetection.isEmpty()){
//                    List<SmokeFire> results = packageFireData(dataId, fireDetection);
//                    if(!results.isEmpty()){
//                        eventVideoMapper.insertSmokeFire(results);
//                    }
//                }
//            }
//            //烟火
//            else if(192514 == eventType){
//                //分析结果
//                JSONArray smokeFireDetection = data.getJSONArray("smokeAndFireDetection");
//                if(null != smokeFireDetection && !smokeFireDetection.isEmpty()){
//                    List<SmokeFire> results = packageSmokeFireData(dataId, smokeFireDetection);
//                    if(!results.isEmpty()){
//                        eventVideoMapper.insertSmokeFire(results);
//                    }
//                }
//            }
//        }
//    }

    /**
     * 封装分析结果数据
     * @param dataId 数据主键
     * @param detection 分析结果
     * @return
     */
    private static SmokeFire packageDetection(Long dataId, JSONObject detection){
        SmokeFire smokeFire = new SmokeFire();
        smokeFire.setDataId(dataId);

        smokeFire.setVisiblePicUrl(detection.getString("visiblePicUrl"));
        //透传字段
        JSONObject targetAttrs = detection.getJSONObject("targetAttrs");
        smokeFire.setDeviceIndexCode(detection.getString("deviceIndexCode"));
        smokeFire.setCameraIndexCode(detection.getString("cameraIndexCode"));
        String imageServerCode = targetAttrs.getString("imageServerCode");
        smokeFire.setImageServerCode(imageServerCode);
        String imageUrl = detection.getString("imageUrl");
        smokeFire.setImageUrl(imageUrl);
        String cameraAddress = targetAttrs.getString("cameraAddress");
        if(StringUtils.isNotEmpty(cameraAddress)){
            smokeFire.setCameraAddress(cameraAddress);
        }

        return smokeFire;
    }

    /**
     * 封装烟雾分析结果
     * @param dataId 事件扩展信息主键
     * @param jsonArray 分析结果
     * @return
     */
    private static List<SmokeFire> packageSmokeData(Long dataId, JSONArray jsonArray){
        List<SmokeFire> list = Lists.newArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject detection = jsonArray.getJSONObject(i);
            SmokeFire smokeFire = packageDetection(dataId, detection);
            smokeDetection(smokeFire, detection);
            list.add(smokeFire);
        }
        return list;
    }

    /**
     * 烟雾检测信息
     * @param smokeFire
     * @param detection
     */
    private static void smokeDetection(SmokeFire smokeFire,JSONObject detection){
        //烟雾框
        JSONObject region = detection.getJSONObject("smokeRegion");
        smokeFire.setSmokeRegionWidth(region.getString("width"));
        smokeFire.setSmokeRegionHeight(region.getString("height"));
        //绝对高度
        JSONObject absolute = detection.getJSONObject("absoluteHigh");
        smokeFire.setSmokeElevation(absolute.getString("elevation"));
        smokeFire.setSmokeAzimuth(absolute.getString("azimuth"));
        smokeFire.setSmokeZoom(absolute.getString("absoluteZoom"));
    }

    /**
     * 封装火点分析结果
     * @param dataId 事件扩展信息主键
     * @param jsonArray 分析结果
     * @return
     */
    private static List<SmokeFire> packageFireData(Long dataId, JSONArray jsonArray) {
        List<SmokeFire> list = Lists.newArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject detection = jsonArray.getJSONObject(i);
            SmokeFire smokeFire = packageDetection(dataId, detection);
            fireDetection(smokeFire, detection);
            list.add(smokeFire);
        }
        return list;
    }

    /**
     * 火点检测信息
     * @param smokeFire
     * @param detection
     */
    private static void fireDetection(SmokeFire smokeFire,JSONObject detection){
        smokeFire.setTemperatureUnit(detection.getString("temperatureUnit"));
        smokeFire.setFireMaxTemperature(detection.getString("fireMaxTemperature"));
        smokeFire.setTargetDistance(detection.getString("targetDistance"));
        smokeFire.setFireScanWaitMode(detection.getString("fireScanWaitMode"));
        //火点框
        JSONObject region = detection.getJSONObject("fireRegion");
        smokeFire.setFireRegionWidth(region.getString("width"));
        smokeFire.setFireRegionHeight(region.getString("height"));
        //最高温度点坐标
        JSONObject point = detection.getJSONObject("highestPoint");
        smokeFire.setFireHighestX(point.getString("x"));
        smokeFire.setFireHighestY(point.getString("y"));
        //绝对高度
        JSONObject absolute = detection.getJSONObject("absoluteHigh");
        smokeFire.setFireElevation(absolute.getString("elevation"));
        smokeFire.setFireAzimuth(absolute.getString("azimuth"));
        smokeFire.setFireZoom(absolute.getString("absoluteZoom"));
    }

    /**
     * 封装烟火分析结果数据
     * @param dataId 数据主键
     * @param jsonArray 分析结果
     * @return
     */
    private static List<SmokeFire> packageSmokeFireData(Long dataId, JSONArray jsonArray){
        List<SmokeFire> list = Lists.newArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject detection = jsonArray.getJSONObject(i);
            SmokeFire smokeFire = packageDetection(dataId, detection);
            //火点检测信息
            JSONObject fireDetection = detection.getJSONObject("fireDetection");
            fireDetection(smokeFire, fireDetection);
            //烟雾检测信息
            JSONObject smokeDetection = detection.getJSONObject("smokeDetection");
            smokeDetection(smokeFire, smokeDetection);
            list.add(smokeFire);
        }
        return list;
    }

}
