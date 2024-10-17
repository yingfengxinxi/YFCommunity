package com.wr.system.web;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.annotation.InnerAuth;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.system.service.GovernLoginService;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysMenu;
import com.wr.remote.domain.SysUser;
import com.wr.remote.model.LoginUser;
import com.wr.remote.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:39:00
 * @Desc: 登录记录
 */
@RestController
@RequestMapping("/govern_login")
public class GovernLoginController extends BaseController {
    @Autowired
    private GovernLoginService governLoginService;

    @InnerAuth(userType = UserConstants.USER_GOVERN)
    @PostMapping
    public AjaxResult add(@RequestBody SysLogininfor logininfor) {
        return toAjax(governLoginService.insertLoginInfo(logininfor));
    }

    /**
     * 获取当前用户信息
     */
    @InnerAuth(userType = UserConstants.USER_GOVERN)
    @GetMapping("/user/{username}")
    public R<LoginUser> info(@PathVariable("username") String username) {
        SysUser sysUser = governLoginService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = governLoginService.getRolePermission(sysUser);
        // 权限集合
        Set<String> permissions = governLoginService.getMenuPermission(sysUser);
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = governLoginService.selectUserById(SecurityUtils.getUserId());
        // 角色集合
        Set<String> roles = governLoginService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = governLoginService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        List<SysMenu> menus = governLoginService.selectMenuTreeByUserId();
        //构建前端路由所需要的菜单
        return AjaxResult.success(MenuUtil.buildMenus(menus));
    }
}
