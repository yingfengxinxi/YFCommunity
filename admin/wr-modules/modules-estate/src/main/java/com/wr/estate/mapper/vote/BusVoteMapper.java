package com.wr.estate.mapper.vote;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.domain.vo.BusVoteResultVo;
import com.wr.remote.estate.vote.BusVote;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:25
 */
public interface BusVoteMapper extends BaseMapper<BusVote> {

    /**
     * @param busVote
     * @return
     */
    List<BusVote> getList(BusVote busVote);

    /**
     * @param id
     * @return
     */
    List<BusVoteResultVo> getByIdCount(@Param("id") String id, @Param("ownerStatus") String ownerStatus);

    /**
     *
     * @param voteId
     * @param ownerStatus
     * @return
     */
    Integer getByVoteIdStatusCount(@Param("voteId") String voteId, @Param("ownerStatus") String ownerStatus);


    /**
     * 应投总数量
     *
     * @param voteId
     * @return
     */
    List<String> getVoteIdIds(@Param("voteId") String voteId);

    /**
     * 已投票数
     *
     * @param voteId
     * @return
     */
    Integer getByVoteIdYtCount(@Param("voteId") String voteId);

    /**
     * 面积
     *
     * @param id
     * @param ownerStatusList
     * @return
     */
    BigDecimal getByIdStatusArea(@Param("id") String id, @Param("ownerStatusList") List<String> ownerStatusList);

    /**
     * 应投票总面积
     *
     * @param voteScopeIds
     * @return
     */
    BigDecimal getVoteScopeIdAreaTotal(@Param("voteScopeIds") List<String> voteScopeIds);

    /**
     *
     * @param voteScopeIds
     * @return
     */
    BigDecimal getVoteScopeIdYtAreaTotal(@Param("voteScopeIds") List<String> voteScopeIds);
}
