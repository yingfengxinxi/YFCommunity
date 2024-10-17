package com.wr.remote.aiot;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 13:56:41
 * @Desc: 设备基础类
 */
@Data
public class BaseDevice implements Serializable {
    private static final long serialVersionUID = 4425003103770053065L;

    /**设备唯一编码*/
    @JSONField(name = "indexCode")
    private String indexCode;

    /**创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "createTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**更新时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "updateTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 厂商==> hikvision：海康 */
    @JSONField(name = "manufacturer")
    private String manufacturer;

    /** 在线状态：-1=未检测，0=离线，1=在线 */
    private Integer online;

    /**数据库同步人*/
    private String renewalBy;

    /**数据库同步时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date renewalTime;

    /**数据库同步人*/
    private String updateBy;

    /**数据库同步时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    /**小区id*/
    private Long communityId;
    /**小区名称*/
    private String communityName;

    /** 楼栋id */
    private Long buildingId;
    /**楼栋名称*/
    private String buildingName;

    /** 单元id */
    private Long unitId;
    /**单元名称*/
    private String unitName;

    /** 设备地址 */
    private String address;

    /** 删除状态 0 未删除 1 已删除*/
    private String delFlag;

    private Long[] ids;
    private String[] indexCodeList;
}
