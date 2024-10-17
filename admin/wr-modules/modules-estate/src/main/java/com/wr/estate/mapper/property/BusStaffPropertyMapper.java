package com.wr.estate.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.estate.entity.vo.BusStaffPropertyVo;
import com.wr.estate.entity.vo.UserIdVehicleNoVo;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.property.StaffProperty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/19 20:31
 */
public interface BusStaffPropertyMapper extends BaseMapper<StaffProperty> {

    List<BusStaffPropertyVo> list(BusStaffPropertyVo busStaffPropertyVo);

    Integer getVehicleNoCount(@Param("communityId") Long communityId, @Param("vehicleNo") String vehicleNo);

    /**
     *
     * @param userId
     * @return
     */
    List<CarportVo> getUserIdVehicleNo(@Param("userId") String userId);
}
