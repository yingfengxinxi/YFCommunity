package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.serve.Vote;
import com.wr.api.owner.entity.vo.serve.VoidVO;
import com.wr.api.owner.entity.vo.serve.VoteItemVO;
import com.wr.api.owner.entity.vo.serve.VoteRecordVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;
import com.wr.api.owner.mapper.serve.VoteMapper;
import com.wr.api.owner.service.serve.VoteService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:57:49
 * @Desc: 服务-委员会投票 业务实现
 */
@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteMapper voteMapper;

    @Override
    public List<VoidVO> getVoidAll(Long communityId, Long buildingId, Long unitId, Long ownerId) {
        return voteMapper.getVoidAll(communityId, buildingId, unitId, ownerId);
    }

    @Override
    public List<VoteItemVO> getVoteItemAll(Long voteId) {
        return voteMapper.getVoteItemAll(voteId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upVoteItem(VoteRecordVO vote) {
        int count = voteMapper.countByOwnerId(vote);
        if (count > 0) {
            throw new ServiceException("您已经投过票啦，不可以重复投票！");
        }
        // 新增投票记录
        voteMapper.insertVoteRecord(vote);
        return voteMapper.upVoteItem(vote.getItemId());
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult addVote(Vote vote) {
        Integer nameUnique = voteMapper.checkVoteNameUnique(vote.getVoteName());
        if (nameUnique > 0) {
            return AjaxResult.error("添加失败，'"+ vote.getVoteName() + "'投票名称已存在");
        }
        // 添加投票表决
        voteMapper.addVote(vote);
        // 添加投票表决-小区
        voteMapper.addVoteCommunity(vote);
        // 添加选项
        Integer num = voteMapper.addVoteItem(vote);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 投票统计
     *
     * @param voteId 投票id
     * @return 统计数据
     */
    @Override
    public VoteStatisticsVO voteStatistics(Long voteId) {
        return voteMapper.voteStatistics(voteId);
    }
}
