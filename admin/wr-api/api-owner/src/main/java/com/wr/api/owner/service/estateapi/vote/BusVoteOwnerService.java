package com.wr.api.owner.service.estateapi.vote;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.estate.center.vo.RoomQueryVO;
import com.wr.remote.estate.vote.BusVoteOwner;
import com.wr.remote.estate.vote.BusVoteScope;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:25
 */
public interface BusVoteOwnerService extends IService<BusVoteOwner> {

    /**
     * @param voteScopeId
     * @return
     */
    List<BusVoteOwner> getList(String voteScopeId);

    /**
     *
     * @param query
     * @return
     */
    List<BusVoteScope> getVoteListRooms(RoomQueryVO query);

    /**
    * @Author SUNk
    * @Description 查询配偶或业主的业主账号ID
    * @Date 15:06 2024/5/15
    * @Param [id]
    * @return java.lang.Long
    **/
    Long findOneOwnerIdByOwner(Long id);

    /**
    * @Author SUNk
    * @Description 查询状态
    * @Date 15:25 2024/5/15
    * @Param [voteId, accountId]
    * @return java.lang.String
    **/
    Map<String, Object> findOneStatusByAccount(String voteId, Long accountId, Long accountId2);
}
