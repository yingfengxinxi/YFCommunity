package com.wr.estate.service.vote;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.vo.BusVoteVo;
import com.wr.remote.estate.vote.BusVote;
import com.wr.remote.estate.vote.BusVoteOwner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:25
 */
public interface BusVoteOwnerService extends IService<BusVoteOwner> {

    /**
     *
     * @param voteId
     * @param roomId
     * @return
     */
    List<BusVoteOwner> getList(String voteId,Long roomId);
}
