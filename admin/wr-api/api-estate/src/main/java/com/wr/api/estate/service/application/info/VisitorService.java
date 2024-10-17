package com.wr.api.estate.service.application.info;

import com.wr.remote.domain.vo.VisitorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/21 10:13
 */
public interface VisitorService {

    /**
     * @param visitorVo
     * @return
     */
    List<VisitorVo> getList(VisitorVo visitorVo);

    VisitorVo getVisitorById(Long visitorId);

    /**
     * @param communityId
     * @return
     */
    Integer getVisitCount(Long communityId);
}
