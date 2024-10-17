package com.wr.govern.service.party.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.govern.mapper.party.PartyMapper;
import com.wr.govern.service.party.PartyService;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.vo.MemberVO;
import com.wr.remote.govern.party.vo.PartyVO;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-25 10:31:14
 * @Desc: 党组织管理 业务实现
 */
@Service
@Log4j
public class PartyServiceImpl implements PartyService {
    @Autowired
    private PartyMapper partyMapper;

    @Override
    public List<PartyVO> getPartyList(Party party) {
        return partyMapper.getPartyList(party);
    }

    @Override
    public PartyVO getPartyById(Long partyId) {
        return partyMapper.getPartyById(partyId);
    }

    @Override
    public String checkNameUnique(Party party) {
        //查看是否已存在
        Party nameUnique = partyMapper.checkNameUnique(party);
        if (ObjectUtils.isNotEmpty(nameUnique)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Integer addParty(Party party) {
        return partyMapper.addParty(party);
    }

    @Override
    public Integer upParty(Party party) {
        return partyMapper.upParty(party);
    }

    @Override
    public AjaxResult delParty(Long partyId) {
        Integer childs = partyMapper.getChilds(partyId);
        if (childs > 0){
            return AjaxResult.error("删除失败，该党组织下存在下一级组织");
        }
        Integer num = partyMapper.delParty(partyId);
        return AjaxResult.success(num);
    }

    @Override
    public List<PartyVO> getTopPartyAll(Long estateId) {
        return partyMapper.getTopPartyAll(estateId);
    }

    @Override
    public List<PartyVO> getParentPartyAll(Long estateId) {
        return partyMapper.getParentPartyAll(estateId);
    }

    @Override
    public List<PartyVO> getMemberAll(Long estateId) {
        return partyMapper.getMemberAll(estateId);
    }

    @Override
    public String getCommunityName(Long communityId) {
        return partyMapper.getCommunityName(communityId);
    }
}
