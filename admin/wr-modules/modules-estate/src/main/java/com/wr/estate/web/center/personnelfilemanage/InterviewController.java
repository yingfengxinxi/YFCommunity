package com.wr.estate.web.center.personnelfilemanage;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.center.personnelfilemanage.InterviewService;
import com.wr.remote.domain.vo.InterviewVo;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.VisitorFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/14 11:20
 */
@RestController
@RequestMapping("interview")
@Api(tags = "物管平台-访客管理")
public class InterviewController extends BaseController {

    @Resource
    private InterviewService interviewService;

    //{"visitorPhone":"123","visitorName":"123","pageNum":1,"pageSize":10,"communityId":1}
    @ApiOperation("分页列表")
    @PostMapping("query")
    public TableDataInfo query(@RequestBody InterviewVo interviewVo) {
        PageUtils.startPage(interviewVo.getPageNum(), interviewVo.getPageSize());
        List<InterviewVo> query = interviewService.query(interviewVo);
        if (CollectionUtils.isNotEmpty(query)) {
            query.stream().forEach(interviewVo1 -> {
                interviewVo1.setOwnerName(DesensitizationUtils.name(interviewVo1.getOwnerName()));
                interviewVo1.setOwnerPhone(DesensitizationUtils.phone(interviewVo1.getOwnerPhone()));
                interviewVo1.setVisitorName(DesensitizationUtils.name(interviewVo1.getVisitorName()));
                interviewVo1.setVisitorPhone(DesensitizationUtils.phone(interviewVo1.getVisitorPhone()));
            });
        }
        return getDataTable(query);
    }

    /**
     * 详情
     *
     * @param
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("{visitorId}")
    public AjaxResult getVisitorById(@PathVariable("visitorId") Long visitorId) {
        InterviewVo interviewVo = interviewService.getVisitorById(visitorId);
        if (interviewVo != null) {
            interviewVo.setOwnerName(DesensitizationUtils.name(interviewVo.getOwnerName()));
            interviewVo.setOwnerPhone(DesensitizationUtils.phone(interviewVo.getOwnerPhone()));
            interviewVo.setVisitorName(DesensitizationUtils.name(interviewVo.getVisitorName()));
            interviewVo.setVisitorPhone(DesensitizationUtils.phone(interviewVo.getVisitorPhone()));
        }

        return AjaxResult.success(interviewVo);
    }


    /**
     * 导出
     */

    @Log(title = "访客档案信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InterviewVo interviewVo) {

        List<InterviewVo> list = interviewService.query(interviewVo);
        ExcelUtil<InterviewVo> util = new ExcelUtil(InterviewVo.class);
        util.exportExcel(response, list, "访客档案数据");

    }


    // 上传模板
    @ApiOperation("导入访客档案信息")
    @Log(title = "导入访客档案信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long communityId) throws Exception {
        ExcelUtil<InterviewVo> util = new ExcelUtil<InterviewVo>(InterviewVo.class);
        List<InterviewVo> dataList = util.importExcel(file.getInputStream());
        String message = interviewService.importData(dataList, communityId);
        return AjaxResult.success(message);
    }


    // 下载模板
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<InterviewVo> util = new ExcelUtil<InterviewVo>(InterviewVo.class);
        util.importTemplateExcel(response, "访客档案");
    }
}
