package com.wr.api.estate.entity.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 15:29:44
 * @Desc: MeterRecord
 */
@Data
public class MeterRecord implements Serializable {

    private static final long serialVersionUID = -5747502221374279652L;
    /*主键*/
    private Long recordId;
    /*小区id，表xccj_community.bus_community*/
    private Long communityId;
    /*楼栋id，表xccj_community.bus_building*/
    private Long buildingId;
    /*单元id，表xccj_community.bus_unit*/
    private Long unitId;
    /*当前所住房间id，表xccj_community.bus_room*/
    private Long roomId;
    /*业主id，表xccj_community.bus_owner*/
    private Long ownerId;
    /**
     * 抄表人id 表xccj_community.bus_staff'
     */
    private Long staffId;
    /*房间号*/
    private String roomName;
    /**/
    private String unitName;
    /**/
    private String buildingName;

    /**
     * 是否已抄表：0=否，1=是
     */
    private String putStatus;
    /*业主姓名*/
    private String ownerName;
    /*业主手机号*/
    private String ownerPhone;

    /**
     * 上次抄表数
     */
    private BigDecimal lastConsume;
    /**
     * 本次抄表数
     */
    private BigDecimal thisConsume;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    /** 抄表时间*/
    private Date meterTime;
    /*是否已推送：0=否，1=是*/
    private String meterPush;
    /**
     * 前端传入日期
     */
    private String mouths;
    private String gender;
    /*删除标记：0=未删除，1=已删除*/
    private String flag;
    /*表类型：0=水表，1=电表，2=燃气表*/
    private String meterType;
    /**
     * 用户id
     */
    private String userId;
}
