package com.wr.estate.mapper.financial;


import com.wr.remote.estate.financial.EstateCash;
import org.aspectj.apache.bcel.generic.LineNumberGen;

import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:11:50
 * @Desc:
 */
public interface CashUseMapper {

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
     * 详细
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
