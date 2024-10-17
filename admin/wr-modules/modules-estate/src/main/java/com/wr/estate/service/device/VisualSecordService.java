package com.wr.estate.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.aiot.VisualSecord;
import com.wr.remote.domain.VisualSecordVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/14 23:20
 */
public interface VisualSecordService extends IService<VisualSecord> {


    /**
     *
     * @param visualSecordVo
     * @return
     */
    List<VisualSecordVo> getList(VisualSecordVo visualSecordVo);
}
