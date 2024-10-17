package com.wr.api.owner.mapper.estateapi.pay;

import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.Community;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/25 20:50
 */
public interface CommunityMapper {

    /**
     * @param communityId
     * @return
     */
    Long getCommunityIdEstateId(@Param("communityId") Long communityId);

    /**
     *
     * @param estateId
     * @return
     */
    List<Community> selectAllCommunityList(@Param("estateId") Long estateId);

    /**
     *
     * @param communityId
     * @return
     */
    CommunityHik selectHikByCommunityId(@Param("communityId") Long communityId);
}
