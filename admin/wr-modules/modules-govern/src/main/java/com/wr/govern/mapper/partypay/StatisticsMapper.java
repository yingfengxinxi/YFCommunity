package com.wr.govern.mapper.partypay;

import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.PartyPay;
import com.wr.remote.govern.party.vo.MemberVO;
import com.wr.remote.govern.party.vo.PartyPayVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-01 14:40:05
 * @Desc: 党费归集-激费统计 mapper
 */
public interface StatisticsMapper {

    /**
     * 获取党员人数
     *
     * @param communityId
     * @return: java.lang.Integer
     */
    Integer getTotalMember(@Param("communityId") Long communityId);

    /**
     * 根据激费类型获取激费人数
     *
     * @param partyPay
     * @return: java.lang.Integer
     */
    Integer getPartyPayNumByType(PartyPay partyPay);

    /**
     * 根据激费方式获取激费人数
     *
     * @param partyPay
     * @return: java.lang.Integer
     */
    Integer getPartyPayNumByWay(PartyPay partyPay);

    /**
     * 根据类型查激费总金额
     *
     * @param partyPay
     * @return: java.math.BigDecimal
     */
    BigDecimal getPartyPaySumType(PartyPay partyPay);

    /**
     * 根据方式查激费总金额
     *
     * @param partyPay
     * @return: java.math.BigDecimal
     */
    BigDecimal getPartyPaySumWay(PartyPay partyPay);

    /**
     * 获取激费列表
     *
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
    List<Party> getPartyAll(@Param("communityId") Long communityId);

    /**
     * 查党员数据
     *
     * @param estateId
     * @return: java.util.List<com.wr.remote.govern.party.Member>
     */
    List<MemberVO> getMemberAll(@Param("estateId") Long estateId, @Param("partyId") Long partyId);

    /**
     * 新建激费
     *
     * @param partyPay
     * @return: java.lang.Integer
     */
    Integer addParyPay(PartyPay partyPay);

    /**
     * 查看是否唯一
     *
     * @param partyPay
     * @return: com.wr.remote.govern.party.PartyPay
     */
    PartyPay checkUnique(PartyPay partyPay);

    /**
     * 归集
     *
     * @param logIds
     * @param collectName
     */
    Integer upCollectStatus(@Param("logIds") Long[] logIds, @Param("collectName") String collectName);

}
