package com.wr.estate.web.communityInformation;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.communityInformation.CommunityInformationService;
import com.wr.remote.domain.ApiMessage;
import com.wr.remote.domain.CommunityInformation;
import com.wr.remote.domain.vo.ApiMessageVo;
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
 * @Date 2024/1/25 14:10
 */
@RestController
@RequestMapping("communityInformation")
@Api(tags = "物管平台-社区资讯")
public class CommunityInformationController extends BaseController {

    @Autowired
    private CommunityInformationService communityInformationService;


    /**
     * @param communityInformation
     * @return
     */
    @PostMapping("list")
    @ApiOperation("社区资讯列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msgTitle", value = "名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(@RequestBody CommunityInformation communityInformation) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        LambdaQueryWrapper<CommunityInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityInformation::getEstateId, estateId);
        queryWrapper.eq(CommunityInformation::getDelFlag, "0");
        if (communityInformation.getCommunityId() != null) {
            queryWrapper.eq(CommunityInformation::getCommunityId, communityInformation.getCommunityId());
        }
        if (StringUtils.isNotEmpty(communityInformation.getMsgTitle())) {
            queryWrapper.like(CommunityInformation::getMsgTitle, communityInformation.getMsgTitle());
        }
        queryWrapper.orderByDesc(CommunityInformation::getTop);
        queryWrapper.orderByAsc(CommunityInformation::getSortNumber);
        queryWrapper.orderByDesc(CommunityInformation::getCreateTime);
        PageUtils.startPage(communityInformation.getPageNum(), communityInformation.getPageSize());
        return getDataTable(communityInformationService.list(queryWrapper));
    }

    /**
     * 新增
     *
     * @param communityInformation
     * @return
     */
    @ApiOperation("社区资讯增加")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody CommunityInformation communityInformation) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        communityInformation.setEstateId(estateId);
        communityInformation.setTop("0");

        //判断是否存在置顶数据
        LambdaQueryWrapper<CommunityInformation> queryWrapper = new LambdaQueryWrapper<CommunityInformation>();
        queryWrapper.eq(CommunityInformation::getCommunityId, communityInformation.getCommunityId());
        queryWrapper.eq(CommunityInformation::getTop, "0");
        queryWrapper.eq(CommunityInformation::getDelFlag, "0");
        List<CommunityInformation> communityInformationList = communityInformationService.list(queryWrapper);
        if (CollectionUtils.isEmpty(communityInformationList)) {
            communityInformation.setTop("1");
        }
        communityInformation.setCreateBy(SecurityUtils.getUsername());
        communityInformation.setCreateTime(new Date());
        communityInformation.setDelFlag("0");
        communityInformationService.save(communityInformation);
        return AjaxResult.success();
    }

    /**
     * 修改
     *
     * @param communityInformation
     * @return
     */
    @ApiOperation("社区资讯修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody CommunityInformation communityInformation) {
        communityInformation.setUpdateBy(SecurityUtils.getUsername());
        communityInformation.setUpdateTime(new Date());
        communityInformationService.updateById(communityInformation);
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("社区资讯详情")
    @GetMapping("/detail")
    public AjaxResult detail(@RequestParam("id") String id) {
        return AjaxResult.success(communityInformationService.getById(id));
    }

    /**
     * @param id
     * @param top
     * @return
     */
    @ApiOperation("社区资讯删除")
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") String id, @RequestParam("top") String top) {
        CommunityInformation detail = communityInformationService.getById(id);
        if (top.equals("1")) {
            LambdaQueryWrapper<CommunityInformation> queryWrapper = new LambdaQueryWrapper<CommunityInformation>();
            queryWrapper.eq(CommunityInformation::getCommunityId, detail.getCommunityId());
            queryWrapper.apply(" id!='" + detail.getId() + "'");
            queryWrapper.orderByDesc(CommunityInformation::getTop);
            queryWrapper.orderByAsc(CommunityInformation::getSortNumber);
            queryWrapper.orderByDesc(CommunityInformation::getCreateTime);
            List<CommunityInformation> communityInformationList = communityInformationService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(communityInformationList)) {
                CommunityInformation message1 = communityInformationList.get(0);
                message1.setTop("1");
                message1.setUpdateTime(new Date());
                communityInformationService.updateById(message1);
            }
        }
        communityInformationService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("社区资讯置顶")
    @GetMapping("updateTop")
    public AjaxResult updateTop(@RequestParam("id") String id) {

        CommunityInformation communityInformation = communityInformationService.getById(id);


        LambdaQueryWrapper<CommunityInformation> queryWrapper = new LambdaQueryWrapper<CommunityInformation>();
        queryWrapper.eq(CommunityInformation::getCommunityId, communityInformation.getCommunityId());
        queryWrapper.eq(CommunityInformation::getTop, "1");
        queryWrapper.orderByDesc(CommunityInformation::getTop);
        queryWrapper.orderByAsc(CommunityInformation::getSortNumber);
        queryWrapper.orderByDesc(CommunityInformation::getCreateTime);
        List<CommunityInformation> communityInformationList = communityInformationService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(communityInformationList)) {
            CommunityInformation ci = communityInformationList.get(0);
            //旧置顶数据
            ci.setTop("0");
            communityInformationService.updateById(ci);
        }

        //新置顶数据
        communityInformation.setTop("1");
        communityInformation.setUpdateTime(new Date());
        communityInformationService.updateById(communityInformation);
        return AjaxResult.success();
    }
}
