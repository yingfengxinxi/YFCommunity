package com.wr.api.owner.service.estateapi.featured;

import com.wr.remote.govern.autonomy.featured.FeaturedComment;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
public interface FeaturedCommentService {


    /**
     * 根据社交表查询总数
     * @param featuredId
     * @return
     */
    Long queryLikeAmount(Long featuredId);

    /**
     * 新增点赞数
     * @param featuredId
     * @return
     */
    Integer addLike(Long featuredId);

    /**
     *新增评论
     * @param featuredComment
     * @return
     */
    Integer addComment(FeaturedComment featuredComment);

    /**
     * 获取系统管理信息列表
     * @param featuredId
     * @return
     */
    List<CommentVo> getCommentList(Long featuredId);

    Integer revokeMsg(FeaturedComment featuredComment);
}
