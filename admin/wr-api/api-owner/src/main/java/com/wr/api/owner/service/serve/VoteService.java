package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.serve.Vote;
import com.wr.api.owner.entity.vo.serve.VoidVO;
import com.wr.api.owner.entity.vo.serve.VoteItemVO;
import com.wr.api.owner.entity.vo.serve.VoteRecordVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:56:18
 * @Desc: 服务-委员会投票 业务
 */
public interface VoteService {

    /**
     * 获取投票表决
     * @param communityId 小区id
     * @return: java.util.List<java.lang.Void>
     */
    List<VoidVO> getVoidAll(Long communityId, Long buildingId, Long unitId, Long ownerId);

    /**
     * 获取投票表决选项
     * @param voteId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.VoteItemVO>
     */
    List<VoteItemVO> getVoteItemAll(Long voteId);

    /**
     * 投票表决
     * @return: java.lang.Integer
     */
    Integer upVoteItem(VoteRecordVO vote);

    /**
     * 添加投票
     * @param vote
     * @return: java.lang.Integer
     */
    AjaxResult addVote(Vote vote);

    /**
     * 投票统计
     *
     * @param voteId 投票id
     * @return 统计数据
     */
    VoteStatisticsVO voteStatistics(Long voteId);

}
