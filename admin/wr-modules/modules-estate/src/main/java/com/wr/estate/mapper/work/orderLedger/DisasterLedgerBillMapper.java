package com.wr.estate.mapper.work.orderLedger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 10:37:58
 * @Desc:
 */
public interface DisasterLedgerBillMapper {

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
    List<LinkedHashMap<String,Object>> queryDisaster(Long id);
}
