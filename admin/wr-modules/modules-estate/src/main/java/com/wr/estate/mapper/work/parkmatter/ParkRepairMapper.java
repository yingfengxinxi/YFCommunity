package com.wr.estate.mapper.work.parkmatter;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 14:42:37
 * @Desc: 园区报事类型
 */
public interface ParkRepairMapper {
    /**
     *  search
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> search(Map<String,Object> params);

    int save(Map<String,Object> params);

    int update(Map<String,Object> params);

    int removes(Long[] ids);

    /**
     *  name one and only
     * @param name
     * @return
     */
    int repairNameCount(@Param("estateId") Long estateId, @Param("name") String name, @Param("id") Long id);
}
