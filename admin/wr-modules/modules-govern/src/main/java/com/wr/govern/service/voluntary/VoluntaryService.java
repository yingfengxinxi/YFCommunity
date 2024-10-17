package com.wr.govern.service.voluntary;

import com.wr.remote.govern.voluntary.Voluntary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 10:08:48
 * @Desc:
 */
public interface VoluntaryService {
    /**
     *  查询
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> searchList(Map<String,Object> params);

    /**
     * 新增 活动
     * @param
     * @return
     */
    int saveVoluntary(Map<String, Object> params);
    /**
     * 删除 活动
     * @param id
     * @return
     */
    int removeVoluntary(Long id);
    /**
     * 修改 活动
     * @param
     * @return
     */
    int updateVoluntary(Map<String, Object> params);
    /**
     * 查询 活动 下的 人员
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> searchListUser(Map<String,Object> params);
}
