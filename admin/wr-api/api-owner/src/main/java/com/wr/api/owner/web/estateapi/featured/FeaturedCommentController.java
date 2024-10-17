package com.wr.api.owner.web.estateapi.featured;

import com.wr.api.owner.service.estateapi.featured.FeaturedCommentService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.featured.FeaturedComment;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 17:27
 */
@RestController
@RequestMapping("/estateapi/featuredComment")
@Api(tags = "业主端-特色板块管理回复")
public class FeaturedCommentController extends BaseController {

    @Autowired
    private FeaturedCommentService featuredCommentService;

    /**
     * 根据id获取点赞数量
     *
     * @param featuredId
     * @return
     */
    @GetMapping(value = {"/", "/{featuredId}"})
    public AjaxResult queryLikeAmount(@PathVariable(value = "featuredId", required = false) Long featuredId) {
        AjaxResult ajax = AjaxResult.success();
        //1.不是空时，发送ajax
        if (StringUtils.isNotNull(featuredId)) {
            //2.查询social表格中信息
            Long num = featuredCommentService.queryLikeAmount(featuredId);
            ajax.put("likeAmount", num);
        }
        return ajax;
    }


    /**
     * 通过socialId新增点赞记录
     *
     * @param featuredId
     * @return
     */
    @PostMapping
    @Log(title = "新增点赞", businessType = BusinessType.INSERT)
    public AjaxResult addLike(@Validated @RequestBody Long featuredId) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = featuredCommentService.addLike(featuredId);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }


    /**
     * 新增评论
     *
     * @param featuredComment
     * @return
     */
    @PostMapping("/addComment")
    @Log(title = "新增评论", businessType = BusinessType.INSERT)
    public AjaxResult addComment(@Validated @RequestBody FeaturedComment featuredComment) {
        Integer i = featuredCommentService.addComment(featuredComment);
        return i > 0 ? AjaxResult.success("评论成功") : AjaxResult.error("评论失败");
    }


    /**
     * 根据社交id查询评论
     *
     * @param featuredId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("根据社交id查询评论")
    @GetMapping("/commentList/{featuredId}")
    public AjaxResult getCommentList(@PathVariable("featuredId") Long featuredId) {
        AjaxResult ajax = AjaxResult.success();
        List<CommentVo> commentList = featuredCommentService.getCommentList(featuredId);
        ajax.put(AjaxResult.DATA_TAG, commentList);
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        ajax.put("loginName", sysUser.getUserName());
        return ajax;
    }


    /**
     * 撤回功能
     *
     * @param featuredComment
     * @return
     */
    @PostMapping("/revokeMsg")
    @Log(title = "撤回", businessType = BusinessType.UPDATE)
    public AjaxResult revokeMsg(@RequestBody FeaturedComment featuredComment) {
        Integer i = featuredCommentService.revokeMsg(featuredComment);
        return i > 0 ? AjaxResult.success("撤回成功") : AjaxResult.error("撤回失败，已超时");
    }
}
