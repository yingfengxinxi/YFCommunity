package com.wr.api.estate.service.application;

import com.wr.api.estate.entity.vo.application.MemberActVO;
import com.wr.remote.estate.member.MemberJoinVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 18:56:59
 * @Desc:
 */
public interface MemberActService {

    /**
     * 党员 活动
     * @param communityId
     * @return
     */
    List<MemberActVO> searchByCommunityId(Long communityId);

    /**
     * 详情
     * @param activityId
     * @return
     */
    MemberActVO detailByActivityId(Long activityId);

    /**
     *
     * @param memberJoinVo
     * @return
     */
    List<MemberJoinVo> selectJoinPeopleList(MemberJoinVo memberJoinVo);
}
