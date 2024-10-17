package com.wr.api.estate.service.impl.application;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.wr.api.estate.entity.vo.application.MemberActVO;
import com.wr.api.estate.mapper.application.MemberActMapper;
import com.wr.api.estate.service.application.MemberActService;
import com.wr.remote.estate.member.MemberJoinVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 18:57:31
 * @Desc:
 */
@Service
public class MemberActServiceImpl implements MemberActService {
    @Resource
    private MemberActMapper memberActMapper;

    @Override
    public List<MemberActVO> searchByCommunityId(Long communityId) {
        List<MemberActVO> memberActVOS = memberActMapper.searchByCommunityId(communityId);
        if (CollectionUtils.isNotEmpty(memberActVOS)) {
            memberActVOS.stream().forEach(memberActVO -> {
                String state = memberActVO.getState();
                if (StringUtils.isNotEmpty(state)) {
                    memberActVO.setState(stateName(state));
                }

            });
        }
        return memberActVOS;
    }


    private String stateName(String state) {
        if ("0".equals(state)) {
            state = "进行中";
        }
        if ("1".equals(state)) {
            state = "未开始";
        }
        if ("2".equals(state)) {
            state = "已结束";
        }
        return state;
    }

    @Override
    public MemberActVO detailByActivityId(Long activityId) {
        memberActMapper.updateByIdViewNum(activityId);
        MemberActVO memberActVO = memberActMapper.detailByActivityId(activityId);
        String state = memberActVO.getState();
        if (StringUtils.isNotEmpty(state)) {
            memberActVO.setState(stateName(state));
        }

        return memberActVO;
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
