package com.wr.estate.web.estate;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.estate.EstateMobileRoleMenuService;
import com.wr.remote.domain.EstateMobileRoleMenu;
import com.wr.remote.domain.EstateRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端角色菜单中间表
 */
@RestController
@RequestMapping("estateMobileRoleMenu")
@Api(tags = "物业移动端-物业移动端角色菜单中间表")
public class EstateMobileRoleMenuController extends BaseController {

    @Autowired
    private EstateMobileRoleMenuService estateMobileRoleMenuService;


    /**
     * 列表
     *
     * @param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("列表")
    public AjaxResult list(@RequestParam("estateRoleId") Long estateRoleId) {

        return AjaxResult.success(estateMobileRoleMenuService.getByRoleIdList(estateRoleId));
    }

    /**
     * 新增数据
     *
     * @param estateMobileRoleMenuList 实体
     * @return 新增结果
     */
    @ApiOperation("新增")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody List<EstateMobileRoleMenu> estateMobileRoleMenuList) {
        estateMobileRoleMenuService.insert(estateMobileRoleMenuList);
        return AjaxResult.success();
    }
}