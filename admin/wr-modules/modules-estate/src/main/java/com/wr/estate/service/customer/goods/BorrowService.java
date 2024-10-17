package com.wr.estate.service.customer.goods;

import com.wr.remote.estate.customer.goods.vo.BorrowVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-12 16:27:24
 * @Desc: 借用申请表服务接口
 */
public interface BorrowService {

    /**
     * 查询Borrow列表
     *
     * @param borrowVo 筛选条件
     * @return 查询结果
     */
    List<BorrowVo> selectBorrowList(BorrowVo borrowVo);

    /**
     * 根据id查询单条数据
     *
     * @param borrowId 主键
     * @return 实例对象
     */
    GoodsVo getBorrowById(Long borrowId);

    /**
     * 修改数据
     *
     * @param borrow 实例对象
     * @return 影响行数
     */
    int updateBorrow(BorrowVo borrow);

    /**
     * 借用天数
     * @param goodsId
     * @return
     */
    BorrowVo getBorrowDay(Long goodsId);

    /**
     * 历史借用次数
     *
     * @param goodsId
     * @return
     */
    int getBorrowCount(Long goodsId);
}
