package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.MajordomoVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 16:42:39
 * @Desc: 服务-专属管家 mapper
 */
public interface MajordomoMapper {

    /**
     * 获取楼栋专属管家
     * @param communityId 小区id
     * @param buildingId 楼栋id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.MajordomoVO>
     */
    MajordomoVO getMajordomo(@Param("communityId") Long communityId,@Param("buildingId") Long buildingId);
}
