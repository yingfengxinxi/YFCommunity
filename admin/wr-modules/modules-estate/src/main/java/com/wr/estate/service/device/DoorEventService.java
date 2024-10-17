package com.wr.estate.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.aiot.Door;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.vo.DoorEventVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:17
 */
public interface DoorEventService extends IService<DoorEvent> {

    /**
     *
     * @param doorEventVo
     * @return
     */
    List<DoorEventVo> getList(DoorEventVo doorEventVo);

    /**
     *
     * @return
     */
    List<Door> getAllDoorList();


    /**
     *
     * @param id
     * @return
     */
    DoorEventVo getByIdEventInfo(@Param("id") String id);

}
