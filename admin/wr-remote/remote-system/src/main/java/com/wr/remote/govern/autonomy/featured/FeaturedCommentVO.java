package com.wr.remote.govern.autonomy.featured;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 17:31
 */
@Data
public class FeaturedCommentVO extends FeaturedComment implements Serializable {
    private static final long serialVersionUID = -6791881878205985793L;
    /**
     * 头像URL
     */
    private String avatarUrlContent;

    /**
     * 用户昵称
     */
    private String nickNameContent;

    /**
     * 点赞数量
     */
    private Long numberLikeContent;

    /**
     * 评论内容
     */
    private String contentContent;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeContent;

    /**
     * 评论区是否点赞 0:没有点赞, 1:已经点赞
     */
    private String whetherPraiseContent;

    List<FeaturedCommentVO> featuredCommentVOS;
}
