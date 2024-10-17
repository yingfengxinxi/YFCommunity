package com.wr.remote.work.deviceorder;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.sql.Date;

/**
 * @author DIANWEI
 * @Description 设备维修工单实体类
 * @date 2022/10/27 15:43
 */
@Data
public class DeviceOrder extends BaseEntity {
    /**
     * 主键
     */
    private Long orderId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 工单编号
     */
    private String orderNo;
    /**
     * 工单名称
     */
    private String orderName;
    /**
     * 设备类型：见类 DeviceConstants
     */
    private String deviceType;
    /**
     * 设备唯一标识，device_type对应的表
     */
    private String indexCode;
    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;
    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;
    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;
    /**
     * 负责人id，表xccj_community.bus_staff
     */
    private Long staffId;
    /**
     * 协助人数
     */
    private Integer assistNum;
    /**
     * 转交状态：0=无需转交，1=转交待确认
     */
    private String transferStatus;
    /**
     * 分配状态：0=待分配，1=已分配
     */
    private String allotStatus;
    /**
     * 接单状态：0=待接单，1=已接单
     */
    private String acceptStatus;
    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private String executeStatus;
    /**
     * 评定结果：0=合格，1=违规，2=整改完成
     */
    private String assessResult;
    /**
     * 执行人id，表xccj_community.bus_staff
     */
    private Long executeId;
    /**
     * 执行时间
     */
    private Date executeTime;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 完成图片
     */
    private String imageUrl;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}
