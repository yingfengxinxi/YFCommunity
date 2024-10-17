package com.wr.api.owner.mapper.serve;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.aiot.Visual;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/15 13:08
 */
public interface VisualMapper extends BaseMapper<Visual> {

    /**
     *
     * @param communityId
     * @param buildingId
     * @param unitId
     * @return
     */
    List<Visual> getList(@Param("communityId") Long communityId, @Param("buildingId") Long buildingId, @Param("unitId") Long unitId);
}
