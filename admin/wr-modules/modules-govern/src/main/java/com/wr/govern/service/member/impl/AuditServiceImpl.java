package com.wr.govern.service.member.impl;

import com.wr.govern.mapper.member.AuditMapper;
import com.wr.govern.service.member.AuditService;
import com.wr.remote.govern.party.Member;
import com.wr.remote.govern.party.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-27 16:45:56
 * @Desc: 入党审核 业务实现
 */
@Service
@Slf4j
public class AuditServiceImpl implements AuditService {
    @Autowired
    private AuditMapper auditMapper;

    @Override
    public List<MemberVO> getMemberAll(MemberVO member) {
        List<MemberVO> memberAll = auditMapper.getMemberAll(member);
        // 获取详细地址
        memberAll.forEach(x->{
            String address = x.getCommunityName() + x.getBuildingName() + x.getUnitName() + x.getRoomName();
            x.setAddress(address);
        });
        return memberAll;
    }

    @Override
    public MemberVO getMemberById(Long memberId) {
        MemberVO memberById = auditMapper.getMemberById(memberId);
        // 获取详细地址
        String address = memberById.getCommunityName() + memberById.getBuildingName() + memberById.getUnitName() + memberById.getRoomName();
        memberById.setAddress(address);
        return memberById;
    }

    @Override
    public Integer through(Long memberId) {
        return auditMapper.through(memberId);
    }

    @Override
    public Integer rejected(Member member) {
        return auditMapper.rejected(member);
    }
}
