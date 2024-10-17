package com.wr.api.estate.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class LiaisonVo {


    private static final long serialVersionUID = 541214240135001071L;
    private Integer communityId;
    /**
     * 主键id
     */
    private Long liaisonId;

    /**
     * 账号id，表bus_account(account_type=2,3)
     */
    private Long accountId;

    /**
     * 拜访房间id,表bus_room
     */
    private Long roomId;

    /**
     * 被访人id,表bus_owner
     */
    private Long ownerId;

    /**
     * 拜访人姓名
     */
    private String userName;

    /**
     * 拜访人联系方式
     */
    private String userPhone;

    /**
     * 拜访人身份证号
     */
    private String cardNo;

    /**
     * 二维码路径
     */
    private String codeUrl;

    /**
     * 拜访人车牌号
     */
    private String plantNo;

    /**
     * 同行人数
     */
    private Integer withNum;

    /**
     * 二维码有效期开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date beginTime;

    /**
     * 二维码有效期过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 数据类型:0=用户为自己创建,1=业主为别人创建,2=装修人员
     */
    private String liaisonType;

    /**
     * 业主姓名
     */
    private String ownerName;

    /**
     * 业主联系方式
     */
    private String ownerPhone;


    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 楼栋名称
     */
    private String buildingName;

    /**
     * 房号名称
     */
    private String roomName;

    /**
     * 当前装修id，表bus_decorate(liaison_type=2时有值)
     */
    private Long decorateId;

    /**
     * 二维码申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 二维码业主审核通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date passTime;

    /**
     * 状态 0 待审核 1 审核通过 2 未通过审核
     */
    private String liaisonStatus;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;
}
