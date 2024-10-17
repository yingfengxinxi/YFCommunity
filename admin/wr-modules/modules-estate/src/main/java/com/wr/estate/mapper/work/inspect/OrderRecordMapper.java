package com.wr.estate.mapper.work.inspect;

import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.vo.OrderRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单执行情况表
 * @Author: lvzy
 * @Date: 2022-10-31 15:57:52
 * @Desc: 工单执行情况表
 */
public interface OrderRecordMapper {

    /**
     * 新增
     * @param orderRecord
     * @return
     */
    Integer insertSelective(OrderRecord orderRecord);

    /**
     * 修改
     * @param orderRecord
     * @return
     */
    Integer updateByPrimaryKeySelective(OrderRecord orderRecord);

    /**
     * 查找数据是否重复
     * @param orderRecord
     * @return
     */
    Integer selectDataRepeat(OrderRecord orderRecord);

    /**
     * OrderType
     * @param orderId
     * @param orderType
     * @return
     */
    List<OrderRecord> deatilListThree(@Param("orderId") Long orderId, @Param("orderType") String orderType);

    /**
     * 获取执行情况详情
     * @param orderId
     * @param orderType
     * @return
     */
    List<OrderRecord> getRecord(@Param("orderId") Long orderId, @Param("orderType") String orderType);

    List<OrderRecordVo> getRecordTwo(@Param("orderId") Long orderId, @Param("orderType") String orderType);
}