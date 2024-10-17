package com.wr.api.estate.web.application.info;


import com.wr.api.estate.service.application.info.VisitorService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.vo.VisitorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/2/21 10:14
 */
@RequestMapping("v2/visitor")
@RestController
@Api(tags = "物业移动端-访客信息【弃用】")
public class VisitorController extends BaseController {

    @Autowired
    private VisitorService visitorService;


    /**
     * 访客列表
     *
     * @param visitorVo
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("访客列表")
    @AuthRequired
    public TableDataInfo getVisitPageList(@RequestBody VisitorVo visitorVo) {
        PageUtils.startPage(visitorVo.getPageNum(),visitorVo.getPageSize());
        return getDataTable(visitorService.getList(visitorVo));
    }

    /**
     * 访客详情
     *
     * @param visitorId
     * @return
     */
    @GetMapping("/getVisitorById")
    @AuthRequired
    @ApiOperation("详情")
    public AjaxResult getVisitorById(@RequestParam("visitorId") Long visitorId) {
        //调用接口获取内容
        return AjaxResult.success(visitorService.getVisitorById(visitorId));
    }

    /**
     * 访客总数
     *
     * @param communityId
     * @return
     */
    @ApiOperation("访客数量")
    @AuthRequired
    @GetMapping("/count")
    public AjaxResult getCount(@RequestParam("communityId") Long communityId) {
        int size = visitorService.getVisitCount(communityId);
        return AjaxResult.success(size);
    }
}
