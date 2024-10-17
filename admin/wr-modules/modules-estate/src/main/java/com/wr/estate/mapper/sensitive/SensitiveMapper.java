package com.wr.estate.mapper.sensitive;

import com.wr.remote.domain.Sensitive;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/19 10:12
 */
public interface SensitiveMapper {


    /**
     * 敏感词管理列表查询
     *
     * @Param 敏感词管理实体类
     **/
    List<Sensitive> getListBusSensitive();

}
