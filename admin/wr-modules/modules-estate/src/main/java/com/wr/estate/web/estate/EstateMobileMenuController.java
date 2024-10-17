package com.wr.estate.web.estate;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.estate.EstateMobileMenuService;
import com.wr.estate.service.estate.EstateMobileRoleMenuService;
import com.wr.remote.domain.EstateMobileMenu;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.activity.ActiviteType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端菜单表
 */
@RestController
@RequestMapping("estateMobileMenu")
@Api(tags = "物业移动端-物业移动端菜单")
public class EstateMobileMenuController extends BaseController {

    @Autowired
    private EstateMobileMenuService estateMobileMenuService;

    @Autowired
    private EstateMobileRoleMenuService estateMobileRoleMenuService;

    /**
     * 列表
     *
     * @param
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("列表")
    public TableDataInfo list(@RequestBody EstateMobileMenu estateMobileMenu) {
        PageUtils.startPage(estateMobileMenu.getPageNum(), estateMobileMenu.getPageSize());
        List<EstateMobileMenu> list = estateMobileMenuService.getList(estateMobileMenu);
        return getDataTable(list);
    }

    /**
     * 列表
     *
     * @param
     * @return
     */
    @GetMapping("/listMenu")
    public AjaxResult listMenu( EstateMobileMenu estateMobileMenu) {
        List<EstateMobileMenu> list = estateMobileMenuService.getList(estateMobileMenu);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param estateMobileMenu 实体
     * @return 新增结果
     */
    @ApiOperation("新增菜单")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody EstateMobileMenu estateMobileMenu) {

        EstateMobileMenu emm = new EstateMobileMenu();
        emm.setEstateMenuCode(estateMobileMenu.getEstateMenuCode());
        int i1 = estateMobileMenuService.getByNameCount(emm);
        if (i1 > 0) {
            return AjaxResult.error("菜单编号已存在");
        }
        EstateMobileMenu emm2 = new EstateMobileMenu();
        emm2.setEstateMenuName(estateMobileMenu.getEstateMenuName());
        int i2 = estateMobileMenuService.getByNameCount(emm2);
        if (i2 > 0) {
            return AjaxResult.error("菜单名称已存在");
        }

        return toAjax(estateMobileMenuService.insert(estateMobileMenu));
    }

    /**
     * 修改数据
     *
     * @param estateMobileMenu 实体
     * @return 修改结果
     */

    @PutMapping("/update")
    @ApiOperation("修改菜单")
    public AjaxResult update(@RequestBody EstateMobileMenu estateMobileMenu) {
        EstateMobileMenu emm = new EstateMobileMenu();
        emm.setEstateMenuCode(estateMobileMenu.getEstateMenuCode());
        emm.setEstateMenuId(estateMobileMenu.getEstateMenuId());
        int i1 = estateMobileMenuService.getByNameCount(emm);
        if (i1 > 0) {
            return AjaxResult.error("菜单编号已存在");
        }
        EstateMobileMenu emm2 = new EstateMobileMenu();
        emm2.setEstateMenuName(estateMobileMenu.getEstateMenuName());
        emm2.setEstateMenuId(estateMobileMenu.getEstateMenuId());
        int i2 = estateMobileMenuService.getByNameCount(emm2);
        if (i2 > 0) {
            return AjaxResult.error("菜单名称已存在");
        }
        return toAjax(estateMobileMenuService.update(estateMobileMenu));

    }

    /**
     * 删除数据
     *
     * @return 删除菜单
     */
    @ApiOperation("删除菜单")
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestParam("estateMenuId") Long estateMenuId) {
        //校验菜单是否被分配
        Integer count = estateMobileRoleMenuService.getByMenuIdRoleCount(estateMenuId);
        if (count >= 1) {
            return AjaxResult.error("当前菜单被使用,请解绑后删除");
        }
        estateMobileMenuService.delete(estateMenuId);
        return AjaxResult.success();
    }

    /**
     * 通过主键查询单条数据
     */
    @ApiOperation("菜单详情")
    @GetMapping(value = "/{estateMenuId}")
    public AjaxResult getInfo(@PathVariable("estateMenuId") Long estateMenuId) {
        return AjaxResult.success(estateMobileMenuService.getById(estateMenuId));
    }

}
