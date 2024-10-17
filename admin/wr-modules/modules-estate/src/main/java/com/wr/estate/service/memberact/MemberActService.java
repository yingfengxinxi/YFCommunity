package com.wr.estate.service.memberact;

import com.wr.remote.estate.member.MemberJoinVo;
import com.wr.remote.govern.party.MemberAct;
import com.wr.remote.govern.party.vo.MemberActVO;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-31 13:26:39
 * @Desc: 党员活动 业务
 */
public interface MemberActService {
    /**
     * 获取党员活动列表
     *
     * @param memberAct
     * @return: java.util.List<com.wr.govern.entity.vo.MemberActVO>
     */
    List<MemberActVO> getMemberActAll(MemberActVO memberAct);

    /**
     * 查看详情
     *
     * @param activityId
     * @return: com.wr.govern.entity.vo.MemberActVO
     */
    MemberActVO getMemberActById(Long activityId);

    /**
     * 查看名称是否唯一
     *
     * @param member
     * @return: com.wr.govern.entity.Member
     */
    String checkNameUnique(MemberAct member);

    /**
     * 添加活动
     *
     * @param memberAct
     * @return: java.lang.Integer
     */
    Integer addMeberAct(MemberAct memberAct);


    /**
     * 修改活动
     *
     * @param memberAct
     * @return
     */
    Integer updateMeberAct(MemberAct memberAct);

    /**
     * 删除
     *
     * @param activityIds
     * @return: java.lang.Integer
     */
    Integer delMeberAct(Long[] activityIds);

    /**
     * @param activityId
     * @return
     */
    MemberActVO getByIdMemberActInfo(Long activityId);

    /**
     * @param memberJoinVo
     * @return
     */
    List<MemberJoinVo> selectJoinPeopleList(MemberJoinVo memberJoinVo);

}
