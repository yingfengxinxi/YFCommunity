package com.wr.estate.web.reportingWarranty;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.DictUtils;
import com.wr.estate.service.reportingWarranty.ReportingWarrantyService;
import com.wr.remote.domain.ReportingWarranty;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.domain.SysUser;
import com.wr.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:57
 */
@RestController
@RequestMapping("reportingWarranty")
@Api(tags = "物管平台-报事报修")
public class ReportingWarrantyController extends BaseController {

    @Autowired
    private ReportingWarrantyService reportingWarrantyService;


    @RequestMapping("list")
    @ApiOperation("报事报修列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "当前登录账号loginName", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(ReportingWarranty reportingWarranty) {

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
}


