package com.wr.govern.web.security;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.CommunityService;
import com.wr.govern.service.security.WarnService;
import com.wr.remote.aiot.*;
import com.wr.common.customize.util.CommunityHik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-27 13:47:52
 * @Desc: 预警事件
 */
@RestController
@RequestMapping("/warn")
public class WarnController extends BaseController {
    @Autowired
    private WarnService warnService;
    @Autowired
    private CommunityService communityService;

    @RequiresPermissions("govern:warn:list")
    @GetMapping("/list")
    public TableDataInfo list(WarnEvent warnEvent) {
        startPage();
        List<WarnEvent> list = warnService.selectWarnEventList(warnEvent);
        return getDataTable(list);
    }

    /**
     * 根据id查询数据
     */
    @RequiresPermissions("govern:warn:query")
    @GetMapping(value = "/{warnId}")
    public AjaxResult getInfo(@PathVariable Long warnId) {
        WarnEvent warnEvent = warnService.selectWarnEventById(warnId);
        if(null == warnEvent){
            return AjaxResult.error("未查询到相关数据");
        }
        CommunityHik hik = communityService.selectCommunityHikById(warnEvent.getCommunityId());
        boolean getImg = false;
        if(null != hik){
            HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
            getImg = true;
        }
        // 门禁事件
        if(Arrays.asList(HikConstants.EVENT_ACS).contains(warnEvent.getEventType())){
            List<AcsData> acsDataList = warnService.selectAcsDataList(warnId, getImg);
            warnEvent.setAcsDataList(acsDataList);
            return AjaxResult.success(warnEvent);
        }
        // 可视对讲事件
        if(Arrays.asList(HikConstants.EVENT_VIS).contains(warnEvent.getEventType())){
            List<VisData> visDataList = warnService.selectVisDataList(warnId, getImg);
            warnEvent.setVisDataList(visDataList);
            return AjaxResult.success(warnEvent);
        }
        // 视频预警
        if(Arrays.asList(HikConstants.EVENT_VIDEO).contains(warnEvent.getEventType())){
            List<VideoData> videoDataList = warnService.selectVideoDataList(warnId);
            warnEvent.setVideoDataList(videoDataList);
            for (VideoData videoData : videoDataList) {
                List<VideoResult> videoResultList = warnService.selectVideoResultList(videoData.getDataId(), getImg);
                videoData.setVideoResultList(videoResultList);
            }
            return AjaxResult.success(warnEvent);
        }
        // 烟火预警
        if(Arrays.asList(HikConstants.EVENT_FIRE).contains(warnEvent.getEventType())){
            List<VideoData> videoDataList = warnService.selectVideoDataList(warnId);
            warnEvent.setVideoDataList(videoDataList);
            for (VideoData videoData : videoDataList) {
                List<SmokeFire> smokeFireList = warnService.selectSmokeFireList(videoData.getDataId(), getImg);
                videoData.setSmokeFireList(smokeFireList);
            }
            return AjaxResult.success(warnEvent);
        }
        return AjaxResult.error();
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("govern:warn:remove")
    @Log(title = "预警事件", businessType = BusinessType.DELETE)
    @PutMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids) {

        return toAjax(warnService.removeWarnEventByIds(ids));
    }


}
