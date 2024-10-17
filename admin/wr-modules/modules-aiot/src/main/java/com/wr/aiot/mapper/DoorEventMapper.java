package com.wr.aiot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.vo.DoorEventVo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:16
 */
public interface DoorEventMapper extends BaseMapper<DoorEvent> {

    /**
     *
     * @param id
     * @return
     */
    DoorEventVo getByIdEventInfo(@Param("id") String id);
}
