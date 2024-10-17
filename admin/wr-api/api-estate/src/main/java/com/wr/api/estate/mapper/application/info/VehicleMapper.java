package com.wr.api.estate.mapper.application.info;

import com.wr.api.estate.entity.vo.application.info.CarportVo;
import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.VehicleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:04:22
 * @Desc: 数据层
 */
public interface VehicleMapper {
    /**
     * 列表
     *
     * @param vehicleVo
     * @return
     */
    List<VehicleVo> getList(VehicleVo vehicleVo);

    /**
     * 表单详情
     *
     * @param vehicleVo
     * @return
     */
    VehicleVo getDetail(VehicleVo vehicleVo);

    /**
     * @param communityId
     * @param accountId
     * @return
     */
    List<VehicleVo> getVehicleDetail(@Param("communityId") Long communityId, @Param("accountId") Long accountId, @Param("vehicleId") Long vehicleId);

    /**
     * @param vehicleId
     * @return
     */
    CarportVo getVehicleIdCarportInfo(@Param("vehicleId") Long vehicleId);

    /**
     * 车主信息
     *
     * @param vehicleVo
     * @return
     */
    VehicleVo getDriverInfo(VehicleVo vehicleVo);


    /**
     * 车辆总数
     *
     * @param communityId
     * @return
     */
    int getVehicleCount(Long communityId);

}
