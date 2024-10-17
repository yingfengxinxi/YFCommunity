package com.wr.api.estate.mapper.application.manage;

import com.wr.api.estate.entity.vo.application.manage.BorrowVo;
import com.wr.api.estate.entity.vo.application.manage.GoodsVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:38:29
 * @Desc: 数据层
 */
public interface GoodsMapper {

    /**
     * 物品列表
     * @param goodsVo
     * @return
     */
    List<GoodsVo> getList(GoodsVo goodsVo);

    /**
     * 物品详情
     * @param goodsId
     * @return
     */
    GoodsVo getById(Long goodsId);

    /**
     * 确认归还
     * @param goodsId
     * @return
     */
    int updateGoodsVoFree(Long goodsId);

    /**
     * 物品借出
     * @param goodsVo
     * @return
     */
    int updateGoodsVoBusy(GoodsVo goodsVo);

    /**
     * 借用历史列表
     * @param goodsId
     * @return
     */
    List<BorrowVo> getBorrowList(Long goodsId);

    /**
     * 驳回借用申请
     * @param borrowVo
     * @return
     */
    int updateBorrowRefuse(BorrowVo borrowVo);

    /**
     * 同意借用申请
     * @param borrowVo
     * @return
     */
    int updateBorrowAgree(BorrowVo borrowVo);

    /**
     * 物品总数
     * @param communityId
     * @return
     */
    int getCount(Long communityId);

    /**
     * 借用申请列表
     * @param communityId
     * @return
     */
    List<BorrowVo> getApplyList(Long communityId);

    /**
     * 借用申请详情
     * @param borrowVo
     * @return
     */
    BorrowVo getApplyDetail(BorrowVo borrowVo);


    /**
     * 归还详情
     * @param goodsId
     * @return
     */
    GoodsVo getDetailById(Long goodsId);

    /**
     * 类别名称列表
     * @param estateId
     * @return
     */
    List<GoodsVo> getClassifyName(Long estateId);

    /**
     * 归还借用数据
     * @param borrowId 借用id
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-06-06 17:29:01
     * @ver v1.0.0
     */
    Integer updateBorrowStatus(Long borrowId);

    /**
     * 现金赔偿删除物品
     * @param goodsId
     * @return {@link Integer}
     * @Author RainCity
     * @ver v1.0.0
     */
    Integer removeGoods(Long goodsId);
}
