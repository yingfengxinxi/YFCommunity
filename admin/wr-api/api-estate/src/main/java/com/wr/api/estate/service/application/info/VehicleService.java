package com.wr.api.estate.service.application.info;

import com.wr.api.estate.entity.vo.application.info.VehicleVo;
import com.wr.common.core.web.domain.AjaxResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:04:22
 * @Desc: 业务层
 */
public interface VehicleService {

    /**
     * 列表
     *
     * @param vehicleVo
     * @return
     */
    List<VehicleVo> getList(VehicleVo vehicleVo);

    /**
     * 详情
     *
     * @param vehicleVo
     * @return
     */
    AjaxResult getDetail(VehicleVo vehicleVo);

    /**
     * 业主总数
     *
     * @param communityId
     * @return
     */
    int getVehicleCount(Long communityId);

    List<VehicleVo> getVehicleDetail(Long communityId, Long accountId, Long vehicleId);
}
