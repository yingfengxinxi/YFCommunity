package com.wr.estate.web.device;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.device.VisitingSecordService;
import com.wr.remote.aiot.VisitingSecord;
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
 * @Date 2024/4/3 17:29
 */
@RestController
@RequestMapping("visitingSecord")
@Api(tags = "物管平台-访客记录")
public class VisitingSecordController extends BaseController {


    @Autowired
    private VisitingSecordService visitingSecordService;


    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(VisitingSecordVo visitingSecordVo) {
        startPage();
        List<VisitingSecordVo> list = visitingSecordService.getList(visitingSecordVo);
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
}
