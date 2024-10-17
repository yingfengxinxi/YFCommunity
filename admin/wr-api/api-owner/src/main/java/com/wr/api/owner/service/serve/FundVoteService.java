package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.serve.FundVote;
import com.wr.api.owner.entity.vo.serve.FundVoteResultVO;
import com.wr.api.owner.entity.vo.serve.FundVoteVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:56:18
 * @Desc: 服务-委员会投票 业务
 */
public interface FundVoteService {

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
     * 新增维修基金投票
     *
     * @param vote 新增参数
     * @author yx
     * @date 2023/1/17 14:54
     */
    void insertFundVote(FundVoteVO vote);

    /**
     * 投票
     *
     * @param vote 投票信息
     * @author yx
     * @date 2023/1/17 14:56
     */
    void vote(FundVoteVO vote);

    /**
     * 基金投票统计
     *
     * @param voteId 投票id
     * @return 统计数据
     */
    VoteStatisticsVO voteResultSum(Long voteId);

}
