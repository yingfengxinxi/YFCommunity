package com.wr.api.owner.service.estateapi.vote.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.vote.BusVoteOwnerMapper;
import com.wr.api.owner.service.estateapi.vote.BusVoteOwnerService;
import com.wr.remote.estate.center.vo.RoomQueryVO;
import com.wr.remote.estate.vote.BusVoteOwner;
import com.wr.remote.estate.vote.BusVoteScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:26
 */
@Service
public class BusVoteOwnerServiceImpl extends ServiceImpl<BusVoteOwnerMapper, BusVoteOwner> implements BusVoteOwnerService {


    /**
     *
     * @param voteScopeId
     * @return
     */
    @Override
    public List<BusVoteOwner> getList(String voteScopeId) {
        return baseMapper.getList(voteScopeId);
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<BusVoteScope> getVoteListRooms(RoomQueryVO query) {
        return baseMapper.getVoteListRooms(query);
    }

    @Override
    public Long findOneOwnerIdByOwner(Long id) {
        return baseMapper.findOneOwnerIdByOwner(id);
    }

    @Override
    public Map<String, Object> findOneStatusByAccount(String voteId, Long accountId, Long accountId2) {
        return baseMapper.findOneStatusByAccount( voteId,accountId,accountId2 );
    }
}
