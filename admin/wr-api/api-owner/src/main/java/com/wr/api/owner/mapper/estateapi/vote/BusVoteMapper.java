package com.wr.api.owner.mapper.estateapi.vote;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.domain.vo.BusVoteResultVo;
import com.wr.remote.estate.vote.BusVote;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:25
 */
public interface BusVoteMapper extends BaseMapper<BusVote> {

    /**
     * @param busVote
     * @return
     */
    List<BusVote> getList(BusVote busVote);
    /**
    * @Author SUNk
    * @Description 统计投票人数
    * @Date 15:25 2024/5/11
    * @Param [busVote]
    * @return java.lang.String
    **/
    String findCountNumVote(BusVote busVote);

    /**
     * @param id
     * @return
     */
    List<BusVoteResultVo> getByIdCount(@Param("id") String id, @Param("ownerStatus") String ownerStatus);

    /**
     * @param id
     * @param ownerStatusList
     * @return
     */
    Integer getByIdStatusCount(@Param("id") String id, @Param("ownerStatusList") List<String> ownerStatusList);

    /**
     * 面积
     *
     * @param id
     * @param ownerStatusList
     * @return
     */
    BigDecimal getByIdStatusArea(@Param("id") String id, @Param("ownerStatusList") List<String> ownerStatusList);
}
