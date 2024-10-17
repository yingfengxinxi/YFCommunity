package com.wr.estate.service.device.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.device.DoorEventMapper;
import com.wr.estate.service.device.DoorEventService;
import com.wr.remote.aiot.Door;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.vo.DoorEventVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:18
 */
@Slf4j
@Service
public class DoorEventServiceImpl extends ServiceImpl<DoorEventMapper, DoorEvent> implements DoorEventService {


    @Override
    public List<DoorEventVo> getList(DoorEventVo doorEventVo) {
        return baseMapper.getList(doorEventVo);
    }

    @Override
    public List<Door> getAllDoorList() {
        return baseMapper.getAllDoorList();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public DoorEventVo getByIdEventInfo(String id) {
        return baseMapper.getByIdEventInfo(id);
    }

}
