package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.vo.application.serve.InvestVO;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 11:31:49
 * @Desc:
 */
public interface InvestService {
    /**
     * 招商公告
     * @param communityId
     * @return
     */
    List<InvestVO> searchByCommunityId(Long communityId,String investType);
    /**
     * 详情
     * @param investId
     * @return
     */
    InvestVO detailByInvestId(Long investId);
}
