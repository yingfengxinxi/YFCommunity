package com.wr.govern.service.autonomy.vote;

import com.wr.remote.govern.autonomy.vote.Vote;
import com.wr.remote.govern.autonomy.vote.VoteRecord;

import java.text.ParseException;
import java.util.List;


/**
 * 部门管理 服务层
 *
 * @author wr
 */
public interface VoteService {
    /**
     * 查询投票管理
     *
     * @param vote 投票管理
     * @return 投票管理信息集合
     */
    public List<Vote> selectBusEstateList(Vote vote);

    /**
     * 投票结果业主详情
     *
     * @param
     * @return 投票管理信息集合
     */
    public List<VoteRecord> getVoteRecord(VoteRecord voteRecord);

    /**
     * 根据投票管理ID查询信息
     *
     * @param voteId 投票id
     * @return 结果
     */
    public Vote selectBusEstateById(Long voteId);

    /**
     * 新增投票管理信息
     *
     * @param vote 投票管理信息
     * @return 结果
     */
    public int insertBusEstate(Vote vote) throws ParseException;


    /**
     * 删除投票管理信息
     *
     * @param voteId 投票id
     * @return 结果
     */
    public int deleteBusEstateById(Long[] voteId);

    /**
     * 发布公告
     *
     * @param voteId 投票id
     */
    void bulletin(Long voteId);
}
