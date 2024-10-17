package com.wr.govern.mapper.partypay;

import com.wr.remote.govern.party.Member;

/**
 * @Author: SJiang
 * @Date: 2022-11-03 13:58:45
 * @Desc: 党费归集-党费设置 mapper
 */
public interface MemberFeeMapper {

    /**
     * 党费设置
     *
     * @param member
     * @return: java.lang.Integer
     */
    Integer partyFeeEdit(Member member);

}
