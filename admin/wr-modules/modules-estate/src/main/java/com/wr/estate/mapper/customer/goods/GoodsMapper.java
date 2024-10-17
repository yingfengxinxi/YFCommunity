package com.wr.estate.mapper.customer.goods;

import com.wr.remote.estate.customer.goods.vo.BorrowVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 19:23:46
 * @Desc: 物品列表数据库访问层
 */
public interface GoodsMapper {


    /**
     * 导出列表
     * @param goodsVo
     * @return
     */
    List<GoodsVo> export(GoodsVo goodsVo);

    /**
     * 物品列表
     * @param goodsVo
     * @return
     */
    List<GoodsVo> selectGoodsList(GoodsVo goodsVo);

    /**
     * 物品详情
     * @param goodsId
     * @return
     */
    GoodsVo getGoodsInfo(@Param("goodsId") Long goodsId,@Param("applyTime") Date applyTime);

    /**
     * 借用人详情
     *
     * @param accountId
     * @return
     */
    GoodsVo getOwnerInfo(Long accountId);


    /**
     * 物品借用历史
     *
     * @param goodsId
     * @return
     */
    List<BorrowVo> selectBorrowHistory(Long goodsId);

    /**
     * 修改
     * @param goodsVo
     * @return
     */
    int updateGoods(GoodsVo goodsVo);

    /**
     * 新增
     *
     * @param goodsVo
     * @return
     */
    int insertClassify(GoodsVo goodsVo);


    /**
     * 删除
     * @param goodsId
     * @return
     */
    int deleteById(Long goodsId);

    /**
     * 新增提醒
     * @param goodsVo
     * @return
     */
    int insertRemind(GoodsVo goodsVo);

    /**
     * 物品编号是否存在
     *
     * @param goodsVo
     * @return
     */
    int getIsExsit(GoodsVo goodsVo);
}
