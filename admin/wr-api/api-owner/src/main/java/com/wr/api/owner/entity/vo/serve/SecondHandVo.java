package com.wr.api.owner.entity.vo.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-11 15:14:34
 * @Desc: 二手交易实体类 xccj_community.bus_second_hand
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandVo {
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

    /** 款式*/
    private String handShape;

    /** 成色*/
    private String handQuality;

    /** 价格*/
    private BigDecimal handPrice;

    /** 备注*/
    private String handRemark;

    /** 状态：0=在售，1=已售，2=下架*/
    private String handStatus;

    /** 缩略图*/
    private String thumbnail;

    /** 联系电话*/
    private String contactPhone;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /** 下架时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shelveTime;

    /** 联系人*/
    private String accountName;

    private File file;
}
