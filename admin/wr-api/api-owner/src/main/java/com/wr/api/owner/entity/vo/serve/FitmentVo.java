package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 14:23:01
 * @Desc: 装修实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitmentVo {
    /**
     * 主键
     */
    private Long decorateId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;

    private Long accountId;
    /**
     * 单元id，表bus_unit
     */
    private Long unitId;
    /**
     * 当前所住房间id，表bus_room
     */
    private Long roomId;
    /**
     * 业主id，表bus_owner
     */
    private Long ownerId;
    /**
     * 计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planStart;
    /**
     * 计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planEnd;
    /**
     * 装修押金
     */
    private BigDecimal deposit;
    /**
     * 审核状态：0=审核中，1=驳回，2=通过（进行中），3=延期待确认,4=已延期，5=已完成
     */
    private String auditStatus;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 延期天数（天）
     */
    private Integer delayDay;
    /**
     * 完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date finishDate;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 申请类型：0=装修申请，1=延期申请
     */
    private String decorateType;
    /**
     * 审批人
     */
    private String auditBy;
    /**
     * 业主手机号
     */
    private String ownerPhone;
    /**
     * 业主姓名
     */
    private String ownerName;
    /**
     * 装修地址
     */
    private String decorateName;
    /**
     * 房间号
     */
    private String roomName;
    /**
     * 地址
     */
    private String address;


    private String cardNo;

    /**
     * 小区名
     */
    private String communityName;

    /**
     * 单元名
     */
    private String unitName;

    /**
     * 楼栋名
     */
    private String buildingName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 装修工
     */
    List<UpfitterVo> upfitterVos;

    /**
     *1:内部,2外部
     */
    private String inside;

    /**
     * 装修工内外部:0=内部，1=外部
     */
    private String intOut;

    private String workerName;

    private String workerPhone;

    /**
     * 装修人数
     */
    private Integer countPeople;

    private String staffName;

    private String staffPhone;

    private String workerType;

    private String avatar;
}
