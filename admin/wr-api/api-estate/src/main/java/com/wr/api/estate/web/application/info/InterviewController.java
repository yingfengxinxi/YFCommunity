package com.wr.api.estate.web.application.info;

import com.wr.api.estate.service.application.info.InterviewService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.vo.InterviewVo;
import com.wr.remote.domain.vo.VisitorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/3/14 10:59
 */
@RequestMapping("v2/interview")
@RestController
@Api(tags = "物业移动端-访客信息")
public class InterviewController extends BaseController {

    @Autowired
    private InterviewService interviewService;


    /**
     * 访客列表
     *
     * @param interviewVo
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("访客列表")
    public TableDataInfo getVisitPageList(@RequestBody InterviewVo interviewVo) {
        PageUtils.startPage(interviewVo.getPageNum(), interviewVo.getPageSize());
        return getDataTable(interviewService.getList(interviewVo));
    }

    /**
     * 访客详情
     *
     * @param visitorId
     * @return
     */
    @GetMapping("/getVisitorById")
    @ApiOperation("详情")
    public AjaxResult getVisitorById(@RequestParam("visitorId") String visitorId) {
        //调用接口获取内容
        return AjaxResult.success(interviewService.getVisitorById(visitorId));
    }

    /**
     * 访客总数
     *
     * @param communityId
     * @return
     */
    @ApiOperation("访客数量")
    @GetMapping("/count")
    public AjaxResult getCount(@RequestParam("communityId") Long communityId) {
        int size = interviewService.getVisitCount(communityId);
        return AjaxResult.success(size);
    }
}
