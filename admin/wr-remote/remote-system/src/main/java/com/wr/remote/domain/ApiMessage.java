package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 党建资讯
 *
 * @Author: lvzy
 * @Date: 2022-11-15 16:37:09
 * @Desc:党建资讯
 */
@Data
public class ApiMessage extends BaseEntity implements Serializable {

    /**
     * id
     */
    private Long messageId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 咨询标题
     */
    private String msgTitle;

    //是否置顶0=否1=是
    private String top;

    /**
     * 跳转路径
     */
    private String skipUrl;

    //活动封面
    private String activePicture;

    /**
     * 内容
     */
    private String content;

    /**
     * 阅读数
     */
    private Long lookNumber;

    /**
     * 排序序号
     */
    private Long sortNumber;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}