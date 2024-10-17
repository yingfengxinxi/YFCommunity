package com.wr.govern.service.partypay;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.PartyPay;
import com.wr.remote.govern.party.vo.MemberVO;
import com.wr.remote.govern.party.vo.PartyPayVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-01 16:41:06
 * @Desc: 党费归集-激费统计 业务
 */
public interface StatisticsService {

    /**
     * 查询统计数据
     * @param partyPay
     * @return: java.lang.Integer
     */
    AjaxResult queryStatisticsByDate(PartyPay partyPay);

    /**
     * 获取激费列表
     * @param partyPay
     * @return: com.wr.remote.govern.party.vo.PartyPayVO
     */
    List<PartyPayVO> getPartyPayAll(PartyPay partyPay);

    /**
     * 获取党组织
     *
     * @param communityId 小区id
     * @return: java.util.List<com.wr.govern.entity.Party>
     */
    List<Party> getPartyAll( Long communityId);

    /**
     * 查党员数据
     * @return: java.util.List<com.wr.remote.govern.party.Member>
     */
    List<MemberVO> getMemberAll(Long partyId);

    /**
     * 新建激费
     * @param partyPay
     * @return: java.lang.Integer
     */
    AjaxResult addParyPay(PartyPay partyPay);

    /**
     * 归集
     * @param logIds
     */
    Integer upCollectStatus(@Param("logIds") Long [] logIds);
}
