package com.wr.estate.mapper.customer.goods;

import com.wr.remote.estate.customer.goods.vo.BorrowVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

 /**
 * @Author: Guan Yu
 * @Date: 2022-10-12 16:27:24
 * @Desc: 借用申请表数据库访问层
 */
public interface BorrowMapper {

    /**
    * 查询借用申请表列表
    *
    * @param borrowVo 查询条件
    * @return 对象列表
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
     int getBorrowCount(@Param("goodsId") Long goodsId,@Param("accountId") Long accountId);

}

