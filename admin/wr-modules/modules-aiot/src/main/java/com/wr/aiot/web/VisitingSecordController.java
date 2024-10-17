package com.wr.aiot.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.aiot.service.DoorEventService;
import com.wr.aiot.service.VisitingSecordService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.domain.SysDictData;
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
@RequestMapping("visitingSecord")
@Api(tags = "访客记录")
public class VisitingSecordController extends BaseController {


    @Autowired
    private VisitingSecordService visitingSecordService;


    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(VisitingSecord visitingSecord) {
        LambdaQueryWrapper<VisitingSecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VisitingSecord::getDoorIndexCode, visitingSecord.getDoorIndexCode());

        if (StringUtils.isNotEmpty(visitingSecord.getStartTime())) {
            queryWrapper.apply(" DATE_FORMAT(visit_register_time,'%Y-%m-%d') BETWEEN '" + visitingSecord.getStartTime() + "' AND '" + visitingSecord.getEndTime() + "' ");
        }
        startPage();
        queryWrapper.orderByDesc(VisitingSecord::getVisitRegisterTime);
        List<VisitingSecord> list = visitingSecordService.list(queryWrapper);
        return getDataTable(list);
    }


    /**
     * @param id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        //
        return AjaxResult.success(visitingSecordService.getByIdVisitingSecordInfo(id));
    }


    @ApiOperation("初始化数据")
    @GetMapping("visitingSecords")
    public AjaxResult doorEvents() {
        try {
            visitingSecordService.visitingSecord("0");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }
}
