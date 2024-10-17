package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-14 15:59:21
 * @Desc: 工单查询实体类
 */
@Data
public class OrderQueryVO {

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 员工id
     */
    private Long staffId;

    /**
     * 员工名
     */
    private String staffName;

    /**
     * 工单状态，1待分配，2待接单，3待执行，4转交中，5执行中，6已完成，7超时未完成，8超时完成
     */
    private Integer orderStatus;

    /**
     * 工单类型，0巡查工单，1巡更工单，2巡检工单，3保洁工单，4装修评定工单，5居家维修工单，6设备维修工单，7园区报事工单，8灾害报事工单
     */
    private Integer orderType;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 完成图片
     */
    private String finishImageUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 转交接收人id
     */
    private Long targetId;

    /**
     * 协助人信息
     */
    List<Long> assistIds;

    /**
     * 签到点id
     */
    private String id;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 签到状态，1正常，2异常
     */
    private Integer signInStatus;

    /**
     * 图片集合
     */
    private List<String> images;

    /**
     * 登陆id
     */
    private Long userId;

    /**
     * 登陆用户
     */
    private String userName;

    /**
     * 用户类型
     */
    private String userType;


    /**
     * 住户是否损坏公共区域：0=否，1=是
     */
    private Integer damageStatus;


    /**
     * 住户装修楼道整洁度：1=脏乱，2=干净，3=无暇
     */
    private Integer neatStatus;

    /**
     * 评定结果：0=待评定，1=合格，2=违规，3=整改完成
     */
    private String assessResult;
}
