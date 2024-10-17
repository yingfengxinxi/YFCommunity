package com.wr.api.owner.mapper.estateapi.vote;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.estate.center.vo.RoomQueryVO;
import com.wr.remote.estate.vote.BusVoteOwner;
import com.wr.remote.estate.vote.BusVoteScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author lvzy
 * @Date 2024/2/23 16:21
 */
public interface BusVoteOwnerMapper extends BaseMapper<BusVoteOwner> {

    /**
     * @param voteScopeId
     * @return
     */
    List<BusVoteOwner> getList(@Param("voteScopeId") String voteScopeId);

    /**
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
    Long findOneOwnerIdByOwner(@Param("id") Long id);

    /**
    * @Author SUNk
    * @Description 查询状态
    * @Date 15:25 2024/5/15
    * @Param [voteId, accountId]
    * @return java.lang.String
    **/
    Map<String, Object> findOneStatusByAccount(@Param("id") String id, @Param("accountId") Long accountId, @Param("accountId2") Long accountId2);
}
