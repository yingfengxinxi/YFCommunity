package com.wr.govern.mapper.voluntary;

import com.wr.remote.govern.voluntary.Voluntary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 09:13:41
 * @Desc: 志愿者服务 -->活动管理
 */
public interface VoluntaryMapper {
    /**
     *  查询
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> searchList(Map<String,Object> params);
    /**
     * 新增 活动
     * @param v
     * @return
     */
    int saveVoluntary(Voluntary v);

    /**
     * 活动下的小区
     * @param id
     * @return
     */
    List<Long> queryByVoluntaryId(Long id);
    /**
     * 新增 活动关联小区
     * @param params
     * @return
     */
    int saveVoluntaryRang(Map<String,Object> params);

    /**
     * 删除 活动
     * @param id
     * @return
     */
    int removeVoluntary(Long id);

    /**
     * 删除 活动关联小区
     * @param id
     * @return
     */
    int removeVoluntaryRang(Long id);

    /**
     * 删除 活动关联人员
     * @param id
     * @return
     */
    int removeVoluntaryUser(Long id);

    /**
     * 修改 活动
     * @param v
     * @return
     */
    int updateVoluntary(Voluntary v);

    /**
     * 查询 活动 下的 人员
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> searchListUser(Map<String,Object> params);
}
