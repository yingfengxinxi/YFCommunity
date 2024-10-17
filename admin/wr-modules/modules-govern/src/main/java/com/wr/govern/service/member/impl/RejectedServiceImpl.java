package com.wr.govern.service.member.impl;

import com.wr.govern.mapper.member.RejectedMapper;
import com.wr.govern.service.member.RejectedService;
import com.wr.remote.govern.party.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-27 20:12:40
 * @Desc: 审核未通过 业务实现
 */
@Service
@Slf4j
public class RejectedServiceImpl implements RejectedService {
    @Autowired
    private RejectedMapper rejectedMapper;

    @Override
    public List<MemberVO> getMemberAll(MemberVO member) {
        List<MemberVO> memberAll = rejectedMapper.getMemberAll(member);
        // 获取详细地址
        memberAll.forEach(x->{
            String address = x.getCommunityName() + x.getBuildingName() + x.getUnitName() + x.getRoomName();
            x.setAddress(address);
        });
        return memberAll;
    }
}
