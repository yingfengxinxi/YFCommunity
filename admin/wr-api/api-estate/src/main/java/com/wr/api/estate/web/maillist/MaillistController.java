package com.wr.api.estate.web.maillist;

import com.wr.api.estate.entity.maillist.MaillistEntity;
import com.wr.api.estate.entity.maillist.UserEntity;
import com.wr.api.estate.entity.maillist.ContactEntity;
import com.wr.api.estate.entity.maillist.OwnerEntity;
import com.wr.api.estate.service.maillist.MaillistService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: EnterpriseController
 * @History:
 */

@RestController
@RequestMapping("/v2/maillist")
@Api(tags = "物业移动端-通讯录")
public class MaillistController extends BaseController {

    @Autowired
    private MaillistService maillistService;

    /**
     * 企业通讯录（物业端）
     *
     * @param userEntity
     * @return
     */
    @GetMapping("/enterprise")
    @ApiOperation("企业通讯录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchValue", value = "关键字（姓名/手机号）", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "当登录用id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "estateId", value = "物业id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo selectRent(UserEntity userEntity) {
        startPage();
        return getDataTable(maillistService.getEnterprise(userEntity));
    }

    /**
     * 常用电话
     *
     * @param contactEntity
     * @return
     */
    @GetMapping("/contact")
    @ApiOperation("常用电话列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchValue", value = "关键字（姓名/手机号）", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "estateId", value = "物业id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo contact(ContactEntity contactEntity) {
        startPage();
        return getDataTable(maillistService.contact(contactEntity));
    }

    /**
     * 业主电话（物业端）
     *
     * @param owner
     * @return
     */
    @GetMapping("/owner")
    @ApiOperation("业主电话列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchValue", value = "关键字（姓名/手机号）", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "estateId", value = "物业id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public AjaxResult owner(OwnerEntity owner) {
        return AjaxResult.success(maillistService.getowner(owner));
    }

    /**
     * 查看收藏的联系方式
     *
     * @param maillistEntity
     * @return
     */
    @GetMapping("/collect")
    @ApiOperation("查看收藏的联系方式")
    public AjaxResult collect(MaillistEntity maillistEntity) {
        return AjaxResult.success(maillistService.getCollect(maillistEntity));
    }

    /**
     * 添加收藏
     *
     * @param maillistEntity
     * @return
     */
    @ApiOperation("添加收藏")
    @PostMapping("/collect/add")
    @AuthRequired
    public AjaxResult collectAdd(@RequestBody MaillistEntity maillistEntity) {
        return AjaxResult.success(maillistService.collectAdd(maillistEntity));
    }

    /**
     * 取消收藏
     *
     * @param maillistEntity
     * @return
     */
    @ApiOperation("取消收藏")
    @PostMapping("/collect/delete")
    @AuthRequired
    public AjaxResult collectDelete(@RequestBody MaillistEntity maillistEntity) {
        return AjaxResult.success(maillistService.collectDelete(maillistEntity));
    }
}
