package com.wr.system.business.controller;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SysUser;
import com.wr.system.business.domain.RoleVerVO;
import com.wr.system.business.domain.SysUserVO;
import com.wr.system.business.service.PropUserService;
import com.wr.system.business.service.RoleVerService;
import com.wr.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 16:42:07
 * @Desc: 物业账号管理控制层
 */
@RestController
@RequestMapping("/prop_user")
public class PropUserController extends BaseController {
    @Autowired
    private PropUserService propUserService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private RoleVerService roleVerService;

    /**
     * 获取用户列表
     */
    @RequiresPermissions("system:prop_user:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUserVO> list = propUserService.selectPropUserList(user);
        return getDataTable(list);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @RequiresPermissions("system:prop_user:query")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        List<RoleVerVO> roles = roleVerService.selectProRoleList(new RoleVerVO());
        ajax.put("roles", roles);
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, propUserService.selectPropUserById(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @RequiresPermissions("system:prop_user:add")
    @Log(title = "物业账号管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(propUserService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增账号'" + user.getUserName() + "'失败，账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(propUserService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增账号'" + user.getUserName() + "'失败，手机号码已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(propUserService.insertPropUser(user));
    }


    /**
     * 修改用户
     */
    @RequiresPermissions("system:prop_user:edit")
    @Log(title = "物业账号管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(propUserService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改账号'" + user.getUserName() + "'失败，手机号码已存在");
        }
        user.setUpdateBy(SecurityUtils.getUsername());
        if(StringUtils.isNotEmpty(user.getPassword())){
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        }
        return toAjax(propUserService.updatePropUser(user));
    }

    /**
     * 删除用户
     */
    @RequiresPermissions("system:prop_user:remove")
    @Log(title = "物业账号管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {

        return toAjax(propUserService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @RequiresPermissions("system:prop_user:resetPwd")
    @Log(title = "物业账号管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(propUserService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("system:prop_user:edit")
    @Log(title = "物业账号管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(propUserService.updateUserStatus(user));
    }
}
