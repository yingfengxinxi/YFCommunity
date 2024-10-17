package com.wr.estate.web.device;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.device.CrossRecordService;
import com.wr.remote.aiot.CrossRecord;
import com.wr.remote.domain.vo.VisitingSecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/10 8:55
 */
@RestController
@RequestMapping("crossRecord")
@Api(tags = "物管平台-车辆出行")
public class CrossRecordController extends BaseController {

    @Autowired
    private CrossRecordService crossRecordService;


    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(CrossRecord crossRecord) {
        LambdaQueryWrapper<CrossRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CrossRecord::getCommunityId, crossRecord.getCommunityId());
        if (StringUtils.isNotEmpty(crossRecord.getPlateNo())) {
            queryWrapper.eq(CrossRecord::getPlateNo, crossRecord.getPlateNo());
        }
        if (StringUtils.isNotEmpty(crossRecord.getPersonName())) {
            queryWrapper.eq(CrossRecord::getPersonName, crossRecord.getPersonName());
        }
        if (StringUtils.isNotEmpty(crossRecord.getStartTime())) {
            queryWrapper.apply(" DATE_FORMAT(cross_time, '%Y-%m-%d') BETWEEN '" + crossRecord.getStartTime() + "' and '" + crossRecord.getEndTime() + "'");
        }
        queryWrapper.orderByDesc(CrossRecord::getCrossTime);
        startPage();
        List<CrossRecord> list = crossRecordService.list(queryWrapper);
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
        return AjaxResult.success(crossRecordService.getById(id));
    }
}
