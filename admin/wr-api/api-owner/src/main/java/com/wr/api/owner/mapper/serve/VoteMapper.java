package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.Vote;
import com.wr.api.owner.entity.vo.serve.VoidVO;
import com.wr.api.owner.entity.vo.serve.VoteItemVO;
import com.wr.api.owner.entity.vo.serve.VoteRecordVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:30:21
 * @Desc: 服务-业主委员会投票
 */
public interface VoteMapper {


    /**
     * 获取投票表决
     *
     * @param communityId 小区id
     * @return: java.util.List<java.lang.Void>
     */
    List<VoidVO> getVoidAll(@Param("communityId") Long communityId,
                            @Param("buildingId") Long buildingId,
                            @Param("unitId") Long unitId,
                            @Param("ownerId") Long ownerId);

    /**
     * 获取投票表决选项
     *
     * @param voteId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.VoteItemVO>
     */
    List<VoteItemVO> getVoteItemAll(@Param("voteId") Long voteId);

    /**
     * 投票表决
     *
     * @return: java.lang.Integer
     */
    Integer upVoteItem(@Param("itemId") Long itemId);

    /**
     * 添加投票
     *
     * @param vote
     * @return: java.lang.Integer
     */
    Integer addVote(Vote vote);

    /**
     * 添加投票-小区
     *
     * @param vote
     * @return: java.lang.Integer
     */
    Integer addVoteCommunity(Vote vote);

    /**
     * 查看名称是否唯一
     *
     * @param voteName
     * @return: java.lang.Integer
     * @author: SJiang
     * @date: 2022/11/23 11:57
     */
    Integer checkVoteNameUnique(@Param("voteName") String voteName);

    /**
     * 添加投票选项
     *
     * @param vote
     * @return: java.lang.Integer
     */
    Integer addVoteItem(Vote vote);

    /**
     * 新增投票记录
     *
     * @param vote 投票结果
     * @author yx
     * @date 2023/1/17 10:05
     */
    void insertVoteRecord(VoteRecordVO vote);

    /**
     * 查询人员是否已经投票
     *
     * @param vote
     * @return int
     * @author yx
     * @date 2023/1/17 10:06
     */
    int countByOwnerId(VoteRecordVO vote);

    /**
     * 投票统计
     *
     * @param voteId 投票id
     * @return 统计数据
     */
    VoteStatisticsVO voteStatistics(Long voteId);
}
