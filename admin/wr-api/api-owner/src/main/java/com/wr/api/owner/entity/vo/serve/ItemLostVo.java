package com.wr.api.owner.entity.vo.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:33:50
 * @Desc: 寻物
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLostVo {
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

    /** 失踪日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lostDate;

    /** 失踪地点*/
    private String lostAddress;

    /** 联系人*/
    private String contactName;

    /** 联系人电话*/
    private String contactPhone;

    /** 详情*/
    private String lostInfo;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    /** 状态：0=寻找中，1=已找回，2=已撤销*/
    private String lostStatus;
}
