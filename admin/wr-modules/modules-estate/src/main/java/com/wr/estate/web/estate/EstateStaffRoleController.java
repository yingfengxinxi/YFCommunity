package com.wr.estate.web.estate;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.service.estate.EstateStaffRoleService;
import com.wr.remote.domain.EstateMobileRoleMenu;
import com.wr.remote.domain.EstateStaffRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色表
 */
@RestController
@RequestMapping("estateStaffRole")
@Api(tags = "物业移动端-物业员工移动端角色中间表")
public class EstateStaffRoleController extends BaseController {

    @Autowired
    private EstateStaffRoleService estateStaffRoleService;


    /**
     * 列表
     *
     * @param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("列表")
    public AjaxResult list(@RequestParam("staffId") Long staffId) {

        return AjaxResult.success(estateStaffRoleService.getByStaffIdList(staffId));
    }

    /**
     * 新增数据
     *
     * @param estateStaffRoleList 实体
     * @return 新增结果
     */
    @ApiOperation("新增")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody List<EstateStaffRole> estateStaffRoleList) {
        estateStaffRoleService.insert(estateStaffRoleList);
        return AjaxResult.success();
    }
}
