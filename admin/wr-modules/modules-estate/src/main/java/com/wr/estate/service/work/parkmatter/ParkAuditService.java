package com.wr.estate.service.work.parkmatter;

import com.wr.common.core.web.domain.AjaxResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 18:31:27
 * @Desc:
 */
public interface ParkAuditService {

    List<LinkedHashMap<String,Object>> search(Map<String,Object> params);

    AjaxResult changeState(Map<String,Object> params);
    /**
     * 工单详情
     * @param auditId
     * @return
     */
    LinkedHashMap<String,Object> detail(Long auditId);
}
