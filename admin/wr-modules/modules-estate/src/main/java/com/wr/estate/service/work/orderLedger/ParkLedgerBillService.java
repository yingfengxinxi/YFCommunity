package com.wr.estate.service.work.orderLedger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-31 17:59:32
 * @Desc:
 */
public interface ParkLedgerBillService {
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
    List<LinkedHashMap<String,Object>> queryRepair();

    /**
     *  search 工单协助人
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryAssist(Long id);
}
