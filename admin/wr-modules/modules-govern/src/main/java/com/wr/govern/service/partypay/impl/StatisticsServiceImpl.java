package com.wr.govern.service.partypay.impl;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.member.MemberMapper;
import com.wr.govern.mapper.party.PartyMapper;
import com.wr.govern.mapper.partypay.StatisticsMapper;
import com.wr.govern.service.partypay.StatisticsService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.PartyPay;
import com.wr.remote.govern.party.vo.MemberVO;
import com.wr.remote.govern.party.vo.PartyPayVO;
import com.wr.remote.govern.party.vo.PartyVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-11-01 16:42:09
 * @Desc: 激费统计 业务实现
 */
@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticalMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PartyMapper partyMapper;

    @Override
    public AjaxResult queryStatisticsByDate(PartyPay partyPay) {
        AjaxResult ajaxResult = new AjaxResult();
        // 缴费月份转换
        String dateToStr = DateUtils.parseDateToStr("yyyy-MM", DateUtils.parseDate(partyPay.getPayMonth()));
        partyPay.setPayMonth(dateToStr);
        // 获取党员人数
        Integer totalMember = statisticalMapper.getTotalMember(partyPay.getCommunityId());
        // 获取已激费人数
        Integer payersNum = statisticalMapper.getPartyPayNumByType(partyPay);
        // 已激费总金额
        BigDecimal moneySum = statisticalMapper.getPartyPaySumType(partyPay);
        ajaxResult.put("totalMember", totalMember);
        ajaxResult.put("payersNum", payersNum);
        ajaxResult.put("moneySum", moneySum);

        // 获取正常激费人数
        partyPay.setPayType("1");
        Integer normalPayNum = statisticalMapper.getPartyPayNumByType(partyPay);
        // 正常激费总金额
        BigDecimal normalPaySum = statisticalMapper.getPartyPaySumType(partyPay);
        ajaxResult.put("normalPayNum", normalPayNum);
        ajaxResult.put("normalPaySum", normalPaySum);

        // 获取预激费人数
        partyPay.setPayType("2");
        Integer prepaidNum = statisticalMapper.getPartyPayNumByType(partyPay);
        // 预激总金额
        BigDecimal prepaidSum = statisticalMapper.getPartyPaySumType(partyPay);
        ajaxResult.put("prepaidNum", prepaidNum);
        ajaxResult.put("prepaidSum", prepaidSum);

        // 获取补激人数
        partyPay.setPayType("3");
        Integer backFeeNum = statisticalMapper.getPartyPayNumByType(partyPay);
        // 补激总金额
        BigDecimal backFeeSum = statisticalMapper.getPartyPaySumType(partyPay);
        ajaxResult.put("backFeeNum", backFeeNum);
        ajaxResult.put("backFeeSum", backFeeSum);

        // 获取线下激费人数
        partyPay.setPayWay("3");
        Integer offlineNum = statisticalMapper.getPartyPayNumByWay(partyPay);
        // 线下激费总金额
        BigDecimal offlineSum = statisticalMapper.getPartyPaySumWay(partyPay);
        ajaxResult.put("offlineNum", offlineNum);
        ajaxResult.put("offlineSum", offlineSum);
        return ajaxResult;
    }

    @Override
    public List<PartyPayVO> getPartyPayAll(PartyPay partyPay) {
        return statisticalMapper.getPartyPayAll(partyPay);
    }

    @Override
    public List<Party> getPartyAll(Long communityId) {
        return statisticalMapper.getPartyAll(communityId);
    }

    @Override
    public List<MemberVO> getMemberAll(Long partyId) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long estateId = sysUser.getEstateId();
        return statisticalMapper.getMemberAll(estateId, partyId);
    }

    @Override
    public AjaxResult addParyPay(PartyPay partyPay) {
        // 缴费月份转换
        String dateToStr = DateUtils.parseDateToStr("yyyy-MM", DateUtils.parseDate(partyPay.getPayMonth()));
        partyPay.setPayMonth(dateToStr);
        // 获取党组织信息
        PartyVO partyById = partyMapper.getPartyById(partyPay.getPartyId());
        if (ObjectUtils.isEmpty(partyById)) {
            return AjaxResult.error("激费失败，该党组织信息不不存在");
        }
        // 获取党员信息
        MemberVO memberById = memberMapper.getMemberById(partyPay.getMemberId());
        if (ObjectUtils.isEmpty(memberById)) {
            return AjaxResult.error("激费失败，该党员信息不存在");
        }
        // 查看当月是否已激费
        PartyPay unique = statisticalMapper.checkUnique(partyPay);
        if (ObjectUtils.isNotEmpty(unique)) {
            return AjaxResult.error("激费失败，该'" + memberById.getMemberName() + "'党员本月党费已激过");
        }
        // 所属小区
        partyPay.setCommunityId(partyById.getCommunityId());
        // 党员名称
        partyPay.setMemberName(memberById.getMemberName());
        // 党员手机号
        partyPay.setMemberPhone(memberById.getMemberPhone());
        // 要激费的月份
        Date date = DateUtils.dateTime("yyyy-MM", partyPay.getPayMonth());
        int dateOne = DateUtils.getYearMonth(date);
        // 当前年月份
        int dateTwo = DateUtils.getYearMonth(new Date());
        // 正常激费
        if (dateOne == dateTwo) {
            partyPay.setPayType("1");
        } else if (dateOne > dateTwo) {
            // 预激
            partyPay.setPayType("2");
        } else {
            // 补激
            partyPay.setPayType("3");
        }
        partyPay.setPartyFee(memberById.getPartyFee());
        // 激费方式（线下）
        partyPay.setPayWay("3");
        Integer num = statisticalMapper.addParyPay(partyPay);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @Override
    public Integer upCollectStatus(Long[] logIds) {
        String username = SecurityUtils.getUsername();
        Integer num = statisticalMapper.upCollectStatus(logIds, username);
        return num;
    }
}
