package com.wr.estate.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.domain.vo.VisitingSecordVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/8 16:40
 */
public interface VisitingSecordService extends IService<VisitingSecord> {


    /**
     *
     * @param visitingSecordVo
     * @return
     */
    List<VisitingSecordVo> getList(VisitingSecordVo visitingSecordVo);

    /**
     *
     * @param id
     * @return
     */
    public VisitingSecordVo getByIdVisitingSecordInfo(String id);
}
