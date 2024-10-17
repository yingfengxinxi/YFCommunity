package com.wr.estate.mapper.work.parkmatter;

import com.wr.remote.estate.financial.ParkOrderDel;
import com.wr.remote.work.parkmatter.ParkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-04 10:46:39
 * @Desc: 园区 报事 工单
 */
public interface ParkOrderMapper {
    /**
     * search
     *
     * @param params
     * @return
     */
    List<LinkedHashMap<String, Object>> query(Map<String, Object> params);

    /**
     * update
     *
     * @param params
     * @return
     */
    int updateOrder(Map<String, Object> params);

    int remove(Long id);

    /**
     * @param orderNo
     */
    void deleteByOrderNo(@Param("orderNo") String orderNo);


    /**
     * 填入 工单 负责人
     *
     * @param params
     * @return
     */
    int update(Map<String, Object> params);

    /**
     * 新增工单
     *
     * @param parkOrder
     * @return
     */
    int save(ParkOrder parkOrder);

    /**
     *
     * @param orderNo
     * @return
     */
    ParkOrderDel getByOrderNoInfo(@Param("orderNo") String orderNo);

    /**
     * 清理车位管理表
     *
     * @param carportId
     * @return
     */
    void clearBusCarport(String carportId);
}
