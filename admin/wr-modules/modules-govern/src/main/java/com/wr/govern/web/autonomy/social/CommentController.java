package com.wr.govern.web.autonomy.social;

import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.autonomy.social.CommentService;
import com.wr.govern.service.autonomy.social.SocialService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.social.Social;
import com.wr.remote.govern.autonomy.social.SocialComment;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;
import com.wr.remote.system.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 社交评论记录表controller
 */
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private RemoteFileService remoteFileService;

    @Autowired
    private CommentService commentService;




    /**
     *根据id获取点赞数量
     * @param socialId
     * @return
     */
    @GetMapping(value = { "/", "/{socialId}" })
    public AjaxResult queryLikeAmount(@PathVariable(value = "socialId", required = false) Long socialId) {
        AjaxResult ajax = AjaxResult.success();
        //1.不是空时，发送ajax
        if (StringUtils.isNotNull(socialId)){
            //2.查询social表格中信息
            Long num = commentService.queryLikeAmount(socialId);
            ajax.put("likeAmount",num );
        }
        return ajax;
    }



    /**
     * 通过socialId新增点赞记录
     * @param socialId
     * @return
     */
    @PostMapping
    @Log(title = "新增点赞", businessType = BusinessType.INSERT)
    @RequiresPermissions("autonomy:social:addLike")
    public AjaxResult addLike(@Validated @RequestBody Long socialId) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = commentService.addLike(socialId);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }




    /**
     *新增评论
     * @param socialComment
     * @return
     */
    @PostMapping("/addComment")
    @RequiresPermissions("autonomy:social:addComment")
    @Log(title = "新增评论", businessType = BusinessType.INSERT)
    public AjaxResult addComment(@Validated @RequestBody SocialComment socialComment) {
        Integer i = commentService.addComment(socialComment);
        return i > 0 ? AjaxResult.success("评论成功") : AjaxResult.error("评论失败");
    }





    /**
     * 根据社交id查询评论
     * @param socialId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/commentList/{socialId}")
    @RequiresPermissions("autonomy:social:query")
    public AjaxResult getCommentList(@PathVariable("socialId") Long socialId) {
        AjaxResult ajax = AjaxResult.success();
        List<CommentVo> commentList = commentService.getCommentList(socialId);
        ajax.put(AjaxResult.DATA_TAG,commentList);
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        ajax.put("loginName",sysUser.getUserName());
        return ajax;
    }






    /**
     * 撤回功能
     * @param socialComment
     * @return
     */
    @PutMapping("/revokeMsg")
    @Log(title = "撤回", businessType = BusinessType.UPDATE)
    public AjaxResult revokeMsg(@RequestBody  SocialComment socialComment) {
      Integer i =  commentService.revokeMsg(socialComment);
        return i>0 ? AjaxResult.success("撤回成功") : AjaxResult.error("撤回失败，已超时");
    }




















}
