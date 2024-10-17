package com.wr.estate.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.device.VisualSecordMapper;
import com.wr.estate.service.device.VisualSecordService;
import com.wr.remote.aiot.VisualSecord;
import com.wr.remote.domain.VisualSecordVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/15 10:11
 */
@Service
public class VisualSecordServiceImpl extends ServiceImpl<VisualSecordMapper, VisualSecord> implements VisualSecordService {


    /**
     *
     * @param visualSecordVo
     * @return
     */
    @Override
    public List<VisualSecordVo> getList(VisualSecordVo visualSecordVo) {
        return baseMapper.getList(visualSecordVo);
    }
}
