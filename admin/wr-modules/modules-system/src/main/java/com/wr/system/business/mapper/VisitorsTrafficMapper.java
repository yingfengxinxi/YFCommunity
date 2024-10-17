package com.wr.system.business.mapper;

import com.wr.system.business.domain.screen.VisitorsTraffic;

/**
 * @Author: Guo Jia
 * @Date: 2021-12-31 17:27:51
 * @Desc:
 */
public interface VisitorsTrafficMapper {

    VisitorsTraffic selectInformation(Long communityId);

    VisitorsTraffic selectInformationType(Long communityId);

    VisitorsTraffic selectInformationPatrolOrder(Long communityId);

    VisitorsTraffic selectCountPatrolOrder(Long communityId);

    VisitorsTraffic selectCountDuringMonthPatrolOrder(Long communityId);
}
