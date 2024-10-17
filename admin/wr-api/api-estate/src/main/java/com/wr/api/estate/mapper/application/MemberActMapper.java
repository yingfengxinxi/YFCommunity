package com.wr.api.estate.mapper.application;

import com.wr.api.estate.entity.vo.application.MemberActVO;
import com.wr.remote.estate.member.MemberJoinVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 17:05:47
 * @Desc:
 */
public interface MemberActMapper {
    /**
     * 党员 活动
     *
     * @param communityId
     * @return
     */
    List<MemberActVO> searchByCommunityId(Long communityId);

    /**
     * 详情
     *
     * @param activityId
     * @return
     */
    MemberActVO detailByActivityId(@Param("activityId") Long activityId);

    /**
     * @param activityId
     */
    void updateByIdViewNum(@Param("activityId") Long activityId);

    /**
     * 保人人员列表
     *
     * @param memberJoinVo
     * @return
     */
    List<MemberJoinVo> selectJoinPeopleList(MemberJoinVo memberJoinVo);
}
