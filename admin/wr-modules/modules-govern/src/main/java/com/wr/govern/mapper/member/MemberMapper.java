package com.wr.govern.mapper.member;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.estate.customer.decorate.Account;
import com.wr.remote.govern.party.Member;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.vo.MemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-26 14:20:20
 * @Desc: 党员管理 mapper
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 获取党员列表
     *
     * @param member
     * @return: java.util.List<com.wr.govern.entity.vo.MemberVO>
     */
    List<MemberVO> getMemberAll(MemberVO member);

    /**
     * 获取党组织
     * @param estateId
     * @return: java.util.List<com.wr.govern.entity.Party>
     */
    List<Party> getPartyAll(@Param("estateId") Long estateId);

    /**
     * 新增党员
     *
     * @param member
     * @return: java.lang.Integer
     */
    Integer addMember(Member member);

    /**
     * 获取业主，租客（账号）
     *
     * @return: java.util.List<com.wr.remote.estate.manage.contract.Owner>
     */
    List<Account> getOwnerAll(Member member);

    /**
     * 获取详情
     *
     * @param memberId
     * @return: com.wr.govern.entity.vo.MemberVO
     */
    MemberVO getMemberById(@Param("memberId") Long memberId);

    /**
     * 查看名称是否唯一
     *
     * @param member
     * @return: com.wr.govern.entity.Member
     */
    Member checkNameUnique(Member member);

    /**
     * 修改党员
     *
     * @param member
     * @return: java.lang.Integer
     */
    Integer upMember(Member member);

    /**
     * 获取用户信息
     * @param accountId
     * @return: java.lang.String
     */
    Account getAccountById(@Param("accountId") Long accountId);

    /**
     * 获取未注册党员的业主
     * @param accountId
     * @return: java.lang.String
     */
    Member getMemberOne(@Param("accountId") Long accountId);
}
