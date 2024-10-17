package com.wr.api.owner.web.complaintSuggestion;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.owner.service.complaintSuggestion.ComplaintSuggestionService;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.ComplaintSuggestion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/25 16:18
 */
@RestController
@RequestMapping("/v2/complaintSuggestion")
@Api(tags = "物管平台-投诉与建议")
public class ComplaintSuggestionController extends BaseController {

    @Autowired
    private ComplaintSuggestionService complaintSuggestionService;


    /**
     * 分页
     *
     * @param complaintSuggestion 查询对象
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("投诉与建议列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchValue", value = "关键字【问题/回答】", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "complaintSuggestionType", value = "投诉类型【数据字典complaint_suggestion_type】", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "回复状态0=未回复1=回复", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(@RequestBody ComplaintSuggestion complaintSuggestion) {

        LambdaQueryWrapper<ComplaintSuggestion> queryWrapper = new LambdaQueryWrapper<>();
//        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        queryWrapper.eq(ComplaintSuggestion::getEstateId, complaintSuggestion.getEstateId());
        queryWrapper.eq(ComplaintSuggestion::getDelFlag, "0");
        if (complaintSuggestion.getCommunityId() != null) {
            queryWrapper.eq(ComplaintSuggestion::getCommunityId, complaintSuggestion.getCommunityId());
        }
        if (StringUtils.isNotEmpty(complaintSuggestion.getSearchValue())) {
            queryWrapper.like(ComplaintSuggestion::getProblemDescription, complaintSuggestion.getSearchValue()).or().
                    like(ComplaintSuggestion::getPropertyResponse, complaintSuggestion.getSearchValue());
        }
        if (StringUtils.isNotEmpty(complaintSuggestion.getComplaintSuggestionType())) {
            queryWrapper.eq(ComplaintSuggestion::getComplaintSuggestionType, complaintSuggestion.getComplaintSuggestionType());
        }

        if (StringUtils.isNotEmpty(complaintSuggestion.getStatus())) {
            if (StringUtils.equals(complaintSuggestion.getStatus(), "0")) {
                queryWrapper.apply(" (property_response='' or property_response IS NULL )");
            }
            if (StringUtils.equals(complaintSuggestion.getStatus(), "1")) {
                queryWrapper.apply(" (property_response!='' and property_response IS NOT NULL )");
            }
        }

        queryWrapper.orderByDesc(ComplaintSuggestion::getCreateTime);
        PageUtils.startPage(complaintSuggestion.getPageNum(), complaintSuggestion.getPageSize());
        return getDataTable(complaintSuggestionService.list(queryWrapper));
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("投诉与建议详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {
        ComplaintSuggestion complaintSuggestion = complaintSuggestionService.getById(id);
        if (complaintSuggestion != null) {
            String problemImage = complaintSuggestion.getProblemImage();
            if (StringUtils.isNotEmpty(problemImage)) {
                complaintSuggestion.setProblemImageLists(Arrays.asList(problemImage.split(",")));
            }
        }

        return AjaxResult.success(complaintSuggestion);
    }


    /**
     * @param complaintSuggestion
     * @return
     */
    @ApiOperation("投诉与建议回复内容")
    @PostMapping("update")
    public AjaxResult update(@RequestBody ComplaintSuggestion complaintSuggestion) {

        complaintSuggestion.setUpdateTime(new Date());
        complaintSuggestion.setUpdateBy(SecurityUtils.getUsername());
        complaintSuggestionService.updateById(complaintSuggestion);
        return AjaxResult.success();
    }

    /**
     * @param complaintSuggestion
     * @return
     */
    @ApiOperation("投诉与建议新增")
    @PostMapping("addComplaintSuggestion")
    public AjaxResult addComplaintSuggestion(@RequestBody ComplaintSuggestion complaintSuggestion) {

        return AjaxResult.success(complaintSuggestionService.save(complaintSuggestion));
    }
}
