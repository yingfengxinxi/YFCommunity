package com.wr.estate.mapper.vote;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.estate.vote.BusVoteOwner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 16:21
 */
public interface BusVoteOwnerMapper extends BaseMapper<BusVoteOwner> {


    /**
     * @param voteId
     * @param roomId
     * @return
     */
    List<BusVoteOwner> getList(@Param("voteId") String voteId, @Param("roomId") Long roomId);
}
