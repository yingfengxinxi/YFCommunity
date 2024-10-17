package com.wr.api.owner.entity.serve;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 投票表决实体，表：bus_vote
 *
 * @author SJiang
 * @since 2022-11-16 20:26:38
 */
@Data
public class FundVote implements Serializable {
    private static final long serialVersionUID = -6677333136680372371L;

    /**
     * 主键
     */
    private Long voteId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 楼栋id
     */
    private Long buildingId;

    /**
     * 单位id
     */
    private Long unitId;

    /**
     * 投票名称
     */
    private String voteTitle;

    /**
     * 投票类型：0=住宅专项维修基金，1=公共维修基金
     */
    private String voteType;

    /**
     * 发起人id
     */
    private Long ownerId;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 投票开始时间
     */
    private Date startTime;

    /**
     * 投票结束时间
     */
    private Date entTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 驳回理由
     */
    private String rejectReason;

    /**
     * 同意人数
     */
    private Integer agreeNum;

    /**
     * 不同意人数
     */
    private Integer disagreeNum;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


}

