package com.wr.api.estate.web.application.manage;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.manage.StaffService;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.EstateMobileMenu;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.domain.SysFile;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.system.RemoteFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 09:51:03
 * @Desc:
 */
@RestController
@RequestMapping("/v2/staff")
@Api(tags = "物业移动端-个人资料")
public class StaffController extends BaseController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private LoginService loginService;


    /**
     * 详情
     *
     * @param userId
     * @return
     */
    @ApiOperation("个人资料详情")
    @GetMapping("{userId}")
    public AjaxResult getStaffById(@PathVariable("userId") Long userId) {

        return AjaxResult.success(staffService.getStaffById(userId));
    }

    @ApiOperation("个人资料修改")
    @PostMapping("/update")
    public AjaxResult updateStaff(@RequestBody Staff staff) {


        String username = SecurityUtils.getUsername();
        staff.setUpdateBy(username);
        staffService.updateStaff(staff);
        return AjaxResult.success();


    }


    /**
     * 修改员工密码
     */
    @Log(title = "员工密码修改", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPass")
    public AjaxResult resetPass(@RequestBody Staff staff) {
        staff.setPassword(staff.getPassword());
        staffService.resetPass(staff);
        return AjaxResult.success();
    }

    /**
     * 修改员工密码
     */
    @ApiOperation("员工头像修改")
    @Log(title = "员工头像修改", businessType = BusinessType.UPDATE)
    @PostMapping("/resetAvatar")
    public AjaxResult resetAvatar(@RequestBody Staff staff) {
        staffService.resetAvatar(staff);
        return AjaxResult.success();
    }

    /**
     * @param userId
     * @return
     */
    @ApiOperation("工单权限")
    @GetMapping("getUserIdAuth")
    public AjaxResult getUserIdAuth(@RequestParam("userId") Long userId) {

        List<SysDictData> dictDataList = Lists.newArrayList();
        List<EstateMobileMenu> menuList = loginService.getByUserIdMenuList(userId);
        if (CollectionUtils.isNotEmpty(menuList)) {
            List<SysDictData> orderList = DictUtils.getDictCache("order_list");
            if (CollectionUtils.isNotEmpty(orderList)) {
                for (SysDictData sysDictData : orderList) {
                    List<EstateMobileMenu> mobileMenuList = menuList.stream().filter(menu -> menu.getEstateMenuCode().equals(sysDictData.getDictValue())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(mobileMenuList)) {
                        System.out.println("sysDictData>>"+sysDictData.toString());
                        dictDataList.add(sysDictData);
                    }
                }
            }
        }
        return AjaxResult.success(dictDataList);
    }
}
