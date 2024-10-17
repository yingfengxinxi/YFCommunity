package com.wr.remote.domain.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.domain.ApiSocialComment;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 社交评论记录表扩展类
 * @Author: lvzy
 * @Date: 2022-11-24 18:01:28
 * @Desc:社交评论记录表扩展类
 */
@Data
public class ApiSocialCommentVo extends ApiSocialComment implements Serializable {

    private static final long serialVersionUID = 1823815933011189549L;

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

    /**
    * @Author SUNk
    * @Description 二级子评论
    * @Date 18:23 2024/3/18
    * @Param
    * @return
    **/
    List<ApiSocialCommentVo> socialCommentVos;
}
