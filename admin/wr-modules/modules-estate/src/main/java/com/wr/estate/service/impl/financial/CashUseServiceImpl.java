package com.wr.estate.service.impl.financial;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.financial.CashUseMapper;
import com.wr.estate.service.financial.CashUseService;
import com.wr.remote.estate.financial.EstateCash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:40:31
 * @Desc:
 */
@Service
public class CashUseServiceImpl implements CashUseService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CashUseMapper cashUseMapper;


    @Override
    public List<EstateCash> selectList(EstateCash estateCash) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(null != estateCash.getApplyTime()){
            String years = sdf.format(estateCash.getApplyTime());
            estateCash.setYears(years);
        }
        List<EstateCash> list = cashUseMapper.selectList(estateCash);
        for (EstateCash cash : list) {
            int i = cashUseMapper.voteIDCount(cash.getVoteId());
            if (0 != i) {
                cash.setGrantStatus("已拨款");
            }else {
                cash.setGrantStatus("未拨款");
            }
                }
        return list;
    }

    @Override
    public EstateCash infoByVoteId(Long voteId) {
        EstateCash estateCash = cashUseMapper.infoByVoteId(voteId);
        int i = cashUseMapper.voteIDCount(estateCash.getVoteId());
        if (0 == i) {
            estateCash.setGrantStatus("未拨款");
        }else {
            estateCash.setGrantStatus("已拨款");
        }
        return estateCash;
    }

    @Override
    public int updateGrant(EstateCash estateCash) {
        String username = SecurityUtils.getUsername();
        estateCash.setCreateBy(username);
        return cashUseMapper.updateGrant(estateCash);
    }

    @Override
    public int voteIDCount(Long voteId) {
        return cashUseMapper.voteIDCount(voteId);
    }
}
