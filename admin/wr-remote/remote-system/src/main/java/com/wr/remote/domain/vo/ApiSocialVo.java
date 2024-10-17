package com.wr.remote.domain.vo;

import com.wr.remote.domain.ApiSocial;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 社交表扩展类
 * @Author: lvzy
 * @Date: 2022-11-16 16:31:03
 * @Desc:社交表扩展类
 */
@Data
public class ApiSocialVo extends ApiSocial implements Serializable {

    private static final long serialVersionUID = -7613179966427909137L;

    /**
     * 点赞人数
     */
    private Long numberLike;

    /**
     * 头像
     */
    private String avatarUrl;
    //业主账号
    private String ownerId;
    //昵称
    private String accountNick;

    /**
     * 是否点赞 0:没有点赞, 1:已经点赞
     */
    private String whetherPraise;

    /**
     * 类型：0=议事堂，1=红黑榜
     */
    private String socialType;

    //评论总数
    private Integer comentount;

    /**
     * 社交评论记录 List
     */
    List<ApiSocialCommentVo> commentList;
}
