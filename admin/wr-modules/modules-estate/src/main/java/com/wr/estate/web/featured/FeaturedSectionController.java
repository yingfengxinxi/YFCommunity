package com.wr.estate.web.featured;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.featured.FeaturedSectionService;
import com.wr.remote.govern.autonomy.featured.FeaturedSection;
import com.wr.remote.govern.autonomy.social.Social;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 17:27
 */
@RestController
@RequestMapping("featuredSection")
@Api(tags = "物管平台-特色板块")
public class FeaturedSectionController extends BaseController {

    @Autowired
    private FeaturedSectionService featuredSectionService;


    /**
     * 获取信息管理列表
     *
     * @param featuredSection
     * @return
     * @throws ParseException
     */
    @PostMapping("/messageList")
    @ApiOperation("获取特色板块管理列表")
    public TableDataInfo messageList(@RequestBody FeaturedSection featuredSection) {

        featuredSection.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        PageUtils.startPage(featuredSection.getPageNum(), featuredSection.getPageSize());
        List<FeaturedSection> list = featuredSectionService.getList(featuredSection);
        return getDataTable(list);
    }


    /**
     * 获取系统管理信息列表
     *
     * @param featuredSection
     * @return
     * @throws ParseException
     */
    @GetMapping("/systemList")
    @ApiOperation("获取系统管理信息列表")
    public TableDataInfo systemList(FeaturedSection featuredSection) throws ParseException {
        startPage();
        List<FeaturedSection> list = featuredSectionService.systemList(featuredSection);
        return getDataTable(list);
    }


    /**
     * 新增系统信息
     *
     * @param featuredSection
     * @return
     */
    @PostMapping
    @Log(title = "新增", businessType = BusinessType.INSERT)
    @ApiOperation("新增系统信息")
    public AjaxResult addSystemSocial(@Validated @RequestBody FeaturedSection featuredSection) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = featuredSectionService.addSystemFeatured(featuredSection);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }


    /**
     * 删除社交记录
     *
     * @param featuredId
     * @return
     */

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @ApiOperation("删除")
    @DeleteMapping("/delMessage/{featuredId}")
    public AjaxResult delMessageSocial(@PathVariable Long[] featuredId) {
        int i = featuredSectionService.delMessageFeatured(featuredId);
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
    public AjaxResult delSystemFeatured(@PathVariable Long[] id) {
        return toAjax(featuredSectionService.delSystemFeatured(id));
    }


    /**
     * 修改
     *
     * @param featuredSection
     * @return
     */

    @Log(title = "修改", businessType = BusinessType.UPDATE)
    @ApiOperation("修改")
    @PutMapping
    public AjaxResult updateSysSocial(@Validated @RequestBody FeaturedSection featuredSection) {
        int i = featuredSectionService.updateSysFeatured(featuredSection);
        return toAjax(i);
    }


    /**
     * 根据id查询社交的详情内容
     *
     * @param featuredId
     * @return
     */
    @ApiOperation("根据id查询社交的详情内容")
    @GetMapping(value = {"/", "/{featuredId}"})
    public AjaxResult getInfo(@PathVariable(value = "featuredId", required = false) Long featuredId) {

        FeaturedSection featuredSection = featuredSectionService.getFeaturedById(featuredId);
        if (StringUtils.isNotEmpty(featuredSection.getFeaturedUrl())) {
            featuredSection.setFeaturedUrls(Arrays.asList(featuredSection.getFeaturedUrl().split(",")));
        }
        return AjaxResult.success(featuredSection);
    }


}
