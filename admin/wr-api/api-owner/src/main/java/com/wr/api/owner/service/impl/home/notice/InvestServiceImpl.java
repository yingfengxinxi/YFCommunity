package com.wr.api.owner.service.impl.home.notice;


import com.wr.api.owner.entity.home.notice.Invest;
import com.wr.api.owner.mapper.home.notice.InvestMapper;
import com.wr.api.owner.service.home.notice.InvestService;
import com.wr.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公示
 */
@Service
public class InvestServiceImpl implements InvestService {


    @Autowired
    private InvestMapper investMapper;



    /**
     * 列表
     * @param invest
     * @return
     */
    @Override
    public List<Invest> getList(Invest invest) {
        return investMapper.getList(invest);
    }





    /**
     * 公示详情
     * @param investId
     * @return
     */
    @Override
    public Invest getInvestById(Long investId) {
        //查询详情，新增阅读数
        investMapper.updateReadNum(investId);
        return investMapper.getInvestById(investId);
    }

    /**
     * 查询发布时间最新的公示
     * @return
     */
    @Override
    public Invest selectNews(Invest invest) {
        return investMapper.selectNews(invest);
    }

    @Override
    public List<com.wr.remote.estate.info.Invest> selectList(com.wr.remote.estate.info.Invest invest) {
        List<com.wr.remote.estate.info.Invest> list = investMapper.selectList(invest);
        for (com.wr.remote.estate.info.Invest invest1 : list) {
            if ("1".equals(invest1.getInvestStatus())) {
                invest1.setUpdateTime(null);
            }
        }
        return list;
    }

    @Override
    public com.wr.remote.estate.info.Invest selectEstateNameById(Long estateId) {
        return investMapper.selectEstateNameById( SecurityUtils.getLoginUser().getSysUser().getEstateId());
    }

    @Override
    public com.wr.remote.estate.info.Invest selectInvestById(Long investId) {
        com.wr.remote.estate.info.Invest invest = investMapper.selectInvestById(investId);
        if ("1".equals(invest.getInvestStatus())) {
            invest.setUpdateTime(null);
        }
        return invest;
    }

    @Override
    public Integer insertInvest(com.wr.remote.estate.info.Invest invest) {
        int row = investMapper.insertInvest(invest);
        return row;
    }

    @Override
    public Integer updateInvest(com.wr.remote.estate.info.Invest invest) {
        int row = investMapper.updateInvest(invest);
        return row;
    }

    @Override
    public Integer updateStatus(Long investId) {
        int row = investMapper.updateStatus(investId);
        return row;
    }

    @Override
    public List<com.wr.remote.estate.info.Invest> checkTitleUnique(com.wr.remote.estate.info.Invest invest) {
        List<com.wr.remote.estate.info.Invest> list = investMapper.checkTitleUnique(invest);
        return list;
    }

    @Override
    public List<com.wr.remote.estate.info.Invest> checkTitleUnique2(com.wr.remote.estate.info.Invest invest) {
        List<com.wr.remote.estate.info.Invest> list = investMapper.checkTitleUnique2(invest);
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

    @Override
    public Integer updateRedNum(Long investId) {
        return investMapper.updateRedNum(investId);
    }
}
