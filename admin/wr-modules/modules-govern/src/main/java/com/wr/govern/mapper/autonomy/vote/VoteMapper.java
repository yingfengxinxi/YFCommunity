package com.wr.govern.mapper.autonomy.vote;


import com.wr.remote.govern.autonomy.vote.Vote;

import java.util.List;

/**
 * 投票信息管理
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface VoteMapper {
    /**
     * 删除投票管理
     *
     * @param voteId
     * @return
     */
    int deleteBusEstateById(Long[] voteId);

    /**
     * 查询投票管理
     *
     * @param vote
     * @return
     */
    List<Vote> selectBusEstateList(Vote vote);

    /**
     * 根据ID查询投票管理
     *
     * @param voteId
     * @return
     */
    Vote selectBusEstateById(Long voteId);

    /**
     * 新增投票管理
     *
     * @param vote
     * @return
     */
    int insertBusEstate(Vote vote);

    /**
     * 同意统计
     *
     * @param voteId 投票id
     * @return 总数
     */
    int consentCount(Long voteId);

    /**
     * 不同意统计
     *
     * @param voteId 投票id
     * @return 总数
     */
    int unConsentCount(Long voteId);

    /**
     * 查询投票关联小区
     *
     * @param voteId
     * @return
     */
    List<Long> selectCommunityIds(Long voteId);

    /**
     * 已发布通知
     *
     * @param voteId 投票id
     */
    void updateHasNotice(Long voteId);
}
