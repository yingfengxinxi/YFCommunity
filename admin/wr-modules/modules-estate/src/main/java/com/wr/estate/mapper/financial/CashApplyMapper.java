package com.wr.estate.mapper.financial;


import com.wr.remote.estate.financial.EstateCash;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:11:50
 * @Desc:
 */
public interface CashApplyMapper {

    /**
     * 查询看板数据表列表
     *
     * @param estateCash 查询条件
     * @return 对象列表
     */
    List<EstateCash> selectList(EstateCash estateCash);

    /**
     * 投票结果统计
     *
     * @param voteId 查询条件
     * @return 对象列表
     */
    EstateCash voteResultSum(Long voteId);

    /**
     * 已发布通知
     *
     * @param voteId 基金id
     */
    void updateHasNotice(Long voteId);

    /**
     * 根据年份查询累计金额
     *
     * @param yearVo 查询条件
     * @return 对象列表
     */
    BigDecimal cashCountByTime(@Param("yearVo") String yearVo, @Param("communityId") Long communityId);

    /**
     * 查询所有累计金额
     *
     * @param estateCash 查询条件
     * @return 对象列表
     */
    BigDecimal allCashCount(EstateCash estateCash);

    /**
     * 查询投票详情
     *
     * @param voteId 查询条件
     * @return 对象列表
     */
    EstateCash voteInfoById(Long voteId);

    /**
     * 查询投票详情List
     *
     * @param estateCash 查询条件
     * @return 对象列表
     */
    List<EstateCash> ownerVoteList(EstateCash estateCash);

    /**
     * 修改数据
     *
     * @param estateCash 实例对象
     * @return 影响行数
     */
    int updateApply(EstateCash estateCash);

    /**
     * 插入数据
     *
     * @param estateCash 实例对象
     * @return 影响行数
     */

    int insertGrant(EstateCash estateCash);

}
