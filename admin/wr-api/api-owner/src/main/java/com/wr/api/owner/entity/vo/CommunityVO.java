package com.wr.api.owner.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 20:24:00
 * @Desc: 小区 VO
 */
@Data
public class CommunityVO implements Serializable {
    private static final long serialVersionUID = -897753685481206446L;

    private Long communityId;

    private Long estateId;

    private String communityName;

    private String address;


}
