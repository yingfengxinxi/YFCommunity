package com.wr.remote.estate.customer.lost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:58:10
 * @Desc: 寻物启事表实体类
 */
@Data
public class ThingLostVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -65958639411959171L;

    /** 主键*/
    private Long lostId;

    /** 小区id，表xccj_community.bus_community*/
    private Long communityId;

    /** 缩略图*/
    private String thumbnail;

    /** 名称*/
    private String lostName;

    /** 丢失日期*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lostDate;

    /** 丢失地点*/
    private String lostAddress;

    /** 联系人*/
    private String contactName;

    /** 联系人电话*/
    private String contactPhone;

    /** 详情*/
    private String lostInfo;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /** 状态：0=寻找中，1=已找回，2=已撤销*/
    private String lostStatus;

    private String delFlag;

}
