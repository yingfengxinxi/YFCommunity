package com.wr.estate.service.work.orderLedger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 10:53:27
 * @Desc:
 */
public interface DisasterLedgerBillService {

    /**
     *  search
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> query(Map<String,Object> params);

    /**
     *  search 灾害类型
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryDisaster();

    /**
     *  search 工单协助人
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryAssist(Long id);
}
