package com.wr.remote.aiot;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 15:23:10
 * @Desc: 可视对讲预警事件详情
 */
@Data
public class VisData implements Serializable {
    private static final long serialVersionUID = 7298175336824470118L;

    /** 主键 */
    private Long dataId;

    /** 预警事件主键 表xccj_community.bus_warn_event */
    private Long warnId;

    /**小区id*/
    private Long communityId;
    /**小区名称*/
    private String communityName;

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

    /** 事件类型代码:983302=设备防拆报警,983303=劫持报警,983556=烟感报警,983560=紧急开关报警,983563=智能锁撬门报警 */
    private String extEventCode;

    /** 门编号 */
    private String extEventDoorId;

    /** 身份证图片地址 */
    private String extEventIdCardPictureUrl;

    /** 人员类型：0 未知，1 普通，2 来宾，3 黑名单，4 管理员 */
    private String userType;

    /** 身份证id */
    private String idNum;

    /** 姓名 */
    private String userName;

    /** 性别:0-未知,1-男,2-女 */
    private String userSex;

    /** 进出方向:1-进,2-出 */
    private String extEventInOut;

    /** 人员编号 */
    private String extEventPersonNo;

    /** 图片的url */
    private String extEventPictureUrl;

    /** 刷卡次数 */
    private String extEventSwipNum;

    /** 接收时间戳 */
    private String extReceiveTime;

    /** 序列 */
    private String seq;

    /** 图片服务器唯一编码 */
    private String svrIndexCode;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;
}
