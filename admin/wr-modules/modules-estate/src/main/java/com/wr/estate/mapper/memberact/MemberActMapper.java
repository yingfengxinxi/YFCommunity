package com.wr.estate.mapper.memberact;

import com.wr.remote.estate.member.MemberJoinVo;
import com.wr.remote.govern.party.MemberAct;
import com.wr.remote.govern.party.vo.MemberActVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-31 11:15:14
 * @Desc: 党员活动 mapper
 */
public interface MemberActMapper {

    /**
     * 获取党员活动列表
     *
     * @param memberAct
     * @return: java.util.List<com.wr.govern.entity.vo.MemberActVO>
     */
    List<MemberActVO> getMemberActAll(MemberActVO memberAct);

    /**
     * 查已报名人数
     *
     * @param activityId
     * @return: java.lang.Integer
     */
    Integer getToSignUpNumber(@Param("activityId") Long activityId);

    /**
     * @param activityId
     * @return
     */
    MemberActVO getByIdMemberActInfo(@Param("activityId") Long activityId);

    /**
     *
     * @param memberJoinVo
     * @return
     */
    List<MemberJoinVo> selectJoinPeopleList(MemberJoinVo memberJoinVo);

    /**
     * 查看详情
     *
     * @param activityId
     * @return: com.wr.govern.entity.vo.MemberActVO
     */
    MemberActVO getMemberActById(@Param("activityId") Long activityId);

    /**
     * 查看名称是否唯一
     *
     * @param member
     * @return: com.wr.govern.entity.Member
     */
    MemberAct checkNameUnique(MemberAct member);

    /**
     * 添加活动
     *
     * @param memberAct
     * @return: java.lang.Integer
     */
    Integer addMeberAct(MemberAct memberAct);


    Integer updateMeberAct(MemberAct memberAct);

    /**
     * 删除
     *
     * @param activityIds
     * @return: java.lang.Integer
     */
    Integer delMeberAct(@Param("activityIds") Long[] activityIds);

}
