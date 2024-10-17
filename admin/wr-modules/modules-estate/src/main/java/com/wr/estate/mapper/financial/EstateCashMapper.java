package com.wr.estate.mapper.financial;


import com.wr.common.core.annotation.Excel;
import com.wr.remote.estate.financial.EstateCash;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:11:50
 * @Desc:
 */
public interface EstateCashMapper {

    /**
     * 根据小区查楼栋list
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    List<EstateCash> selectbuildingList(EstateCash etateCash);

    /**
     * 根据楼栋查询户主数量
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    int selectOwnerCount(EstateCash etateCash);
    /**
     * 根据楼栋查询楼栋面积
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    BigDecimal selectBuildArea(EstateCash etateCash);
    /**
     * 根据小区ID查询物业费收费标准
     *
     * @param communityId 查询条件
     * @return 对象列表
     */
    EstateCash selectStandardById(Long communityId);
    /**
     * 根据楼栋年限查询已交费用
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    BigDecimal selectPayed(EstateCash etateCash);
    /**
     * 根据楼栋查询已交费户主数量
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    int selectPayedCount(EstateCash etateCash);
    /**
     * 根据楼栋查询户主缴费情况list
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    List<EstateCash> selectbuildingOwnerList(EstateCash etateCash);
    /**
     * 根据小区roomID查询业主详情
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    EstateCash selectPayInfoById(EstateCash etateCash);
    /**
     * 查询户主缴费历年list
     *
     * @param etateCash 查询条件
     * @return 对象列表
     */
    List<EstateCash> selectOwnerPayList(EstateCash etateCash);



}
