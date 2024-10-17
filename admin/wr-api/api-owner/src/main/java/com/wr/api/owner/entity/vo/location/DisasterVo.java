package com.wr.api.owner.entity.vo.location;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.api.owner.entity.home.location.ApiComplaint;
import com.wr.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-12-01 13:12:40
 * @Desc: 灾害报事
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisasterVo {

    /**
     * 主键
     */
    private Long disasterId;


    /**
     * 灾害报事申请主键
     */
    private Long auditId;



    /**
     * 工单灾害工单
     *
     */
    private Long orderId;

    /**
     * 灾害类型
     */
    private String disasterName;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 物业id
     */
    private Long estateId;

    /**
     * 评价星级
     */
    private Integer appraiseStar;

    /**
     * 评价状态：0=未评价，1=已评价
     */
    private String appraiseStatus;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private String executeStatus;

    /**
     * 工单分配方式
     */
    private String allotWay;

    /**
     * 工单分配方式
     */
    private Integer staffId;

    /**
     * 分配状态：0=待分配，1=已分配
     */
    private String allotStatus;

    /**
     * 转交状态：0=无需转交，1=转交待确认
     */
    private String transferStatus;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 业主id
     */
    private Long ownerId;

    /**
     * 名称
     */
    private String auditName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 业主姓名
     */
    private String ownerName;

    /**
     * 图片地址
     */
    private String[] imageUrls;

    private String imageUrl;

    private List<String> imageList;

    /**
     * 图片类型
     */
    private String imageType;

    /**
     * 主表id，根据图片类型对应表
     */
    private Long tableId;

    /**
     * 所有状态（0:待审核，1：待分配，2：待执行，3：执行中，4：待评价，5：已评价，6：转交中）
     */
    private String statusAll;

    /**
     * 工单协助人信息
     */
    List<StaffInfoVo> allAssist;

    List<ApiComplaint> apiComplaint;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planStart;


    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planEnd;

   private String staffNameOne;
    private String staffPhoneOne;
    private String staffNameTwo;
    private String staffPhoneTwo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;


    /**
     * 协助人数
     */
    private Integer assistNum;

    /**
     * 接单状态：0=待接单，1=已接单
     */
    private String acceptStatus;


    /**
     * 执行人id
     */
    private String executeId;

    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date executeTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /**
     * 评价备注
     */
    private String appraiseRemark;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
}
