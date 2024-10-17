package com.wr.govern.mapper.member;


import com.wr.remote.govern.party.vo.MemberVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-27 20:05:50
 * @Desc: 审核未通过 mapper
 */
public interface RejectedMapper {

    /**
     * 获取审核未通过列表
     *
     * @param member
     * @return: java.util.List<com.wr.govern.entity.vo.MemberVO>
     */
    List<MemberVO> getMemberAll(MemberVO member);
}
