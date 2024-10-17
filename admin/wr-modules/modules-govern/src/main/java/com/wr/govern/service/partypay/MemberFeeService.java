package com.wr.govern.service.partypay;

import com.wr.remote.govern.party.Member;

/**
 * @Author: SJiang
 * @Date: 2022-11-03 14:10:56
 * @Desc: 党费归集-党费设置 业务
 */
public interface MemberFeeService {

    /**
     * 党费设置
     *
     * @param member
     * @return: java.lang.Integer
     */
    Integer partyFeeEdit(Member member);
}
