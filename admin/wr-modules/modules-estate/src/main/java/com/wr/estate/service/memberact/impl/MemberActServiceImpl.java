package com.wr.estate.service.memberact.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.estate.mapper.memberact.MemberActMapper;
import com.wr.estate.service.memberact.MemberActService;
import com.wr.remote.estate.member.MemberJoinVo;
import com.wr.remote.govern.party.MemberAct;
import com.wr.remote.govern.party.vo.MemberActVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-31 13:27:28
 * @Desc: 党员活动 业务实现
 */
@Service
@Slf4j
public class MemberActServiceImpl implements MemberActService {
    @Autowired
    private MemberActMapper memberActMapper;

    @Override
    public List<MemberActVO> getMemberActAll(MemberActVO memberAct) {
        List<MemberActVO> memberActAll = memberActMapper.getMemberActAll(memberAct);
        memberActAll.forEach(x -> {
            // 查已报名人数
            Integer toSignUpNumber = memberActMapper.getToSignUpNumber(x.getActivityId());
            x.setToSignUpNumber(toSignUpNumber);
        });
        return memberActAll;
    }

    @Override
    public MemberActVO getMemberActById(Long activityId) {
        MemberActVO memberActById = memberActMapper.getMemberActById(activityId);
        // 查已报名人数
        Integer toSignUpNumber = memberActMapper.getToSignUpNumber(memberActById.getActivityId());
        memberActById.setToSignUpNumber(toSignUpNumber);
        return memberActById;
    }

    @Override
    public String checkNameUnique(MemberAct member) {
        //查看是否已存在
        MemberAct nameUnique = memberActMapper.checkNameUnique(member);
        if (ObjectUtils.isNotEmpty(nameUnique)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Integer addMeberAct(MemberAct memberAct) {
        return memberActMapper.addMeberAct(memberAct);
    }

    @Override
    public Integer updateMeberAct(MemberAct memberAct) {
        return memberActMapper.updateMeberAct(memberAct);
    }

    @Override
    public Integer delMeberAct(Long[] activityIds) {
        return memberActMapper.delMeberAct(activityIds);
    }

    /**
     * @param activityId
     * @return
     */
    @Override
    public MemberActVO getByIdMemberActInfo(Long activityId) {
        return memberActMapper.getByIdMemberActInfo(activityId);
    }

    /**
     * @param memberJoinVo
     * @return
     */
    @Override
    public List<MemberJoinVo> selectJoinPeopleList(MemberJoinVo memberJoinVo) {
        return memberActMapper.selectJoinPeopleList(memberJoinVo);
    }
}
