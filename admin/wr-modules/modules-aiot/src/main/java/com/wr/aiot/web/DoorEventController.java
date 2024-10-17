package com.wr.aiot.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.aiot.service.DoorEventService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.SysDictData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/4/3 17:29
 */
@RestController
@RequestMapping("doorEvent")
@Api(tags = "门禁点事件")
public class DoorEventController extends BaseController {


    @Autowired
    private DoorEventService doorEventService;


    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(DoorEvent doorEvent) {
        LambdaQueryWrapper<DoorEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(" (dev_index_code = '" + doorEvent.getDoorIndexCode() + "' or door_index_code='" + doorEvent.getDoorIndexCode() + "')");
        if (StringUtils.isNotEmpty(doorEvent.getEventType())) {
            queryWrapper.eq(DoorEvent::getEventType, doorEvent.getEventType());
        } else {
            List<SysDictData> dictCache = DictUtils.getDictCache("event_type");
            List<String> eventTypeList = dictCache.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            queryWrapper.in(DoorEvent::getEventType, eventTypeList);
        }
        if (StringUtils.isNotEmpty(doorEvent.getStartTime())) {
            queryWrapper.apply(" DATE_FORMAT(event_time,'%Y-%m-%d') BETWEEN '" + doorEvent.getStartTime() + "' AND '" + doorEvent.getEndTime() + "' ");
        }

        startPage();
        queryWrapper.orderByDesc(DoorEvent::getEventTime);
        List<DoorEvent> list = doorEventService.list(queryWrapper);
        return getDataTable(list);
    }


    /**
     * @param id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        return AjaxResult.success(doorEventService.getByIdEventInfo(id));
    }


    /**
     *
     * @param startTime 开始时间【yyyy-MM-dd HH:mm:ss】
     * @param endTime 结束时间【yyyy-MM-dd HH:mm:ss】
     * @return
     */
    @ApiOperation("初始化数据")
    @GetMapping("doorEvents")
    public AjaxResult doorEvents(@RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime) {
        try {
            doorEventService.doorEvents("0",startTime,endTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }
}
