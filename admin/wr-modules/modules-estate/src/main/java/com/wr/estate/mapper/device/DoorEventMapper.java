package com.wr.estate.mapper.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.aiot.Door;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.vo.DoorEventVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:16
 */
public interface DoorEventMapper extends BaseMapper<DoorEvent> {

    /**
     * @param doorEventVo
     * @return
     */
    List<DoorEventVo> getList(DoorEventVo doorEventVo);

    /**
     *
     * @return
     */
    List<Door>getAllDoorList();

    DoorEventVo getByIdEventInfo(@Param("id") String id);
}
