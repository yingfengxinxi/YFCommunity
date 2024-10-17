package com.wr.estate.web.featured;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.featured.FeaturedSectionTypeService;
import com.wr.remote.govern.autonomy.featured.FeaturedSectionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 11:15
 */
@RestController
@RequestMapping("featuredSectionType")
@Api(tags = "物管平台-特色板块类型")
public class FeaturedSectionTypeController extends BaseController {

    @Autowired
    private FeaturedSectionTypeService featuredSectionTypeService;

    /**
     * 分页
     *
     * @param featuredSectionType 查询对象
     * @return
     */
    @PostMapping("/getListPage")
    @ApiOperation("特色板块分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getListPage(@RequestBody FeaturedSectionType featuredSectionType) {
        LambdaQueryWrapper<FeaturedSectionType> queryWrapper = new LambdaQueryWrapper<>();


        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        queryWrapper.eq(FeaturedSectionType::getEstateId, estateId);
        if (StringUtils.isNotEmpty(featuredSectionType.getName())) {
            queryWrapper.like(FeaturedSectionType::getName, featuredSectionType.getName());
        }
        if (StringUtils.isNotEmpty(featuredSectionType.getStatus())) {
            queryWrapper.eq(FeaturedSectionType::getStatus, featuredSectionType.getStatus());
        }

        if (featuredSectionType.getCommunityId() != null) {
            queryWrapper.eq(FeaturedSectionType::getCommunityId, featuredSectionType.getCommunityId());
        }


        queryWrapper.orderByAsc(FeaturedSectionType::getSort);
        PageUtils.startPage(featuredSectionType.getPageNum(), featuredSectionType.getPageSize());
        List<FeaturedSectionType> rescueVOList = featuredSectionTypeService.list(queryWrapper);

        return getDataTable(rescueVOList);
    }


    /**
     * 分页
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("特色板块列表")
    public AjaxResult list() {
        LambdaQueryWrapper<FeaturedSectionType> queryWrapper = new LambdaQueryWrapper<>();

        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        queryWrapper.eq(FeaturedSectionType::getEstateId, estateId);
        queryWrapper.eq(FeaturedSectionType::getStatus, "0");
        queryWrapper.orderByAsc(FeaturedSectionType::getSort);
        List<FeaturedSectionType> rescueVOList = featuredSectionTypeService.list(queryWrapper);
        return AjaxResult.success(rescueVOList);
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("特色板块详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {

        return AjaxResult.success(featuredSectionTypeService.getById(id));
    }

    /**
     * @param featuredSectionType
     * @return
     */
    @ApiOperation("特色板块增加")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody FeaturedSectionType featuredSectionType) {

        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        featuredSectionType.setEstateId(estateId);
        featuredSectionType.setCreateBy(SecurityUtils.getUsername());
        featuredSectionType.setCreateTime(new Date());
        featuredSectionType.setStatus("0");
        featuredSectionType.setDelFlag("0");
        LambdaQueryWrapper<FeaturedSectionType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FeaturedSectionType::getDelFlag, "0");
        queryWrapper.eq(FeaturedSectionType::getCommunityId, featuredSectionType.getCommunityId());
        queryWrapper.eq(FeaturedSectionType::getName, featuredSectionType.getName());
        int size = featuredSectionTypeService.list(queryWrapper).size();
        if (size >= 1) {
            return AjaxResult.error(featuredSectionType.getName() + "名称与存在，请勿重复添加");
        }
        featuredSectionTypeService.save(featuredSectionType);
        return AjaxResult.success();

    }

    /**
     * @param featuredSectionType
     * @return
     */
    @ApiOperation("特色板块修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody FeaturedSectionType featuredSectionType) {
        featuredSectionType.setUpdateBy(SecurityUtils.getUsername());
        featuredSectionType.setUpdateTime(new Date());

        LambdaQueryWrapper<FeaturedSectionType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FeaturedSectionType::getDelFlag, "0");
        queryWrapper.eq(FeaturedSectionType::getCommunityId, featuredSectionType.getCommunityId());
        queryWrapper.eq(FeaturedSectionType::getName, featuredSectionType.getName());
        FeaturedSectionType fs = featuredSectionTypeService.getOne(queryWrapper);

        if (fs != null) {
            if (!StringUtils.equals(featuredSectionType.getId(), fs.getId())) {
                return AjaxResult.error(featuredSectionType.getName() + "名称与存在，请勿重复添加");
            }
        }
        featuredSectionTypeService.updateById(featuredSectionType);
        return AjaxResult.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("特色板块删除")
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") String id) {

        featuredSectionTypeService.removeById(id);
        return AjaxResult.success();
    }
}
