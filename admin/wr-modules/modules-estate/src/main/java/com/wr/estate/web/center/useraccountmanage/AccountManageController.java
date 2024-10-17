package com.wr.estate.web.center.useraccountmanage;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.AccountVo;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-11 13:32:51
 * @Desc:
 */
@RestController
@RequestMapping("accountManage")
@Api(tags = "物管平台-账号人员管理")
public class AccountManageController extends BaseController {

    @Resource
    private AccountManageService accountManageService;

    @GetMapping("query")
    @ApiOperation("账号人员管理列表")
    @RequiresPermissions("center:accountManage:query")
    public TableDataInfo query(AccountVo accountVo){
        startPage();
        return getDataTable(accountManageService.queryAccountListByCommunityId(accountVo));
    }
    @RequiresPermissions("center:accountManage:updatePwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @ApiOperation("重置密码")
    @PostMapping("updatePwd")
    public AjaxResult updatePwd(@RequestBody Map<String,Object> params){
        Long id =Long.valueOf((String) params.get("id"));
        //原密码
        String pwd = accountManageService.getAccountPwd(id);
        //密码对比
/*
        if (!SecurityUtils.matchesPassword((String) params.get("oldPwd"),pwd)){

            return AjaxResult.error("原密码输入错误!!");
        }
*/
        accountManageService.updateAccountPwd(id,SecurityUtils.encryptPassword((String) params.get("newPwd")));
        return AjaxResult.success();
    }

    @ApiOperation("修改是否是业委会成员")
    @GetMapping("updateIsPropertyCouncil")
    public AjaxResult updateIsPropertyCouncil(@RequestParam("accountId")Long accountId,@RequestParam("isPropertyCouncil")String isPropertyCouncil){

        accountManageService.updateIsPropertyCouncil(accountId,isPropertyCouncil);

        return AjaxResult.success();
    }
}
