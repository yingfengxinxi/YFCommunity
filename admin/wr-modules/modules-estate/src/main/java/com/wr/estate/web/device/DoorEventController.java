package com.wr.estate.web.device;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.DictUtils;
import com.wr.estate.service.device.DoorEventService;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.domain.vo.DoorEventVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/4/3 17:29
 */
@RestController
@RequestMapping("doorEvent")
@Api(tags = "物管平台-门禁点事件")
public class DoorEventController extends BaseController {


    @Autowired
    private DoorEventService doorEventService;


    /**
     *
     * @param doorEventVo
     * @return
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(DoorEventVo doorEventVo) {
        startPage();
        if(StringUtils.isEmpty(doorEventVo.getEventType())){
            List<SysDictData> dictCache = DictUtils.getDictCache("event_type");
            List<String> eventTypeList = dictCache.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            doorEventVo.setEventTypes(eventTypeList);
        }
        return getDataTable(doorEventService.getList(doorEventVo));
    }

    /**
     * @return
     */
    @ApiOperation("门禁点列表下拉")
    @GetMapping("/getAllDoorList")
    public AjaxResult getAllDoorList() {

        return AjaxResult.success(doorEventService.getAllDoorList());
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
}
