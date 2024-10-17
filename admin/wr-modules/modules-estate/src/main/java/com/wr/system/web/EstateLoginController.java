package com.wr.system.web;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.annotation.InnerAuth;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.vo.RouterVo;
import com.wr.system.service.EstateLoginService;
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
@RequestMapping("/estate_login")
public class EstateLoginController extends BaseController {
    @Autowired
    private EstateLoginService estateLoginService;

    @InnerAuth(userType = UserConstants.USER_ESTATE)
    @PostMapping
    public AjaxResult add(@RequestBody SysLogininfor logininfor) {
        return toAjax(estateLoginService.insertLoginInfo(logininfor));
    }

    /**
     * 获取当前用户信息
     */
    @InnerAuth(userType = UserConstants.USER_ESTATE)
    @GetMapping("/user/{username}")
    public R<LoginUser> info(@PathVariable("username") String username) {
        SysUser sysUser = estateLoginService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = estateLoginService.getRolePermission(sysUser);
        // 权限集合
        Set<String> permissions = estateLoginService.getMenuPermission(sysUser);
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
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        logger.info("物业端==>getInfo：{}", System.currentTimeMillis());
        SysUser user = estateLoginService.selectUserById(SecurityUtils.getUserId());
        // 角色集合
        Set<String> roles = estateLoginService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = estateLoginService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        logger.info("物业端==>getInfo：{}", System.currentTimeMillis());
        return ajax;
    }

    /**
     * 获取路由信息
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public AjaxResult getRouters() {
        logger.info("物业端==>getRouters：{}", System.currentTimeMillis());
        List<SysMenu> menus = estateLoginService.selectMenuTreeByUserId();
        //构建前端路由所需要的菜单
        List<RouterVo> routerVos = MenuUtil.buildMenus(menus);
        logger.info("物业端==>getRouters：{}", System.currentTimeMillis());
        return AjaxResult.success(routerVos);
    }
}
