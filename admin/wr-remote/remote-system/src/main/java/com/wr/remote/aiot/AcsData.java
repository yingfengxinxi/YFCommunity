package com.wr.remote.aiot;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 13:39:02
 * @Desc: 门禁事件扩展信息
 */
@Data
public class AcsData implements Serializable {
    private static final long serialVersionUID = -6206385921866320102L;

    /** 主键id */
    private Long dataId;

    /** 小区id */
    private Long communityId;

    /** 预警事件主键 */
    private Long warnId;

    /** 人员类型：0 未知，1 普通，2 来宾，3 黑名单，4 管理员 */
    private String userType;

    /** 身份证id */
    private String idNum;

    /** 姓名 */
    private String userName;

    /** 性别:0-未知,1-男,2-女 */
    private Integer userSex;

    /** 人员通道号 */
    private String extAccessChannel;

    /** 进人数 */
    private String entryTimes;

    /** 出人数 */
    private String exitTimes;

    /** 总通行人数 */
    private String totalTimes;

    /** 卡号 */
    private String extEventCardNo;

    /** 事件输入通道 */
    private String extEventCaseId;

    /** 事件类型代码:198657=门被外力开启,199428=胁迫报警,199708=设备防拆报警,199709=设备防拆恢复 */
    private String extEventCode;

    /** 门编号 */
    private String extEventDoorId;

    /** 身份证图片URL */
    private String extEventIdCardPictureUrl;

    /** 进出方向:1：进,0：出,-1:未知 */
    private String extEventInOut;

    /** 人员编号 */
    private String extEventPersonNo;

    /** 图片的url */
    private String extEventPictureUrl;

    /** 刷卡次数 */
    private String extEventSwipNum;

    /** 事件上报驱动的时间,用于统计事件上报延时，Unix时间戳，精确到微秒 */
    private String extReceiveTime;

    /** 事件流水号，为0无效 */
    private String seq;

    /** 图片服务器唯一编码 */
    private String svrIndexCode;

    /** 删除状态 0 未删除 1 已删除 */
    private String delFlag;
}
