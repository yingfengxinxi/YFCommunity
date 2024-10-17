package com.wr.govern.service.impl.security;

import com.wr.common.customize.util.HikUtils;
import com.wr.govern.mapper.security.WarnEventMapper;
import com.wr.govern.service.security.WarnService;
import com.wr.remote.aiot.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final WarnEventMapper warnEventMapper;

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
        if(getImg){
            acsDataList.forEach(acs ->{
                String picture = HikUtils.acsEventPictures(acs.getSvrIndexCode(), acs.getExtEventPictureUrl());
                acs.setExtEventPictureUrl(picture);
            });
        }
        return acsDataList;
    }

    @Override
    public List<VisData> selectVisDataList(Long warnId, boolean getImg) {

        List<VisData> visDataList = warnEventMapper.selectVisDataList(warnId);
        if(getImg){
            visDataList.forEach(vis ->{
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

    @Override
    public List<VideoResult> selectVideoResultList(Long dataId, boolean getImg) {
        List<VideoResult> videoResultList = warnEventMapper.selectVideoResultList(dataId);
        if(getImg){
            videoResultList.forEach(video ->{
                String picture = HikUtils.videoEventPictures(video.getImageServerCode(), video.getImageUrl());
                video.setImageUrl(picture);
            });
        }
        return videoResultList;
    }

    @Override
    public List<SmokeFire> selectSmokeFireList(Long dataId, boolean getImg) {
        List<SmokeFire> smokeFireList = warnEventMapper.selectSmokeFireList(dataId);
        if(getImg){
            smokeFireList.forEach(smokeFire ->{
                String picture = HikUtils.videoEventPictures(smokeFire.getImageServerCode(), smokeFire.getImageUrl());
                smokeFire.setImageUrl(picture);
            });
        }
        return smokeFireList;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer removeWarnEventByIds(Long[] ids) {

        return warnEventMapper.removeWarnEventByIds(ids);
    }

}
