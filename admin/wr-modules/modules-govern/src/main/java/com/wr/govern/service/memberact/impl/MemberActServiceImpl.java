package com.wr.govern.service.memberact.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.govern.mapper.memberact.MemberActMapper;
import com.wr.govern.service.memberact.MemberActService;
import com.wr.remote.govern.party.MemberAct;
import com.wr.remote.govern.party.vo.MemberActVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        memberActAll.forEach(x->{
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
    public Integer delMeberAct(Long [] activityIds) {
        return memberActMapper.delMeberAct(activityIds);
    }

    @Override
    public List<Map<String,Object>> findListMemberPeople(Map<String,Object> map) {
        return memberActMapper.findListMemberPeople(map);
    }
}
