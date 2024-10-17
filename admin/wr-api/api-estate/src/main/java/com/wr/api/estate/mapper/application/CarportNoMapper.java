package com.wr.api.estate.mapper.application;

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
    void insert(CarportNo carportNo);

    /**
     * @param carportNo
     * @return
     */
    List<CarportNo> list(CarportNo carportNo);

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
     * @return
     */
    String getCarportNoCarportNature(@Param("carportNo") String carportNo);

    Integer getByCarportNoCount(@Param("carportNo") String carportNo);

    /**
     * @param carportNo
     */
    void update(CarportNo carportNo);
}
