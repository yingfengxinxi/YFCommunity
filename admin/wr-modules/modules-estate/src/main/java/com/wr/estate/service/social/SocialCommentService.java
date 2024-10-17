package com.wr.estate.service.social;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.govern.autonomy.social.SocialComment;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */

public interface SocialCommentService {


    /**
     * 根据社交表查询总数
     * @param socialId
     * @return
     */
    Long queryLikeAmount(Long socialId);

    /**
     * 新增点赞数
     * @param socialId
     * @return
     */
    Integer addLike(Long socialId);

    /**
     *新增评论
     * @param socialComment
     * @return
     */
    Integer addComment(SocialComment socialComment);

    /**
     * 获取系统管理信息列表
     * @param socialId
     * @return
     */
    List<CommentVo> getCommentList(Long socialId);

    Integer revokeMsg(SocialComment socialComment);
}
