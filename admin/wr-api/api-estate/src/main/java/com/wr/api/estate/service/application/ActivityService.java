package com.wr.api.estate.service.application;

import com.wr.api.estate.entity.vo.application.ActivityVO;
import com.wr.remote.estate.activity.ActiviteManage;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 15:31:46
 * @Desc:
 */
public interface ActivityService {

    /**
     *  小区的活动
     * @param communityId,state
     * @return
     */
    List<ActivityVO> searchByCommunityId(Long communityId, String state);

    /**
     * 活动详情
     * @param activityId
     * @return
     */
    ActivityVO detailByActivityId(Long activityId);

    /**
     *
     * @param activiteManage
     * @return
     */
    List<ActiviteManage> selectJoinPeopleList(ActiviteManage activiteManage);
}
