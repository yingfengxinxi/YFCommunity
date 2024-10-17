package com.wr.remote.domain;

import com.wr.common.core.business.annotation.SensitiveWord;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 社交表
 * @Author: lvzy
 * @Date: 2022-11-16 15:56:31
 * @Desc:社交表
 */
@Data
public class ApiSocial extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    private Long socialId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    /**
     * 所属小区
     */
    private Long communityId;

    /**
     * 图片地址
     */
    private String socialUrl;

    /**
     * 内容
     */
    @SensitiveWord
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
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}