package com.wr.estate.web.info;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.info.CommunityNoticeService;
import com.wr.remote.estate.info.CommunityNotice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 16:34:39
 * @Desc:
 */
@RestController
@RequestMapping("/notice")
@Api(tags = "物管平台-通知公告")
public class CommunityNoticeController extends BaseController {

    @Autowired
    private CommunityNoticeService noticeService;

    /**
     * 社区头条列表
     */
    @PostMapping("/list")
    @ApiOperation("通知公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeTitle", value = "名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "noticeStatus", value = "状态0=发布1=撤回", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(@RequestBody CommunityNotice notice) {

        notice.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        PageUtils.startPage(notice.getPageNum(), notice.getPageSize());
        List<CommunityNotice> list = noticeService.selectList(notice);
        return getDataTable(list);
    }

    /**
     * 根据编号获取详细信息
     */
    @ApiOperation("通知公告详情")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
//      headlineService.updateReadNum(headlineId);
        CommunityNotice notice = noticeService.selectNoticeById(noticeId);
        return AjaxResult.success(notice);
    }

    /**
     * 添加通知公告
     */
    @ApiOperation("添加通知公告")
    @Log(title = "添加通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody CommunityNotice notice) {
        notice.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        Integer checkTitleUnique = noticeService.checkTitleUnique(notice.getEstateId(), null, notice.getNoticeTitle(), notice.getCommunityId());
        if (checkTitleUnique >= 1) {
            return AjaxResult.error("添加通知公告失败,标题重复");
        }
        notice.setCreateBy(SecurityUtils.getUsername());
        notice.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        int row = noticeService.insertNotice(notice);
        return toAjax(row);
    }

    /**
     * 发布通知公告
     */
    @ApiOperation("发布通知公告")
    @GetMapping(value = "/up/{noticeId}")
    public AjaxResult up(@PathVariable Long noticeId) {
        int row = noticeService.updateStatus(noticeId);
        return toAjax(row);
    }

    /**
     * 撤回通知公告
     */
    @ApiOperation("撤回通知公告")
    @GetMapping(value = "/back/{noticeId}")
    public AjaxResult back(@PathVariable Long noticeId) {
        int row = noticeService.updateStatus2(noticeId);
        return toAjax(row);
    }

    /**
     * 修改通知公告
     */
    @ApiOperation("修改通知公告")
    @Log(title = "修改通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody CommunityNotice notice) {
        notice.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        Integer checkTitleUnique = noticeService.checkTitleUnique(notice.getEstateId(), notice.getNoticeId(), notice.getNoticeTitle(), notice.getCommunityId());
        if (checkTitleUnique >= 1) {
            return AjaxResult.error("修改通知公告失败,标题重复");
        }
        notice.setUpdateBy(SecurityUtils.getUsername());
        int row = noticeService.updateNotice(notice);
        return toAjax(row);
    }

    /**
     * 删除通知公告
     *
     * @param noticeIds 主键
     * @return
     */
    @ApiOperation("删除通知公告")
    @RequiresPermissions("estate:notice:remove")
    @Log(title = "删除通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        int row = noticeService.deleteNoticeByIds(noticeIds);
        return toAjax(row);
    }
}
