package com.wr.estate.web.info.info;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.info.HeadlineMapper;
import com.wr.estate.mapper.info.InvestMapper;
import com.wr.estate.service.info.InvestService;
import com.wr.remote.estate.info.Headline;
import com.wr.remote.estate.info.Invest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-13 09:51:52
 * @Desc:
 */
@Service
public class InvestServiceImpl implements InvestService {

  @Autowired
  private InvestMapper investMapper;

  @Autowired
  private HeadlineMapper headlineMapper;

  @Override
  public List<Invest> selectList(Invest invest) {
    List<Invest> list = investMapper.selectList(invest);
    for (Invest invest1 : list) {
      if ("1".equals(invest1.getInvestStatus())) {
        invest1.setUpdateTime(null);
      }
    }
    return list;
  }

  @Override
  public Invest selectEstateNameById(Long estateId) {
    return investMapper.selectEstateNameById(SecurityUtils.getLoginUser().getSysUser().getEstateId());
  }

  @Override
  public Invest selectInvestById(Long investId) {
    Invest invest = investMapper.selectInvestById(investId);
    if ("1".equals(invest.getInvestStatus())) {
      invest.setUpdateTime(null);
    }
    return invest;
  }

  @Override
  public Integer insertInvest(Invest invest) {
    int row = investMapper.insertInvest(invest);
    return row;
  }

  @Override
  public Integer updateInvest(Invest invest) {
    int row = investMapper.updateInvest(invest);
    return row;
  }

  @Override
  public Integer updateStatus(Long investId) {
    int row = investMapper.updateStatus(investId);
    return row;
  }

  @Override
  public List<Invest> checkTitleUnique(Invest invest) {
    List<Invest> list = investMapper.checkTitleUnique(invest);
    return list;
  }

  @Override
  public List<Invest> checkTitleUnique2(Invest invest) {
    List<Invest> list = investMapper.checkTitleUnique2(invest);
    return list;
  }

  @Override
  public Integer updateStatus2(Long investId) {
    int row = investMapper.updateStatus2(investId);
    return row;
  }

  @Override
  public Integer deleteInvestByIds(Long[] investIds) {
    return investMapper.deleteInvestByIds(investIds);
  }
}
