package com.wr.remote.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 社交点赞记录表
 * @Author: lvzy
 * @Date: 2022-11-16 15:59:06
 * @Desc:社交点赞记录表
 */
@Data
public class ApiSocialLike implements Serializable {

    /**
     * 社交id，表xccj_govern.bus_social
     */
    private Long socialId;

    /**
     * 评论人员类型：0=系统(sys_user)，1=业主(bus_account)，2=租客(bus_account)
     */
    private String userType;

    /**
     * 人员id，user_type对应表id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}