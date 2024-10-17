package com.wr.estate.service.vote.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.mapper.vote.BusVoteMapper;
import com.wr.estate.mapper.vote.BusVoteOwnerMapper;
import com.wr.estate.mapper.vote.BusVoteScopeMapper;
import com.wr.estate.service.vote.BusVoteOwnerService;
import com.wr.estate.service.vote.BusVoteService;
import com.wr.remote.domain.vo.BusVoteVo;
import com.wr.remote.estate.vote.BusVote;
import com.wr.remote.estate.vote.BusVoteOwner;
import com.wr.remote.estate.vote.BusVoteScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:26
 */
@Service
public class BusVoteOwnerServiceImpl extends ServiceImpl<BusVoteOwnerMapper, BusVoteOwner> implements BusVoteOwnerService {


    /**
     *
     * @param voteId
     * @param roomId
     * @return
     */
    @Override
    public List<BusVoteOwner> getList(String voteId,Long roomId) {
        return baseMapper.getList(voteId, roomId);
    }
}
