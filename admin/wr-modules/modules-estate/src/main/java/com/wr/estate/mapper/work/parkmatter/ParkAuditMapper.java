package com.wr.estate.mapper.work.parkmatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 17:56:13
 * @Desc: 园区工单 审核
 */
public interface ParkAuditMapper {

    List<LinkedHashMap<String,Object>> search(Map<String,Object> params);

    int changeState(Map<String,Object> params);


    int remove(Long id);


    /**
     * 工单详情
     * @param auditId
     * @return
     */
    LinkedHashMap<String,Object> detail(Long auditId);
}
