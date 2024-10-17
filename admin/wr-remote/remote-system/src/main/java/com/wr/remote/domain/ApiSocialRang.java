package com.wr.remote.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 社交-小区关联表
 * @Author: lvzy
 * @Date: 2022-11-16 15:59:27
 * @Desc:社交-小区关联表
 */
@Data
public class ApiSocialRang implements Serializable {

    /**
     * 社交id，表xccj_govern.bus_social
     */
    private Long socialId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    private static final long serialVersionUID = 1L;
}