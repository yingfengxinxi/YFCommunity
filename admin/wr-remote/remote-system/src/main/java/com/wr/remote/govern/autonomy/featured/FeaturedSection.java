package com.wr.remote.govern.autonomy.featured;

import com.wr.common.core.business.annotation.SensitiveWord;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022-10-28 14:03:28
 * @Desc: 特色板块表
 */
@Data
public class FeaturedSection extends BaseEntity {

    private static final long serialVersionUID = -5701331041486844465L;
    /**
     * 主键
     */
    private Long featuredId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    private Long communityId;

    /**
     * 内容
     */
    @SensitiveWord
    private String content;


    /**
     * 发布人类型：0=系统，1=业主，2=租客
     */
    private String userType;
    //特色板块类型
    private String featuredSectionTypeId;

    /**
     * 发布人id，user_type对应表id
     */
    private Long userId;

    /**
     * 发布人
     */
    private String userName;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 冗余字段
     * 小区名称
     * 查询使用
     */
    private String userCommunityName;

    /**
     * 图片地址
     */
    private String featuredUrl;
    private String startTime;
    private String endTime;

    private List<String> featuredUrls;

    /**
     * 所发布的小区（系统信息发布到那些小区）
     */
    private Long[] communityIds;

    //头像
    private String avatarUrl;
    //昵称
    private String accountNick;
    //发布人业主ID
    private String ownerId;
    /**
     * 特色板块评论记录 List
     */
    List<FeaturedCommentVO> commentList;
    /**
     * 点赞人数
     */
    private Long numberLike;

    /**
     * 是否点赞 0:没有点赞, 1:已经点赞
     */
    private String whetherPraise;
    //评论总数
    private Integer comentount;
}
