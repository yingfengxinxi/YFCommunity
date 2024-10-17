package com.wr.govern.service.partypay.impl;

import com.wr.govern.mapper.partypay.MemberFeeMapper;
import com.wr.govern.service.partypay.MemberFeeService;
import com.wr.remote.govern.party.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: SJiang
 * @Date: 2022-11-03 14:11:43
 * @Desc: 党费归集-党费设置 业务实现
 */
@Service
@Slf4j
public class MemberFeeServiceImpl implements MemberFeeService {
    @Autowired
    private MemberFeeMapper memberFeeMapper;

    @Override
    public Integer partyFeeEdit(Member member) {
        return memberFeeMapper.partyFeeEdit(member);
    }
}
