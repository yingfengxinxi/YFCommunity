package com.wr.remote.govern.autonomy.social;

import com.wr.common.core.business.annotation.SensitiveWord;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-10-28 14:12:26
 * @Desc:  社交评论记录表
 */
@Data
public class SocialComment extends BaseEntity {


    private static final long serialVersionUID = -6791881878205985793L;
    /**
     * 主键
     */
    private Long commentId;

    /**
     * 社交id，表xccj_govern.bus_social
     */
    private Long socialId;

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
