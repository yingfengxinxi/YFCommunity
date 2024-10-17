package com.wr.remote.govern.autonomy.social;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022-10-28 14:03:28
 * @Desc: 社交表
 */
@Data
public class Social extends BaseEntity {

    private static final long serialVersionUID = -5701331041486844465L;
    /**
     * 主键
     */
    private Long socialId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    private Long communityId;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型：0=议事堂，1=红黑榜
     */
    private String socialType;

    /**
     * 发布人类型：0=系统，1=业主，2=租客
     */
    private String userType;

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
    private String socialUrl;
    private String startTime;
    private String endTime;

    private List<String> socialUrls;

    /**
     * 所发布的小区（系统信息发布到那些小区）
     */
    private Long[] communityIds;
}
