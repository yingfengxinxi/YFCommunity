package com.wr.estate.service.center;

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
    List<CarportVo> getAllCarportNo(Long communityId);

    /**
     * @param carportNoId
     * @return
     */
    CarportNo get(Long carportNoId);

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
    void delete(Long carportNoId);

    /**
     * @param carportNo
     */
    void update(CarportNo carportNo);
}
