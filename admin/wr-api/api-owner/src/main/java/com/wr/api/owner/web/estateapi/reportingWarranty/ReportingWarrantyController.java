package com.wr.api.owner.web.estateapi.reportingWarranty;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wr.api.owner.service.estateapi.reportingWarranty.ReportingWarrantyService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.ReportingWarranty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:57
 */
@RestController
@RequestMapping("/estateapi/reportingWarranty")
@Api(tags = "业主端-报事报修")
public class ReportingWarrantyController extends BaseController {

    @Autowired
    private ReportingWarrantyService reportingWarrantyService;


    @RequestMapping("findListReportingWarranty")
    @ApiOperation("报事报修列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "当前登录账号loginName", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo findListReportingWarranty(ReportingWarranty reportingWarranty) {
        startPage();
        return getDataTable(reportingWarrantyService.getList(reportingWarranty));
    }

    @ApiOperation("报事报修详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        ReportingWarranty warranty = reportingWarrantyService.get(id);
        if (warranty != null) {
            String reportImage = warranty.getReportImage();
            if (StringUtils.isNotEmpty(reportImage)) {
                warranty.setReportImages(Arrays.asList(reportImage.split(",")));
            }
            String orderImage = warranty.getOrderImage();
            if (StringUtils.isNotEmpty(orderImage)) {
                warranty.setOrderImages(Arrays.asList(orderImage.split(",")));
            }
        }
        return AjaxResult.success(warranty);
    }

    @ApiOperation("新增报事报修")
    @PostMapping("saveReportingWarranty")
    public AjaxResult saveReportingWarranty(@RequestBody ReportingWarranty reportingWarranty) {

        reportingWarranty.setNo( "BSBX"+ IdWorker.getIdStr() );
        reportingWarranty.setReportStatus( "0" );
        boolean save = reportingWarrantyService.save( reportingWarranty );
        return AjaxResult.success(save?"上报成功":"上报失败，请稍后重试");
    }

    @ApiOperation("工单评价")
    @PostMapping("updateReportingWarranty")
    public AjaxResult updateReportingWarranty(@RequestBody ReportingWarranty reportingWarranty) {
        if(reportingWarranty == null || StringUtils.isEmpty( reportingWarranty.getId() )){
            throw new ServiceException("获取ID信息失败，请刷新重试");
        }

        boolean update = reportingWarrantyService.updateById( reportingWarranty );
        return AjaxResult.success(update?"评价成功":"评价失败，请稍后重试");
    }
}


