package com.wr.api.owner.service.estateapi.vote;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.vo.BusVoteVo;
import com.wr.remote.estate.vote.BusVote;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:25
 */
public interface BusVoteService extends IService<BusVote> {
    /**
     *
     * @param busVote
     * @return
     */
    List<BusVote> getList(BusVote busVote);

    /**
     *
     * @param busVoteVo
     */
    void saveVote(BusVoteVo busVoteVo);

    /**
     *
     * @param busVoteVo
     */
    void updateVote(BusVoteVo busVoteVo);

    BusVoteVo get(String id);

    void removeVote(String id);

    BusVoteVo getResult(String id);
}
