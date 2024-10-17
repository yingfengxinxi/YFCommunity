package com.wr.estate.mapper.social;

import com.wr.remote.domain.ApiSocialComment;
import com.wr.remote.govern.autonomy.social.SocialComment;
import com.wr.remote.govern.autonomy.social.SocialLike;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;

import java.util.List;

/**
 * 社交评论记录表
 * @Author: lvzy
 * @Date: 2022-11-16 15:58:38
 * @Desc:社交评论记录表
 */
public interface SocialCommentMapper {

    /**
     * 根据社交表查询总数
     * @param socialId
     * @return
     */
    Long queryLikeAmount(Long socialId);

    /**
     *点赞操作
     * @param socialLike
     * @return
     */
    Integer addLike(SocialLike socialLike);

    /**
     *查询用户类型
     * @param userId
     * @return
     */
    String queryAccountType(Long userId);

    /**
     *新增评论
     * @param socialComment
     * @return
     */
    Integer addComment(SocialComment socialComment);

    /**
     * 获取系统信息列表
     * @param socialId
     * @return
     */
    List<CommentVo> getCommentList(Long socialId);

    /**
     * 查询当前社交信息发布人员类型
     * @param socialId
     * @return
     */
    String querySocialInfoUserType(Long socialId);



    /**
     *根据社交id，查询系统信息
     * @param socialId
     * @return
     */
    List<CommentVo> querySysCommentInfo(Long socialId);

    /**
     *
     * @param socialComment
     * @return
     */
    Integer revokeMsg(SocialComment socialComment);
}