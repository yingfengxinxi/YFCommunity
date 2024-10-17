package com.wr.aiot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.vo.DoorEventVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:17
 */
public interface DoorEventService extends IService<DoorEvent> {

    /**
     *
     * @param id
     * @return
     */
    DoorEventVo getByIdEventInfo(@Param("id") String id);

    /**
     *
     * @param type
     * @param startTime
     * @param endTime
     * @throws Exception
     */
    void doorEvents(String type,String startTime,String endTime) throws Exception;

}
