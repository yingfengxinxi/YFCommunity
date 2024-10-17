package com.wr.estate.mapper.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.aiot.VisualSecord;
import com.wr.remote.domain.VisualSecordVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/14 23:19
 */
public interface VisualSecordMapper extends BaseMapper<VisualSecord> {


    /**
     *
     * @param visualSecordVo
     * @return
     */
    List<VisualSecordVo> getList(VisualSecordVo visualSecordVo);
}
