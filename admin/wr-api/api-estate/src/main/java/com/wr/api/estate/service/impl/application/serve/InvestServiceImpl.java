package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.vo.application.serve.InvestVO;
import com.wr.api.estate.mapper.application.serve.InvestMapper;
import com.wr.api.estate.service.application.serve.InvestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 11:32:08
 * @Desc:
 */
@Service
public class InvestServiceImpl implements InvestService {

    @Resource
    private InvestMapper investMapper;

    /**
     * @param communityId
     * @param investType
     * @return
     */
    @Override
    public List<InvestVO> searchByCommunityId(Long communityId, String investType) {
        return investMapper.searchByCommunityId(communityId, investType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvestVO detailByInvestId(Long investId) {
        //增加浏览次数
        investMapper.increaseReadNumById(investId);
        return investMapper.detailByInvestId(investId);
    }
}
