package com.wr.estate.web.social;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;

import com.wr.estate.service.social.SocialService;
import com.wr.remote.domain.*;
import com.wr.remote.domain.vo.ApiSocialLikeVo;
import com.wr.remote.domain.vo.ApiSocialVo;
import com.wr.remote.govern.autonomy.social.Social;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @Author lvzy
 * @Date 2024/2/28 17:27
 */
@RestController
@RequestMapping("social")
@Api(tags = "物管平台-社交管理")
public class SocialController extends BaseController {

    @Autowired
    private SocialService socialService;


    /**
     * 获取信息管理列表
     *
     * @param social
     * @return
     * @throws ParseException
     */
    @PostMapping("/messageList")
    @ApiOperation("获取社交管理列表")
    public TableDataInfo messageList(@RequestBody Social social) {

        social.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        PageUtils.startPage(social.getPageNum(), social.getPageSize());
        List<Social> list = socialService.getList(social);
        return getDataTable(list);
    }


    /**
     * 获取系统管理信息列表
     *
     * @param social
     * @return
     * @throws ParseException
     */
    @GetMapping("/systemList")
    @ApiOperation("获取系统管理信息列表")
    public TableDataInfo systemList(Social social) throws ParseException {
        startPage();
        List<Social> list = socialService.systemList(social);
        return getDataTable(list);
    }


    /**
     * 新增系统信息
     *
     * @param social
     * @return
     */
    @PostMapping
    @Log(title = "新增", businessType = BusinessType.INSERT)
    @ApiOperation("新增系统信息")
    public AjaxResult addSystemSocial(@Validated @RequestBody Social social) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = socialService.addSystemSocial(social);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }


    /**
     * 删除社交记录
     *
     * @param socialId
     * @return
     */

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @ApiOperation("删除")
    @DeleteMapping("/delMessage/{socialId}")
    public AjaxResult delMessageSocial(@PathVariable Long[] socialId) {
        int i = socialService.delMessageSocial(socialId);
        return toAjax(i);
    }


    /**
     * 删除系统社交记录
     *
     * @param id
     * @return
     */

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @ApiOperation("删除系统社交记录")
    @DeleteMapping("/delSystem/{id}")
    public AjaxResult delSystemSocial(@PathVariable Long[] id) {
        return toAjax(socialService.delSystemSocial(id));
    }


    /**
     * 修改
     *
     * @param social
     * @return
     */

    @Log(title = "修改", businessType = BusinessType.UPDATE)
    @ApiOperation("修改")
    @PutMapping
    public AjaxResult updateSysSocial(@Validated @RequestBody Social social) {
        int i = socialService.updateSysSocial(social);
        return toAjax(i);
    }


    /**
     * 根据id查询社交的详情内容
     *
     * @param socialId
     * @return
     */
    @ApiOperation("根据id查询社交的详情内容")
    @GetMapping(value = {"/", "/{socialId}"})
    public AjaxResult getInfo(@PathVariable(value = "socialId", required = false) Long socialId) {

        Social social = socialService.getSocialById(socialId);
        if (StringUtils.isNotEmpty(social.getSocialUrl())) {
            social.setSocialUrls(Arrays.asList(social.getSocialUrl().split(",")));
        }
        return AjaxResult.success(social);
    }

}
