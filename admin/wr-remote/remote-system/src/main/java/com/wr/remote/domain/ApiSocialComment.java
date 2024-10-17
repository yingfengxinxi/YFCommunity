package com.wr.remote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.business.annotation.SensitiveWord;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 社交评论记录表
 * @Author: lvzy
 * @Date: 2022-11-16 15:58:38
 * @Desc:社交评论记录表
 */
@Data
public class ApiSocialComment implements Serializable {

    /**
     * 主键
     */
    private Long commentId;

    /**
     * 社交id，表xccj_govern.bus_social
     */
    private Long socialId;

    /**
     * 评论人员类型：0=系统(sys_user)，1=业主(bus_account)，2=租客(bus_account)
     */
    private String userType;
    //父级id
    private Long parentCommentId;
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
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}