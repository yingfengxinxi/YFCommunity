package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.application.serve.PropertyLog;
import com.wr.api.estate.entity.vo.application.serve.PropertyLogListVo;
import com.wr.api.estate.entity.vo.application.serve.RescueVO;
import com.wr.api.estate.service.application.serve.PropertyLogService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.estate.manage.contract.Owner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 15:32:22
 * @Desc: 物业缴费控制层
 */
@RestController
@RequestMapping("/v2/propertyLog")
@Api(tags = "物业移动端-缴费管理")
public class PropertyLogController extends BaseController {

    @Autowired
    private PropertyLogService propertyLogService;

    /**
     * 获取业主集合
     *
     * @param owner 小区id
     * @return
     */
    @AuthRequired
    @GetMapping("/list")

    public TableDataInfo selectOwnerList(Owner owner) {
        startPage();
        List<Owner> ownerList = propertyLogService.selectOwnerList(owner);
        return getDataTable(ownerList);
    }

    /**
     * 缴费记录列表
     *
     * @param propertyLogListVo
     * @return
     */
    @ApiOperation("缴费管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchValue", value = "姓名/手机号", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    @GetMapping("/getPropertyList")
    public TableDataInfo getPropertyList(PropertyLogListVo propertyLogListVo) {
        startPage();
        return getDataTable(propertyLogService.getPropertyList(propertyLogListVo));
    }

    /**
     * 缴费记录详情
     *
     * @param logId
     * @return
     */
    @ApiOperation("缴费记录详情")
    @GetMapping("/getByLogIdInfo")
    public AjaxResult getByLogIdInfo(@RequestParam("logId") Long logId) {

        return AjaxResult.success(propertyLogService.getByLogIdInfo(logId));
    }

    /**
     * 获取物业缴费记录集合
     *
     * @param ownerId 业主id
     * @return
     */
    @AuthRequired
    @GetMapping
    public AjaxResult selectPropertyLogList(Long ownerId) {
        List<PropertyLog> result = propertyLogService.selectPropertyLogList(ownerId);
        return AjaxResult.success(result);
    }

}
