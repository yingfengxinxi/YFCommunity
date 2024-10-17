package com.wr.govern.service.member.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.UserConstants;
import com.wr.govern.mapper.member.MemberMapper;
import com.wr.govern.service.member.MemberService;
import com.wr.remote.estate.customer.decorate.Account;
import com.wr.remote.govern.party.Member;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-26 16:13:47
 * @Desc: 党员管理 业务实现
 */
@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public List<MemberVO> getMemberAll(MemberVO member) {
        List<MemberVO> memberAll = memberMapper.getMemberAll(member);
        // 获取详细地址
        memberAll.forEach(x -> {
            String address = x.getCommunityName() + " " + x.getBuildingName() + " " + x.getUnitName() + " " + x.getRoomName();
            x.setAddress(address);
        });
        return memberAll;
    }

    @Override
    public List<Party> getPartyAll(Long estateId) {
        return memberMapper.getPartyAll(estateId);
    }

    @Override
    public Integer addMember(Member member) {
        return memberMapper.addMember(member);
    }

    @Override
    public List<Account> getOwnerAll(Member member) {
        List<Account> ownerAll = memberMapper.getOwnerAll(member);
        List<Account> ownerAlls = new ArrayList<>();
        // 筛选未注册过党员或审核未通过的业主
        ownerAll.forEach(x-> {
            Member members = memberMapper.getMemberOne(x.getAccountId());
            if (ObjectUtils.isEmpty(members) || "2".equals((members.getAuditStatus()))){
                Account account = new Account();
                account.setAccountId(x.getAccountId());
                account.setAccountName(x.getAccountName());
                ownerAlls.add(account);
            }
        });
        return ownerAlls;
    }

    @Override
    public MemberVO getMemberById(Long memberId) {
        MemberVO memberById = memberMapper.getMemberById(memberId);
        // 获取详细地址
        String address = memberById.getCommunityName() + " " + memberById.getBuildingName() + " " + memberById.getUnitName() + " " + memberById.getRoomName();
        memberById.setAddress(address);
        return memberById;
    }

    @Override
    public String checkNameUnique(Member member) {
        // 查看是否已存在
        Member nameUnique = memberMapper.checkNameUnique(member);
        if (ObjectUtils.isNotEmpty(nameUnique)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Integer upMember(Member member) {
        return memberMapper.upMember(member);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return memberMapper.getAccountById(accountId);
    }
}
