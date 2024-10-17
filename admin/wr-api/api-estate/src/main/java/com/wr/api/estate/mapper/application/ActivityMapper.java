package com.wr.api.estate.mapper.application;


import com.wr.api.estate.entity.vo.application.ActivityVO;
import com.wr.remote.estate.activity.ActiviteManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 14:47:35
 * @Desc:
 */
public interface ActivityMapper {

    /**
     *  小区的活动
     * @param communityId
     * @param state
     * @return
     */
    List<ActivityVO> searchByCommunityId(@Param("communityId") Long communityId,@Param("state") String state);

    /**
     * 活动详情
     * @param activityId
     * @return
     */
    ActivityVO detailByActivityId(@Param("activityId") Long activityId);

    void updateByIdViewNumber(@Param("activityId") Long activityId);

    List<ActiviteManage> selectJoinPeopleList(ActiviteManage activiteManage);
}
