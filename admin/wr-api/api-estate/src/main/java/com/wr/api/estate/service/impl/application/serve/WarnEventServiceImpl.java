package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.mapper.application.serve.WarnEventMapper;
import com.wr.api.estate.service.application.serve.WarnEventService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.HikUtils;
import com.wr.remote.aiot.WarnEvent;
import com.wr.common.customize.util.CommunityHik;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 09:39:39
 * @Desc: 预警事件数据处理
 */
@Service
public class WarnEventServiceImpl implements WarnEventService {

    @Resource
    private  WarnEventMapper warnEventMapper;
    private final static String dateFormat="yyyy-MM-dd HH:mm:ss";

    @Override
    public CommunityHik selectCommunityHikById(Long communityId) {

        return warnEventMapper.selectHikByCommunityId(communityId);
    }

    @Override
    public List<LinkedHashMap<String,Object>> selectWarnEventList(Long communityId,String srcName) {
        List<LinkedHashMap<String, Object>> list = warnEventMapper.selectWarnEventList(communityId, srcName);
        list.forEach(e->{
            e.put("recordTime",DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDateTime) e.get("recordTime"))));
        });
        return list;
    }

    @Override
    public WarnEvent selectWarnEventById(Long warnId) {

        return warnEventMapper.selectWarnEventById(warnId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> selectAcsDataList(Long warnId, boolean getImg, String eventTyp) {
        List<LinkedHashMap<String, Object>> list = warnEventMapper.selectAcsDataList(warnId);
        list.forEach(e->{
            e.put("warnType","门禁事件");
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(eventTyp)){
                e.put("warnType", eventTyp);
            }
            if (StringUtils.isNotEmpty(String.valueOf(e.get("time")))&&StringUtils.isNotNull(e.get("time"))){
                e.put("time", timeFormat(String.valueOf(e.get("time"))));
            }
            if(getImg){
                String picture = HikUtils.acsEventPictures((String) e.get("svrIndexCode"), (String) e.get("imgUrl"));
                e.put("imgUrl",picture);
            }

        });
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> selectVisDataList(Long warnId, boolean getImg, String eventTyp) {
        List<LinkedHashMap<String, Object>> list = warnEventMapper.selectVisDataList(warnId);
        list.forEach(e->{
            e.put("warnType","可视对讲事件");
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(eventTyp)){
                e.put("warnType", eventTyp);
            }
            if (StringUtils.isNotEmpty(String.valueOf(e.get("time")))&&StringUtils.isNotNull(e.get("time"))){
                e.put("time", timeFormat(String.valueOf(e.get("time"))));
            }
            if(getImg){
                String picture = HikUtils.visEventPictures((String) e.get("svrIndexCode"), (String) e.get("imgUrl"));
                e.put("imgUrl",picture);
            }
        });
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> selectVideoDataList(Long warnId) {

        return warnEventMapper.selectVideoDataList(warnId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> selectVideoResultList(Long dataId, boolean getImg, String eventTyp) {
        List<LinkedHashMap<String, Object>> list = warnEventMapper.selectVideoResultList(dataId);
        list.forEach(e->{
            e.put("warnType","视频预警");
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(eventTyp)){
                e.put("warnType", eventTyp);
            }
            if(getImg){
                String picture = HikUtils.videoEventPictures((String) e.get("imageServerCode"), (String) e.get("imgUrl"));
                e.put("imgUrl",picture);
            }
        });
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> selectSmokeFireList(Long dataId, boolean getImg, String eventTyp) {
        List<LinkedHashMap<String, Object>> list = warnEventMapper.selectSmokeFireList(dataId);
        list.forEach(e->{
            e.put("warnType","烟火预警");
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(eventTyp)){
                e.put("warnType", eventTyp);
            }
            if(getImg){
                String picture = HikUtils.videoEventPictures((String) e.get("imageServerCode"), (String) e.get("imgUrl"));
                e.put("imgUrl",picture);
            }
        });

        return list;
    }

    private static String timeFormat(String time){
        return DateUtils.parseDateToStr(dateFormat, DateUtils.toDate(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(time)), ZoneOffset.of("+8"))));
    }
}
