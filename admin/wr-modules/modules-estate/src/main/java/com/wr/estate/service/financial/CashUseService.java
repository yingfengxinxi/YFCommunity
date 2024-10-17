package com.wr.estate.service.financial;

import com.wr.remote.estate.financial.EstateCash;

import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:39:49
 * @Desc:
 */
public interface CashUseService {

    /**
     * 查询列表
     *
     * @param estateCash 查询条件
     * @return 对象列表
     */
    List<EstateCash> selectList(EstateCash estateCash);
    /**
     * 详细
     *
     * @param voteId 查询条件
     * @return 对象列表
     */
    EstateCash infoByVoteId(Long voteId);


    /**
     * 通过驳回
     *
     * @param estateCash 查询条件
     * @return 对象列表
     */
    int updateGrant(EstateCash estateCash);

    /**
     * 详细
     *
     * @param voteId 查询条件
     * @return 对象列表
     */
    int voteIDCount(Long voteId);

}
