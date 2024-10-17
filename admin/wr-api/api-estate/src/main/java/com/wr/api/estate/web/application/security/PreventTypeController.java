package com.wr.api.estate.web.application.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.service.application.security.PreventTypeService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.estate.prevent.BusPreventType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/15 17:31
 */
@RestController
@RequestMapping("v2/preventType")
@Api(tags = "物业移动端-防控类型")
public class PreventTypeController extends BaseController {

    @Autowired
    private PreventTypeService preventTypeService;


    /**
     * 下拉查询
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("防控类型列表")
    public AjaxResult list() {
        LambdaQueryWrapper<BusPreventType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(BusPreventType::getSort);
        List<BusPreventType> rescueVOList = preventTypeService.list(queryWrapper);
        return AjaxResult.success(rescueVOList);
    }

}
