package com.wr.aiot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.domain.vo.VisitingSecordVo;

/**
 * @Author lvzy
 * @Date 2024/4/7 19:30
 */
public interface VisitingSecordService extends IService<VisitingSecord> {


    /**
     *
     * @param id
     * @return
     */
    VisitingSecordVo getByIdVisitingSecordInfo(String id);

    /**
     *
     * @param type
     * @throws Exception
     */
    void visitingSecord(String type) throws Exception;
}
