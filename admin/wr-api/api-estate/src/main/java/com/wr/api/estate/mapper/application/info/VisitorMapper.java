package com.wr.api.estate.mapper.application.info;

import com.wr.remote.domain.vo.VisitorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/21 10:13
 */
public interface VisitorMapper {

    /**
     * @param visitorVo
     * @return
     */
    List<VisitorVo> getList(VisitorVo visitorVo);

    /**
     *
     * @param visitorId
     * @return
     */
    VisitorVo getVisitorById(@Param("visitorId") Long visitorId);

    /**
     *
     * @param communityId
     * @return
     */
    Integer getVisitCount(@Param("communityId") Long communityId);
}
