package com.wr.govern.service.member;


import com.wr.remote.govern.party.vo.MemberVO;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-27 20:12:10
 * @Desc: 审核未通过 业务
 */
public interface RejectedService {

    /**
     * 获取审核未通过列表
     *
     * @param member
     * @return: java.util.List<com.wr.govern.entity.vo.MemberVO>
     */
    List<MemberVO> getMemberAll(MemberVO member);
}
