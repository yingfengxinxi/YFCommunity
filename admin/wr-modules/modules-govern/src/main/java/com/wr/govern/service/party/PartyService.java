package com.wr.govern.service.party;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.vo.MemberVO;
import com.wr.remote.govern.party.vo.PartyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-25 10:30:28
 * @Desc: 党组织管理 业务
 */
public interface PartyService {
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
    String checkNameUnique(Party party);

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
    AjaxResult delParty(Long partyId);

    /**
     * 查询顶层党组织
     * @param estateId
     * @return: java.util.List<com.wr.remote.govern.party.vo.PartyVO>
     * @author: SJiang
     **/
    List<PartyVO> getTopPartyAll(Long estateId);

    /**
     * 查询上级党组织
     *
     * @param estateId
     * @return: java.util.List<com.wr.remote.govern.party.vo.PartyVO>
     * @author: SJiang
     **/
    List<PartyVO> getParentPartyAll(Long estateId);

    /**
     * 获取所有党员（审核通过）
     *
     * @return: java.util.List<com.wr.govern.entity.Member>
     */
    List<PartyVO> getMemberAll(Long estateId);

    /**
     * 获取小区名称
     *
     * @param communityId
     * @return: java.lang.String
     */
    String getCommunityName(@Param("communityId") Long communityId);
}
