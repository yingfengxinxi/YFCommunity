package com.wr.api.estate.service.application.serve;

import com.wr.remote.domain.CarportNo;
import com.wr.remote.domain.vo.CarportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/6 15:44
 */
public interface CarportNoService {
    /**
     * @param communityId
     * @return
     */
    List<CarportVo> getAllCarportNo(Long communityId);

    /**
     * @param carportNoId
     * @return
     */
    CarportNo get(Long carportNoId);

    /**
     *
     * @param carportNo
     * @return
     */
    String getCarportNoCarportNature(String carportNo);

    /**
     *
     * @param carportNo
     * @return
     */
    Integer getByCarportNoCount(String carportNo);

}
