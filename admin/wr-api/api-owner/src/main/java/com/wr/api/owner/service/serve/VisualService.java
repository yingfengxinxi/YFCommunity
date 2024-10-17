package com.wr.api.owner.service.serve;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Visual;
import com.wr.remote.aiot.VisualDeviceControl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/15 12:00
 */
public interface VisualService {

    /**
     *
     * @param communityId
     * @param buildingId
     * @param unitId
     * @return
     */
    public List<Visual> getList(Long communityId, Long buildingId, Long unitId);

    /**
     *
     * @param visualDeviceControl
     * @return
     * @throws Exception
     */
    public AjaxResult visualDeviceControl(VisualDeviceControl visualDeviceControl) throws Exception;
}
