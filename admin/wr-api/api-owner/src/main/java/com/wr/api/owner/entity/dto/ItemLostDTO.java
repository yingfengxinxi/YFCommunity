package com.wr.api.owner.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-29 14:27:30
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLostDTO {
    /** 主键*/
    private Long lostId;

    /** 小区id，表xccj_community.bus_community*/
    private Long communityId;

    /** 发布人账号id，表xccj_community.bus_account*/
    private Long accountId;

    /** 缩略图*/
    private String thumbnail;

    /** 名称*/
    private String lostName;

    /** 详情*/
    private String lostInfo;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    /** 联系人*/
    private String contactName;

    /** 联系人*/
    private String contactPhone;


}
