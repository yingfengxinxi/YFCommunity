package com.wr.estate.web.estate;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.estate.EstateRoleService;
import com.wr.estate.service.estate.EstateStaffRoleService;
import com.wr.remote.domain.EstateRole;
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
@RequestMapping("estateRole")
@Api(tags = "物业移动端-物业员工移动端角色表")
public class EstateRoleController extends BaseController {

    @Autowired
    private EstateRoleService estateRoleService;

    @Autowired
    private EstateStaffRoleService estateStaffRoleService;

    /**
     * 列表
     *
     * @param
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("列表")
    public TableDataInfo list(@RequestBody EstateRole estateRole) {
        // 获取用户所属物业
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        estateRole.setEstateId(estateId);
        PageUtils.startPage(estateRole.getPageNum(), estateRole.getPageSize());
        List<EstateRole> list = estateRoleService.getList(estateRole);
        return getDataTable(list);
    }
 /**
     * 列表
     *
     * @param
     * @return
     */
    @GetMapping("/listRole")
    @ApiOperation("列表")
    public AjaxResult listRole(EstateRole estateRole) {
        // 获取用户所属物业
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        estateRole.setEstateId(estateId);
        List<EstateRole> list = estateRoleService.getList(estateRole);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param estateRole 实体
     * @return 新增结果
     */
    @ApiOperation("新增")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody EstateRole estateRole) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        estateRole.setEstateId(estateId);
        int i2 = estateRoleService.getByNameCount(estateRole.getEstateId(),estateRole.getEstateRoleName(), null);
        if (i2 > 0) {
            return AjaxResult.error("角色名称已存在");
        }

        return toAjax(estateRoleService.insert(estateRole));
    }

    /**
     * 修改数据
     *
     * @param estateRole 实体
     * @return 修改结果
     */

    @PutMapping("/update")
    @ApiOperation("修改")
    public AjaxResult update(@RequestBody EstateRole estateRole) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        estateRole.setEstateId(estateId);
        int i2 = estateRoleService.getByNameCount(estateId,estateRole.getEstateRoleName(), estateRole.getEstateRoleId());
        if (i2 > 0) {
            return AjaxResult.error("角色名称已存在");
        }

        return toAjax(estateRoleService.update(estateRole));

    }

    /**
     * 删除数据
     *
     * @return 删除菜单
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestParam("estateRoleId") Long estateRoleId) {
        //校验角色是否被分配
        Integer count = estateStaffRoleService.getByEstateRoleIdCount(estateRoleId);
        if (count >= 1) {
            return AjaxResult.error("当前角色被使用,请解绑后删除");
        }
        estateRoleService.delete(estateRoleId);
        return AjaxResult.success();
    }

    /**
     * 通过主键查询单条数据
     */
    @ApiOperation("详情")
    @GetMapping(value = "/{estateRoleId}")
    public AjaxResult getInfo(@PathVariable("estateRoleId") Long estateRoleId) {
        return AjaxResult.success(estateRoleService.getById(estateRoleId));
    }

}
