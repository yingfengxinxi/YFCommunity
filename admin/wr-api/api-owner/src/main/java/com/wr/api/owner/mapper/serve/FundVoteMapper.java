package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.FundVote;
import com.wr.api.owner.entity.vo.serve.FundVoteResultVO;
import com.wr.api.owner.entity.vo.serve.FundVoteVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:30:21
 * @Desc: 服务-业主委员会投票
 */
public interface FundVoteMapper {

    /**
     * 查询列表
     *
     * @param fundVote 查询条件
     * @return java.util.List<com.wr.api.owner.entity.serve.FundVote>
     * @author yx
     * @date 2023/1/17 14:46
     */
    List<FundVoteResultVO> list(FundVote fundVote);

    /**
     * 查看业主基金投票数量，用来判断是否重复投票了
     *
     * @param vote 查询参数
     * @return int
     * @author yx
     * @date 2023/1/17 14:54
     */
    int countByFundUser(FundVoteVO vote);

    /**
     * 新增维修基金投票
     *
     * @param vote 新增参数
     * @author yx
     * @date 2023/1/17 14:54
     */
    void insertFundVote(FundVoteVO vote);

    /**
     * 批量新增基金投票与单元关联
     *
     * @param vote 投票
     */
    void insertFundVoteUnitBatch(FundVoteVO vote);

    /**
     * 批量新增基金投票与楼栋关联
     *
     * @param vote 投票
     */
    void insertFundVoteBuildingBatch(FundVoteVO vote);

    /**
     * 投票
     *
     * @param vote 投票信息
     * @author yx
     * @date 2023/1/17 14:56
     */
    void vote(FundVoteVO vote);

    /**
     * 新增投票记录
     *
     * @param vote 投票信息
     * @author yx
     * @date 2023/1/17 14:57
     */
    void insertVoteUser(FundVoteVO vote);

    /**
     * 基金投票统计
     *
     * @param voteId 投票id
     * @return 统计数据
     */
    VoteStatisticsVO voteResultSum(Long voteId);
}
