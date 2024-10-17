package com.wr.estate.mapper.vote;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.estate.vote.BusVote;
import com.wr.remote.estate.vote.BusVoteScope;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:25
 */
public interface BusVoteScopeMapper extends BaseMapper<BusVoteScope> {


    List<BusVoteScope> getList(BusVoteScope busVoteScope);
}
