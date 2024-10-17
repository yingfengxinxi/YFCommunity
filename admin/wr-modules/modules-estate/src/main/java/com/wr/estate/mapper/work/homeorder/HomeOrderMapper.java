package com.wr.estate.mapper.work.homeorder;

import com.wr.estate.entity.HomeOrder;
import com.wr.estate.entity.HomeRepair;
import com.wr.estate.entity.vo.HomeOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-05 16:39:00
 * @Desc: 居家维修工单 mapper
 */
public interface HomeOrderMapper {

    /**
     * 获取居家维修工单列表
     * @param homeOrder
     * @return: java.util.List<com.wr.estate.entity.vo.HomeOrderVO>
     */
    List<HomeOrderVO> getHomeOrderAll(HomeOrderVO homeOrder);

    /**
     * 获取居家维修工单详情信息
     * @param orderId
     * @return: com.wr.estate.entity.vo.HomeOrderVO
     */
    HomeOrderVO getHomeOrderById(@Param("orderId") Long orderId);

    /**
     * 分配工单
     * @param homeOrder
     * @return: java.lang.Integer
     */
    Integer distribution(HomeOrder homeOrder);

    /**
     * 获取居家维修类型
     * @param estateId
     * @return: java.util.List<com.wr.estate.entity.HomeRepair>
     */
    List<HomeRepair> getHomeRepair(@Param("estateId") Long estateId);

    /**
     * 获取工单图片
     * @param imageType
     * @param tableId
     * @return: java.util.List<java.lang.String>
     */
    List<String> getPhoto(@Param("imageType") String imageType,@Param("tableId") Long tableId);

    /**
     * 获取异常
     * @param orderType
     * @param orderId
     * @return: java.util.List<java.lang.String>
     */
    String getAbnormal(@Param("orderType") String orderType,@Param("orderId") Long orderId);
}
