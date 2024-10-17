package com.wr.api.owner.service.estateapi.vote.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.vote.BusVoteScopeMapper;
import com.wr.api.owner.service.estateapi.vote.BusVoteScopeService;
import com.wr.remote.estate.vote.BusVoteScope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:26
 */
@Service
public class BusVoteScopeServiceImpl extends ServiceImpl<BusVoteScopeMapper, BusVoteScope> implements BusVoteScopeService {


    @Override
    public List<BusVoteScope> getList(BusVoteScope busVoteScope) {
        return baseMapper.getList(busVoteScope);
    }
}
