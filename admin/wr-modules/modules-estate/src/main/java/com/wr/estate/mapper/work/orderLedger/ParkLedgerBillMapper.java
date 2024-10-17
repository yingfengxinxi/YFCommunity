package com.wr.estate.mapper.work.orderLedger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-31 15:56:31
 * @Desc: 园区保修台账
 */
public interface ParkLedgerBillMapper {
    /**
     *  search
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> query(Map<String,Object> params);

    /**
     *  search 报事类型
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryRepair(Long id);

    /**
     *  search 工单协助人 *
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryAssist(Map<String,Object> params);

    /**
     *  search 工单图片 *
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryOrderImg(Map<String,Object> params);
}
