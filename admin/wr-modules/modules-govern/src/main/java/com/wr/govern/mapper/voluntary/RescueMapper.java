package com.wr.govern.mapper.voluntary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-26 13:29:30
 * @Desc: 志愿者服务 --> 社区求助
 */
public interface RescueMapper {
    /**
     * search
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> searchList(Map<String,Object> params);
}
