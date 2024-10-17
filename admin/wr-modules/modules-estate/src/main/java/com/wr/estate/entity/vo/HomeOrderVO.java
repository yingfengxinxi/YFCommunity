package com.wr.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.work.patrol.vo.AssistStaffListVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-05 16:39:40
 * @Desc: 居家维修工单 VO
 */
@Data
public class HomeOrderVO implements Serializable {

    private static final long serialVersionUID = -263330318020222996L;

    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 主键
     */
    private Long orderId;
    /**
     * 小区id
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
     * 业主id，表xccj_community.bus_owner
     */
    private Long ownerId;
    /**
     * 业主
     */
    private String ownerName;
    /**
     * 业主手机号
     */
    private String ownerPhone;
    /**
     * 维修类型id，表xccj_work.bus_home_repair
     */
    private Long repairId;
    /**
     * 维修类型
     */
    private String repairName;
    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;
    /**
     * 负责人id，表xccj_community.bus_staff
     */
    private Long staffId;
    /**
     * 负责人
     */
    private String staffName;
    /**
     * 负责人手机号
     */
    private String staffPhone;
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
     * 状态：0=未支付，1=已支付
     */
    private String appointStatus;
    /**
     * 上传图片
     */
    private List<String> photos;
    /**
     * 报事地址
     */
    private String address;
    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;
    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;
    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    /**
     * 异常
     */
    private String abnormalInfo;
    /**
     * 提交人备注
     */
    private String remark;

    private String complaintType;

    /**
     * 协助人列表
     */
    private List<AssistStaffListVO> staffList;

    @Override
    public String toString() {
        return "HomeOrderVO{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", orderName='" + orderName + '\'' +
                ", ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", repairId=" + repairId +
                ", repairName='" + repairName + '\'' +
                ", allotWay='" + allotWay + '\'' +
                ", staffId=" + staffId +
                ", staffName=" + staffName +
                ", acceptStatus='" + acceptStatus + '\'' +
                ", executeStatus='" + executeStatus + '\'' +
                ", appointStatus='" + appointStatus + '\'' +
                '}';
    }
}
