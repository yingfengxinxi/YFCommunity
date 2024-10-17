package com.wr.api.owner.service.estateapi.vote;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.estate.vote.BusVoteScope;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:25
 */
public interface BusVoteScopeService extends IService<BusVoteScope> {

    List<BusVoteScope> getList(BusVoteScope busVoteScope);
}
