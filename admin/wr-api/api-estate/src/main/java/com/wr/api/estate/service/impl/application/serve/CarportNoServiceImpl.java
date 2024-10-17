package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.mapper.application.CarportNoMapper;
import com.wr.api.estate.service.application.serve.CarportNoService;
import com.wr.remote.domain.CarportNo;
import com.wr.remote.domain.vo.CarportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/6 15:44
 */
@Service
public class CarportNoServiceImpl implements CarportNoService {

    @Autowired
    private CarportNoMapper carportNoMapper;

    @Override
    public List<CarportVo> getAllCarportNo(Long communityId) {
        return carportNoMapper.getAllCarportNo(communityId);
    }

    @Override
    public CarportNo get(Long carportNoId) {
        return carportNoMapper.get(carportNoId);
    }

    @Override
    public String getCarportNoCarportNature(String carportNo) {
        return carportNoMapper.getCarportNoCarportNature(carportNo);
    }

    /**
     *
     * @param carportNo
     * @return
     */
    @Override
    public Integer getByCarportNoCount(String carportNo) {
        return carportNoMapper.getByCarportNoCount(carportNo);
    }
}
