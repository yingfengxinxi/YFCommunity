package com.wr.estate.web.device;


import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.device.VisualSecordService;
import com.wr.remote.domain.VisualSecordVo;
import com.wr.remote.domain.vo.VisitingSecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/14 23:29
 */
@RestController
@RequestMapping("visualSecord")
@Api(tags = "物管平台-单元楼出行记录")
public class VisualSecordController extends BaseController {

    @Autowired
    private VisualSecordService visualSecordService;

    /**
     * @param visualSecordVo
     * @return
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(VisualSecordVo visualSecordVo) {
        startPage();
        List<VisualSecordVo> list = visualSecordService.getList(visualSecordVo);
        return getDataTable(list);
    }


}
