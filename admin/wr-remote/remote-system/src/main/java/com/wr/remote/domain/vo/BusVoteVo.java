package com.wr.remote.domain.vo;

import com.wr.remote.estate.vote.BusVote;
import com.wr.remote.estate.vote.BusVoteScope;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 16:00
 */
@Data
public class BusVoteVo extends BusVote {

    //投票范围
    private List<BusVoteScope> voteScopeList;
    private List<BusVoteResultVo> voteResultList;
    //投票结果
    private String result;
    //应投票人数
    private Integer voteTotal;
    //已投票人数
    private Integer yVoteTotal;
    //已投票百分比
    private String yVotePercentage;
    //未反馈投票人数
    private Integer wVoteTotal;
    //未反馈百分比
    private String wVotePercentage;
    //总面积
    private BigDecimal totalArea;
    //已投总面积
    private BigDecimal yTotalArea;
    //已投总面积百分比
    private String yTotalAreaPercentage;
    //未反馈面积
    private BigDecimal wTotalArea;
    //未反馈面积百分比
    private String wTotalAreaPercentage;

}
