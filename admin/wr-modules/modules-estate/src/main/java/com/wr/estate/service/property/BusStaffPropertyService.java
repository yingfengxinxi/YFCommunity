package com.wr.estate.service.property;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.estate.entity.vo.BusStaffPropertyVo;
import com.wr.estate.entity.vo.StaffPropertyVo;
import com.wr.estate.entity.vo.UserIdVehicleNoVo;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.property.StaffProperty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/19 20:00
 */
public interface BusStaffPropertyService extends IService<StaffProperty> {


    /**
     * @param busStaffPropertyVo
     * @return
     */
    public List<BusStaffPropertyVo> list(BusStaffPropertyVo busStaffPropertyVo);

    /**
     *
     * @param dataList
     * @return
     */
    String importData(List<StaffPropertyVo> dataList);

    /**
     *
     * @param userId
     * @return
     */
    List<CarportVo> getUserIdVehicleNo(String userId);
}
