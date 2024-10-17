package com.wr.estate.service.work.parkmatter;

import com.wr.remote.estate.financial.ParkOrderDel;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-04 11:26:32
 * @Desc:
 */
public interface ParkOrderService {

    /**
     *  search
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> query(Map<String,Object> params);

    /**
     *  update
     * @param params
     * @return
     */
    int updateOrder(Map<String,Object> params);

    int remove(Map<String,Object> params);

    /**
     *
     * @param orderNo
     */
    void deleteByOrderNo(String orderNo);
    /**
     * 填入 工单 负责人
     * @param params
     * @return
     */
    int update(Map<String,Object> params);


    /**
     *
     * @param orderNo
     * @return
     */
    ParkOrderDel getByOrderNoInfo(String orderNo);

    /**
     * 清理车位管理表
     *
     * @param carportId
     * @return
     */
    void clearBusCarport(String carportId);
}
