package com.wr.remote.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/21 10:02
 */
@Data
public class InterviewVo extends BaseEntity {

    private String id;
    private String visitorId;
    //小区id
    private Long communityId;
    //拜访人账号id
    private Long accountId;
    //访客姓名
    @Excel(name = "访客姓名")
    private String visitorName;
    //访客手机号
    @Excel(name = "访客手机号码")
    private String visitorPhone;
    //拜访地址
    @Excel(name = "拜访地址",type = Excel.Type.EXPORT)
    private String address;
    //访客人数
    @Excel(name = "访客人数")
    private Integer withNum;
    //车牌号
    @Excel(name = "访客车牌号")
    private String plantNo;

    //拜访次数
    @Excel(name = "拜访次数",type = Excel.Type.EXPORT)
    private Integer visitorNum;

    //拜访时间
    @Excel(name = "拜访时间", dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    //有效时间
    @Excel(name = "有效时间", dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    //拜访房间号
    private Long roomId;
    //拜访业主
    private Long ownerId;

    private String plantCar;

    //被访人姓名
    @Excel(name = "拜访业主姓名")
    private String ownerName;
    //被访人手机号
    @Excel(name = "拜访业主手机号")
    private String ownerPhone;

    private String delFlag;
}
