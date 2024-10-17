package com.wr.estate.mapper.featured;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.estate.service.featured.FeaturedCommentService;
import com.wr.remote.govern.autonomy.featured.FeaturedComment;
import com.wr.remote.govern.autonomy.featured.FeaturedLike;
import com.wr.remote.govern.autonomy.social.SocialComment;
import com.wr.remote.govern.autonomy.social.SocialLike;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
public interface FeaturedCommentMapper{



    /**
     * 根据社交表查询总数
     * @param featuredId
     * @return
     */
    Long queryLikeAmount(@Param("featuredId") Long featuredId);

    /**
     *点赞操作
     * @param featuredLike
     * @return
     */
    Integer addLike(FeaturedLike featuredLike);

    /**
     *查询用户类型
     * @param userId
     * @return
     */
    String queryAccountType(Long userId);

    /**
     *新增评论
     * @param featuredComment
     * @return
     */
    Integer addComment(FeaturedComment featuredComment);

    /**
     * 获取系统信息列表
     * @param featuredId
     * @return
     */
    List<CommentVo> getCommentList(@Param("featuredId") Long featuredId);

    /**
     * 查询当前社交信息发布人员类型
     * @param featuredId
     * @return
     */
    String queryFeaturedInfoUserType(@Param("featuredId") Long featuredId);



    /**
     *根据社交id，查询系统信息
     * @param featuredId
     * @return
     */
    List<CommentVo> querySysCommentInfo(@Param("featuredId") Long featuredId);

    /**
     *
     * @param featuredComment
     * @return
     */
    Integer revokeMsg(FeaturedComment featuredComment);

}
