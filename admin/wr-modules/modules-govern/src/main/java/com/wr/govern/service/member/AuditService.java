package com.wr.govern.service.member;

import com.wr.remote.govern.party.Member;
import com.wr.remote.govern.party.vo.MemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-27 16:45:23
 * @Desc: 入党审核 业务
 */
public interface AuditService {

    /**
     * 获取党员列表
     *
     * @param member
     * @return: java.util.List<com.wr.govern.entity.vo.MemberVO>
     */
    List<MemberVO> getMemberAll(MemberVO member);

    /**
     * 获取详情
     *
     * @param memberId
     * @return: com.wr.govern.entity.vo.MemberVO
     */
    MemberVO getMemberById(@Param("memberId") Long memberId);

    /**
     * 审核通过
     * @param memberId
     * @return: java.lang.Integer
     */
    Integer through(Long memberId);

    /**
     * 驳回
     * @param member
     * @return: java.lang.Integer
     */
    Integer rejected(Member member);
}
