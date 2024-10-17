package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.vo.application.serve.InvestVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 11:22:18
 * @Desc:
 */
public interface InvestMapper {
    /**
     * @param communityId
     * @param investType
     * @return
     */
    List<InvestVO> searchByCommunityId(@Param("communityId") Long communityId, @Param("investType") String investType);

    /**
     * @param estateId
     * @return
     */
    List<String> getByEstateIdInvestId(@Param("estateId") Long estateId, @Param("communityId") Long communityId);

    /**
     * 公告 阅读数 +1
     *
     * @param id
     * @return
     */
    int increaseReadNumById(Long id);

    /**
     * 详情
     *
     * @param investId
     * @return
     */
    InvestVO detailByInvestId(@Param("investId") Long investId);
}
