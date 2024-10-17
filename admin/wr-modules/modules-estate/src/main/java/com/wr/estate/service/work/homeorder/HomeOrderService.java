package com.wr.estate.service.work.homeorder;

import com.wr.estate.entity.HomeOrder;
import com.wr.estate.entity.HomeRepair;
import com.wr.estate.entity.vo.HomeOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-05 17:49:58
 * @Desc: 居家维修工单 业务
 */
public interface HomeOrderService {

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
    HomeOrderVO getHomeOrderById(Long orderId);

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
}
