package com.wr.remote.govern.autonomy.social.vo;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.govern.autonomy.social.SocialComment;

import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-11-02 17:57:22
 * @Desc: 评论的vo类
 */
public class CommentVo extends SocialComment {

    private static final long serialVersionUID = -6643750163205303249L;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String accountName;


    /**
     * 内容
     */
    private String content;


    /**
     * 评论id
     */
    private Long commentId;


    //被回复的登录名称
    private String replyName;


    /**
     * 被评论的登录名称
     */
    private String loginName;



    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
