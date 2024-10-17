package com.wr.api.estate.service.application.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.Inspection;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:43
 */
public interface InspectionService extends IService<Inspection> {

    /**
     *
     * @param inspection
     */
    void add(Inspection inspection);

    /**
     *
     * @param id
     * @return
     */
    Inspection get(String id);
}
