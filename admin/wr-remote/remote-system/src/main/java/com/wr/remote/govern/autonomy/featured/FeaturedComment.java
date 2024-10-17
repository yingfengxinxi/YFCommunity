package com.wr.remote.govern.autonomy.featured;

import com.wr.common.core.business.annotation.SensitiveWord;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/2/28 17:31
 */
@Data
public class FeaturedComment extends BaseEntity {


    private static final long serialVersionUID = -6791881878205985793L;
    /**
     * 主键
     */
    private Long commentId;

    /**
     * 特色板块表id，表xccj_govern.bus_featured_section
     */
    private Long featuredId;

    /**
     * 评论人员类型：0=系统，1=业主，2=租客
     */
    private String userType;

    /**
     * 评论人员id，user_type对应表id
     */
    private Long userId;

    /**
     * 评论id，表xccj_govern.bus_social_comment
     */
    private Long replyId;

    //父级id评论表主键(用于二级评论)
    private Long parentCommentId;

    /**
     * 评论内容
     */
    @SensitiveWord
    private String content;


    /**
     * '删除标记：0=未删除，1=已删除'
     */
    private String delFlag;
    //是否可以撤回0=能撤回1=不能撤回
    private String flag;
}
