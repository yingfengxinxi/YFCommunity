package com.wr.remote.work.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-01 14:29:23
 * @Desc: 灾害报事工单表Vo实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDisasterOrderVo {



    /**
     * 主键id
     */
    private Long orderId;


    /**
     *
     */
    private Long auditId;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 灾害类型id
     */

    private Long disasterId;
    /**
     * 灾害类型
     */
    private String disasterName;

    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;

    /**
     * 接单人
     */
    private String staffName;


    /**
     * 接单状态：0=待接单，1=已接单
     */
    private String acceptStatus;

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

    /**
     * 工单执行人信息
     */
     List <BusDisasterRecordVo> busDisasterRecordVos;


    /**
     * 手机号
     */
    private String staffPhone;

    /**
     * 身份证号
     */
    private String cardNo;

    /**
     * 岗位：0=安防人员，1=维修人员，2=装修人员，3=场馆人员，4=保洁人员，5=志愿者
     */
    private String workPost;

    /**
     * 人员类别
     */
    private String personnelType;
    /**
     * 接单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;


    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 图片
     */
    private String imageUrl;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 主要负责人
     */
    private String mainStaffName;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 状态
     */
    private String auditStatus;

    /**
     *分配状态
     */
    private String allotStatus;

    /**
     *
     */
    private String userName;

    private Date createTime;

    private String delFlag;

    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private String executeStatus;

    /**
     * 审核状态
     */
    private String auditType;

    private List<String> imageList;

    private String yc;

    private Long staffId;

    private String assistNum;

    private String transferStatus;


    private Long executeId;



    private String appraiseStatus;

    private String appraiseStar;

    private String appraiseRemark;
    /**
     *物业Id
     */
    private Long estateId;

    private String complaintType;
}
