package com.wr.aiot.web;

import com.wr.aiot.service.VisualSecordService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lvzy
 * @Date 2024/4/14 23:29
 */
@RestController
@RequestMapping("visualSecord")
@Api(tags = "单元楼出行记录")
public class VisualSecordController extends BaseController {

    @Autowired
    private VisualSecordService visualSecordService;


    /**
     * @param startTime 开始时间【yyyy-MM-dd HH:mm:ss】
     * @param endTime   结束时间【yyyy-MM-dd HH:mm:ss】
     * @return
     */
    @ApiOperation("初始化数据")
    @GetMapping("visualSecord")
    public AjaxResult visualSecord(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        try {
            visualSecordService.visualSecord("0", startTime, endTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }
}
