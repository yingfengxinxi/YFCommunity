package com.wr.estate.service.sensitive;

import com.wr.remote.domain.Sensitive;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/19 10:14
 */
public interface SensitiveService {

    /**
     * 敏感词管理列表查询
     *
     * @Param 敏感词管理实体类
     **/
    List<Sensitive> getListBusSensitive();
}
