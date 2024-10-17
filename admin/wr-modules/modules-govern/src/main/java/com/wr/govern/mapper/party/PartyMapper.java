package com.wr.govern.mapper.party;


import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.vo.MemberVO;
import com.wr.remote.govern.party.vo.PartyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-25 09:34:01
 * @Desc: 党组织管理mapper
 */
public interface PartyMapper {
    /**
     * 获取党组织列表
     *
     * @param party
     * @return: java.util.List<com.wr.govern.entity.Party>
     */
    List<PartyVO> getPartyList(Party party);

    /**
     * 获取党组织详情
     *
     * @param partyId
     * @return: java.util.List<com.wr.govern.entity.Party>
     */
    PartyVO getPartyById(@Param("partyId") Long partyId);

    /**
     * 查看名称是否唯一
     *
     * @param party
     * @return: partyId
     */
    Party checkNameUnique(Party party);

    /**
     * 新增党组织
     *
     * @param party
     * @return: java.lang.Integer
     */
    Integer addParty(Party party);

    /**
     * 修改党组织
     *
     * @param party
     * @return: java.lang.Integer
     */
    Integer upParty(Party party);

    /**
     * 删除党组织
     *
     * @param partyId
     * @return: java.lang.Integer
     */
    Integer delParty(@Param("partyId") Long partyId);

    /**
     * 查询顶层党组织
     *
     * @return: java.util.List<com.wr.govern.entity.vo.PartyVO>
     * @author: SJiang
     * @date: 2022/10/25 16:28
     */
    List<PartyVO> getTopPartyAll(@Param("estateId") Long estateId);

    /**
     * 查询上级党组织
     *
     * @return: java.util.List<com.wr.govern.entity.vo.PartyVO>
     * @author: SJiang
     * @date: 2022/10/25 16:28
     */
    List<PartyVO> getParentPartyAll(@Param("estateId") Long estateId);

    /**
     * 获取所有党员（审核通过）
     * @return: java.util.List<com.wr.govern.entity.Member>
     */
    List<PartyVO> getMemberAll(@Param("estateId") Long estateId);

    /**
     * 获取小区名称
     * @param communityId
     * @return: java.lang.String
     */
    String getCommunityName(@Param("communityId") Long communityId);

    /**
     * 查看是否存在子级
     * @param partyId
     * @return: java.lang.Integer
     */
   Integer getChilds(@Param("partyId") Long partyId);
}
