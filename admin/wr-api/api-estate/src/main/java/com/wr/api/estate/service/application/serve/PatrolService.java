package com.wr.api.estate.service.application.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.Patrol;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:43
 */
public interface PatrolService extends IService<Patrol> {

    /**
     *
     * @param patrol
     */
    void add(Patrol patrol);

    /**
     *
     * @param id
     * @return
     */
    Patrol get(String id);
}
