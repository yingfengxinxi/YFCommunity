package com.wr.estate.service.impl.financial;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.financial.CashApplyMapper;
import com.wr.estate.service.financial.CashApplyService;
import com.wr.estate.service.info.CommunityNoticeService;
import com.wr.remote.estate.financial.EstateCash;
import com.wr.remote.estate.info.CommunityNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:40:31
 * @Desc:
 */
@Service
public class CashApplyServiceImpl implements CashApplyService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CashApplyMapper cashApplyMapper;

    @Autowired
    private CommunityNoticeService communityNoticeService;

    @Override
    public List<EstateCash> selectList(EstateCash estateCash) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (null != estateCash.getApplyTime()) {
            Date applyTime = estateCash.getApplyTime();
            String format1 = sdf.format(applyTime);
            estateCash.setYears(format1);
        }
        List<EstateCash> list = cashApplyMapper.selectList(estateCash);
        for (EstateCash cash : list) {
            EstateCash estateCash1 = cashApplyMapper.voteResultSum(cash.getVoteId());
            Long agreeSum = estateCash1.getAgreeSum();
            Long disagreeSum = estateCash1.getDisagreeSum();
            Long Sum = agreeSum + disagreeSum;
            if (Sum == 0) {
                cash.setAgreeRate("0.00%");
            } else {
                String format = String.format("%.2f", ((agreeSum.doubleValue() / Sum.doubleValue()) * 100));
                cash.setAgreeRate(format + "%");
            }
        }

        return list;
    }

    @Override
    public EstateCash voteResultSum(Long voteId) {
        return cashApplyMapper.voteResultSum(voteId);
    }

    @Override
    public BigDecimal cashCountByTime(String yearVo, Long communityId) {
        return cashApplyMapper.cashCountByTime(yearVo, communityId);
    }

    @Override
    public BigDecimal allCashCount(EstateCash estateCash) {
        return cashApplyMapper.allCashCount(estateCash);
    }

    @Override
    public EstateCash voteInfoById(Long voteId) {
        return cashApplyMapper.voteInfoById(voteId);
    }

    @Override
    public List<EstateCash> ownerVoteList(EstateCash estateCash) {
        return cashApplyMapper.ownerVoteList(estateCash);
    }

    @Override
    public int updateApply(EstateCash estateCash) {
        estateCash.setAuditTime(DateUtils.getNowDate());
        return cashApplyMapper.updateApply(estateCash);
    }

    @Override
    public int insertGrant(EstateCash estateCash) {
        String username = SecurityUtils.getUsername();
        estateCash.setCreateBy(username);
        return cashApplyMapper.insertGrant(estateCash);
    }

    /**
     * 发布公告
     *
     * @param voteId 基金投票id
     */
    @Override
    public void bulletin(Long voteId) {
        String noticeTemplate = "同意人数：%s人\n不同意人数：%s\n同意率：%s%%";
        // 查询基金投票详情
        EstateCash estateCash = this.voteInfoById(voteId);
        // 生成公告并新增
        CommunityNotice notice = new CommunityNotice();
        notice.setCreateBy(SecurityUtils.getUsername());
        notice.setNoticeTitle(estateCash.getVoteTitle());

        EstateCash estateCash1 = cashApplyMapper.voteResultSum(voteId);
        Long agreeSum = estateCash1.getAgreeSum();
        Long disagreeSum = estateCash1.getDisagreeSum();
        Long Sum = agreeSum + disagreeSum;
        String rate;
        if (agreeSum == 0 || Sum == 0) {
            rate = "0.00%";
        } else {
            rate = String.format("%.2f", (float) ((agreeSum / Sum) * 100));
        }
        notice.setRange("0");
        notice.setContent(String.format(noticeTemplate, agreeSum, disagreeSum, rate));
        notice.setCommunityIds(new Long[]{estateCash.getCommunityId()});
        communityNoticeService.insertNotice(notice);
        // 已发布
        cashApplyMapper.updateHasNotice(voteId);
    }
}
