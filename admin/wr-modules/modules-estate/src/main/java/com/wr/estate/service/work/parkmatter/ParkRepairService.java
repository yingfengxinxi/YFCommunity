package com.wr.estate.service.work.parkmatter;

import com.wr.common.core.web.domain.AjaxResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 15:04:01
 * @Desc:
 */
public interface ParkRepairService {

    /**
     *  search
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> search(Map<String,Object> params);

    AjaxResult save(Map<String,Object> params);

    AjaxResult update(Map<String,Object> params);

    int removes(Long[] ids);

}
