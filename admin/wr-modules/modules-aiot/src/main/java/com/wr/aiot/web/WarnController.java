package com.wr.aiot.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.aiot.entity.WarnEventStatus;
import com.wr.aiot.mapper.WarnEventStatusMapper;
import com.wr.aiot.service.CommunityService;
import com.wr.aiot.service.WarnEventStatusService;
import com.wr.aiot.service.WarnService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.remote.aiot.AcsData;
import com.wr.remote.aiot.SmokeFire;
import com.wr.remote.aiot.VideoData;
import com.wr.remote.aiot.VideoResult;
import com.wr.remote.aiot.VisData;
import com.wr.remote.aiot.WarnEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-27 13:47:52
 * @Desc: 预警事件
 */
@RestController
@RequestMapping("/warn")
@Api(tags = "预警事件")
public class WarnController extends BaseController {
    @Autowired
    private WarnService warnService;

    @Autowired
    private WarnEventStatusService warnEventStatusService;

    @ApiOperation("分页列表")
    @GetMapping("/list")
    public AjaxResult list(WarnEvent warnEvent) {
        startPage();
        List<WarnEvent> list = warnService.selectWarnEventList(warnEvent);
        LambdaQueryWrapper<WarnEventStatus> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarnEventStatus::getCommunityId, warnEvent.getCommunityId());
        WarnEventStatus warnEventStatus = warnEventStatusService.getOne(queryWrapper);
        String status = "0";
        if (warnEventStatus != null) {
            status = warnEventStatus.getStatus();
        }

        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("eventStatus", status);
        TableDataInfo dataTable = getDataTable(list);
        ajaxResult.put("pageList", dataTable);
        return ajaxResult;
    }

    /**
     * 根据id查询数据
     */
    @ApiOperation("根据id查询数据")
    @GetMapping(value = "/{warnId}")
    public AjaxResult getInfo(@PathVariable Long warnId) {
        WarnEvent warnEvent = warnService.selectWarnEventById(warnId);
        if (null == warnEvent) {
            return AjaxResult.error("未查询到相关数据");
        }
        // 视频预警

        List<VideoData> videoDataList = warnService.selectVideoDataList(warnId);
        warnEvent.setVideoDataList(videoDataList);
        for (VideoData videoData : videoDataList) {
            List<VideoResult> videoResultList = warnService.selectVideoResultList(videoData.getDataId(), warnEvent.getCommunityId());
            videoData.setVideoResultList(videoResultList);
        }
        return AjaxResult.success(warnEvent);

    }

    /**
     * 删除数据
     */
    @ApiOperation("删除数据")
    @Log(title = "预警事件", businessType = BusinessType.DELETE)
    @PutMapping("/{ids}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids) {

        return toAjax(warnService.removeWarnEventByIds(ids));
    }


    /**
     * 预警事件订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("预警事件订阅")
    @Log(title = "预警事件订阅", businessType = BusinessType.OTHER)
    @PostMapping("/subscribe/{communityId}")
    public AjaxResult subscribe(@PathVariable Long communityId) {

        return warnService.subscribeWarnEvent(communityId);
    }

    /**
     * 预警事件取消订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("预警事件取消订阅")
    @Log(title = "预警事件取消订阅", businessType = BusinessType.OTHER)
    @PostMapping("/unsubscribe/{communityId}")
    public AjaxResult unsubscribe(@PathVariable Long communityId) {

        return warnService.unsubscribeWarnEvent(communityId);
    }
}
