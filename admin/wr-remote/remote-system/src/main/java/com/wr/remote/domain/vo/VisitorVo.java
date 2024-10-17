package com.wr.remote.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/21 10:02
 */
@Data
public class VisitorVo extends BaseEntity {

    private Long visitorId;
    //小区id
    private Long communityId;
    //拜访人账号id
    private Long accountId;
    //访客姓名
    private String visitorName;
    //访客手机号
    private String visitorPhone;
    //车牌号
    private String plantNo;
    //访客人数
    private Integer withNum;
    //拜访时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    //有效时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    //拜访房间号
    private Long roomId;
    //拜访业主
    private Long ownerId;
    //被访人姓名
    private String ownerName;
    //被访人手机号
    private String ownerPhone;
    //拜访地址
    private String address;
    private String delFlag;
}
