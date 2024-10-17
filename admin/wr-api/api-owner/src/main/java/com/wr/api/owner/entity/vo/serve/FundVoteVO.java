package com.wr.api.owner.entity.vo.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 投票表决实体，表：bus_vote
 *
 * @author SJiang
 * @since 2022-11-16 20:26:38
 */
@Data
public class FundVoteVO implements Serializable {
    private static final long serialVersionUID = -6677333136680372371L;

    private Long voteId;

    /**
     * 小区id
     */
    private Long communityId;


    /**
     * 楼栋ids
     */
    private List<Long> buildingIdList;

    /**
     * 单位ids
     */
    private List<Long> unitIdList;

    /**
     * 投票名称
     */
    private String voteTitle;

    /**
     * 投票类型：0=住宅专项维修基金，1=公共维修基金
     */
    private String voteType;

    /**
     * 投票结果：0=同意，1=不同意
     */
    private String voteStatus;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 发起人id/投票人id
     */
    private Long ownerId;

    /**
     * 投票开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 投票结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 内容
     */
    private String content;

}

