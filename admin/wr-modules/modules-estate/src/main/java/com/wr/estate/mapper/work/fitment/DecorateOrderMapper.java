package com.wr.estate.mapper.work.fitment;

import com.wr.remote.estate.customer.decorate.Decorate;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.fitment.DecorateOrder;
import com.wr.remote.work.inspect.OrderRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-11-02 15:54:34
 * @Desc: 装修评定工单
 */
@Mapper
public interface DecorateOrderMapper {

    /**
     * 装修评定工单列表
     *
     * @param decorateOrder
     * @return
     */
    List<DecorateOrder> getDecorateOrderList(DecorateOrder decorateOrder);

    /**
     * 查询装修列表
     *
     * @param communityId
     * @return
     */
    List<Decorate> getDecorateList(Long communityId);

    /**
     * 根据装修id查询装修详细信息
     *
     * @param decorateId
     * @return
     */
    Decorate getDecorateDetail(Long decorateId);

    /**
     * 装修评定工单新增
     *
     * @param decorateOrder
     * @return
     */
    int addDecorateOrder(DecorateOrder decorateOrder);

    /**
     * 查询工单分配设置
     *
     * @param communityId
     * @return
     */
    Distribution getDecorateOrderDistribution(Long communityId);

    /**
     * 装修评定工单删除
     *
     * @param orderIds
     * @return
     */
    int deleteDecorateOrder(Long[] orderIds);

    /**
     * 装修评定工单整改
     *
     * @param orderId
     * @return
     */
    int rectificationDecorateOrder(Long orderId);

    /**
     * 查询装修评定工单详情
     *
     * @param orderId
     * @return
     */
    DecorateOrder getDecorateOrderDetail(Long orderId);

    /**
     * 装修评定工单修改页面查询数据
     *
     * @param orderId
     * @return
     */
    DecorateOrder getUpdateDetail(Long orderId);

    /**
     * 装修评定工单修改
     *
     * @param decorateOrder
     * @return
     */
    int updateDecorateOrder(DecorateOrder decorateOrder);

    /**
     * 工单分配
     *
     * @param decorateOrder
     * @return
     */
    int decorateOrderDistribution(DecorateOrder decorateOrder);

    /**
     * 查询工单协助人信息
     *
     * @param orderId
     * @return
     */
    List<Staff> getStaffList(Long orderId);

    /**
     * 工单执行情况表插入数据
     *
     * @param orderRecord
     */
    void insertOrderRecord(OrderRecord orderRecord);
}
