package com.wr.api.owner.entity.vo.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class FundVoteResultVO implements Serializable {
    private static final long serialVersionUID = -6677333136680372371L;

    private Long voteId;

    /**
     * 投票名称
     */
    private String voteName;

    /**
     * 投票类型：0=住宅专项维修基金，1=公共维修基金
     */
    private String voteType;

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

    /**
     * 状态，0最新，1历史
     */
    private Integer state;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private Integer auditStatus;

    /**
     * 驳回理由
     */
    private String rejectReason;

    /**
     * 是否已经投票，1已投票，0未投票
     */
    private Integer hasVote;

}

