package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:31:06
 * @Desc: 投票表决 VO
 */
@Data
public class VoidVO implements Serializable {
    private static final long serialVersionUID = 4484931924754179247L;

    /**
     * 主键
     */
    private Long voteId;
    /**
     * 投票名称
     */
    private String voteName;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 状态：0=最新，1=历史
     */
    private String status;

    /**
     * 是否已经投票，1已投票，0未投票
     */
    private Integer hasVote;
}
