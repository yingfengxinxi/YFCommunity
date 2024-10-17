package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.serve.FundVote;
import com.wr.api.owner.entity.vo.serve.FundVoteResultVO;
import com.wr.api.owner.entity.vo.serve.FundVoteVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;
import com.wr.api.owner.mapper.serve.FundVoteMapper;
import com.wr.api.owner.service.serve.FundVoteService;
import com.wr.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:56:18
 * @Desc: 服务-委员会投票 业务
 */
@Service
public class FundVoteServiceImpl implements FundVoteService {

    @Autowired
    private FundVoteMapper fundVoteMapper;

    /**
     * 查询列表
     *
     * @param fundVote 查询条件
     * @return java.util.List<com.wr.api.owner.entity.serve.FundVote>
     * @author yx
     * @date 2023/1/17 14:46
     */
    @Override
    public List<FundVoteResultVO> list(FundVote fundVote) {
        return fundVoteMapper.list(fundVote);
    }

    /**
     * 新增维修基金投票
     *
     * @param vote 新增参数
     * @author yx
     * @date 2023/1/17 14:54
     */
    @Override
    public void insertFundVote(FundVoteVO vote) {
        fundVoteMapper.insertFundVote(vote);
        if (!CollectionUtils.isEmpty(vote.getUnitIdList())) {
            // 新增基金投票与单元关联
            fundVoteMapper.insertFundVoteUnitBatch(vote);
        }
        if (!CollectionUtils.isEmpty(vote.getBuildingIdList())) {
            // 新增基金投票与楼栋关联
            fundVoteMapper.insertFundVoteBuildingBatch(vote);
        }
    }

    /**
     * 投票
     *
     * @param vote 投票信息
     * @author yx
     * @date 2023/1/17 14:56
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void vote(FundVoteVO vote) {
        int count = fundVoteMapper.countByFundUser(vote);
        if (count > 0) {
            throw new ServiceException("您已经投过票啦，不可以重复投票！");
        }
        fundVoteMapper.vote(vote);
        fundVoteMapper.insertVoteUser(vote);
    }

    /**
     * 基金投票统计
     *
     * @param voteId 投票id
     * @return 统计数据
     */
    @Override
    public VoteStatisticsVO voteResultSum(Long voteId) {
        return fundVoteMapper.voteResultSum(voteId);
    }
}
