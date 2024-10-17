package com.wr.govern.mapper.autonomy.vote;


import com.wr.remote.govern.autonomy.vote.VoteItem;
import com.wr.remote.govern.autonomy.vote.VoteRecord;

import java.util.List;

/**
 * 投票选项管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface VoteItemMapper {


    /**
     * 添加问卷调查题目选项数据
     * @param voteItem
     * @return
     */
    public int insertBusVoteItem(VoteItem voteItem);
    /**
     * 查看问卷调查选项数据
     * @param
     * @return
     */
    public List<VoteItem> selectBusVoteItem(Long voteId);
    /**
     * 投票结果业主详情
     * @param
     * @return
     */
    public List<VoteRecord> getVoteRecord(VoteRecord voteRecord);
    /**
     * 修改题目选项
     * @param voteItem
     * @return
     */
    int updateBusVoteItem(VoteItem voteItem);
}
