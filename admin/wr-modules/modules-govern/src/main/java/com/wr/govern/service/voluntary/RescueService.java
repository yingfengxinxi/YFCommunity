package com.wr.govern.service.voluntary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-26 14:53:11
 * @Desc:
 */
public interface RescueService {
    /**
     * search
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> searchList(Map<String,Object> params);
}
