package com.wr.govern.service.autonomy.social;


import com.wr.remote.govern.autonomy.social.Social;
import com.wr.remote.govern.autonomy.social.SocialComment;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;

import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 自治表决接口
 */
public interface CommentService {

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
