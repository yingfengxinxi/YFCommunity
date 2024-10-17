package com.wr.api.owner.mapper.estateapi;

import com.wr.remote.domain.CarportNo;
import com.wr.remote.domain.vo.CarportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/6 15:26
 */
public interface CarportNoMapper {

    /**
     * 新增
     *
     * @param carportNo
     */
    Integer insert(CarportNo carportNo);

    /**
     * @param carportNo
     * @return
     */
    List<CarportNo> list(CarportNo carportNo);

    Long getCarPoreNoCount(@Param("communityId") Long communityId, @Param("carportNo") String carportNo);

    /**
     * @param communityId
     * @return
     */
    List<CarportVo> getAllCarportNo(@Param("communityId") Long communityId);

    /**
     * @param carportNoId
     * @return
     */
    CarportNo get(@Param("carportNoId") Long carportNoId);

    /**
     * @param carportNo
     * @param communityId
     * @return
     */
    Long getCarportNoId(@Param("carportNo") String carportNo, @Param("communityId") Long communityId);

    /**
     * @param carportNo
     * @return
     */
    Integer updateCheckCount(CarportNo carportNo);

    /**
     * @param carportNo
     * @return
     */
    Integer addCheckCount(CarportNo carportNo);

    /**
     * @param carportNoId
     */
    void delete(@Param("carportNoId") Long carportNoId);

    /**
     * @param carportNo
     */
    void update(CarportNo carportNo);
}
