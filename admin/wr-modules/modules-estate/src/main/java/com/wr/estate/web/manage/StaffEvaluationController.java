package com.wr.estate.web.manage;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.manage.StaffEvaluationService;
import com.wr.remote.domain.vo.StaffEvaluationVo;
import com.wr.remote.estate.manage.Staff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/18 13:44
 */
@RestController
@RequestMapping("staffEvaluation")
@Api(tags = "物管平台-员工评价")
public class StaffEvaluationController extends BaseController {

    @Autowired
    private StaffEvaluationService staffEvaluationService;

    /**
     * @param StaffEvaluationVo
     * @return
     */
    @ApiOperation("员工评价列表")
    @PostMapping("getList")
    public TableDataInfo getList(@RequestBody StaffEvaluationVo staffEvaluationVo) {
        PageUtils.startPage(staffEvaluationVo.getPageNum(),staffEvaluationVo.getPageSize());
        List<StaffEvaluationVo> list = staffEvaluationService.getList(staffEvaluationVo.getStaffId());
        return getDataTable(list);
    }
}
