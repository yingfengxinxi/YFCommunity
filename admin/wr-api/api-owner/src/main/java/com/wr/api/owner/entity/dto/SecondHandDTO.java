package com.wr.api.owner.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-29 14:06:23
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandDTO {
    /** 主键*/
    private Long handId;

    /** 小区id*/
    private Long communityId;

    /** 发布人账号id，表bus_account*/
    private Long accountId;

    /** 标题/名称*/
    private String handTitle;

    /** 品牌*/
    private String handBrand;

    /** 联系人*/
    private String accountName;

    /** 价格*/
    private BigDecimal handPrice;

    /** 缩略图*/
    private String thumbnail;

    /** 成色*/
    private String handQuality;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    /** 状态：0=在售，1=已售，2=下架*/
    private String handStatus;
}
