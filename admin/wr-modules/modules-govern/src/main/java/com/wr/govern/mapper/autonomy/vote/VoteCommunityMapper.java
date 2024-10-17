package com.wr.govern.mapper.autonomy.vote;

import com.wr.remote.govern.autonomy.vote.Vote;
import com.wr.remote.govern.autonomy.vote.VoteCommunity;

/**
 * 投票社区关联管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface VoteCommunityMapper {

    /**
     * 新增问卷调查发布范围
     *
     * @param voteCommunity
     * @return
     */
    public int insertBusVoteCommunity(VoteCommunity voteCommunity);

    /**
     * 批量新增社区投票楼栋范围关联
     *
     * @param vote 投票
     * @return int
     */
    void insertVoteBuildingBatch(Vote vote);

    /**
     * 批量新增社区投票楼栋范围关联
     *
     * @param vote 投票
     * @return int
     */
    void insertVoteUnitBatch(Vote vote);
}
