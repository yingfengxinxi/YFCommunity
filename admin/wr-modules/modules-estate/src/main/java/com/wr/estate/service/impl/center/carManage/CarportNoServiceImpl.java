package com.wr.estate.service.impl.center.carManage;

import com.wr.estate.mapper.center.carManage.CarportNoMapper;
import com.wr.estate.service.center.CarportNoService;
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
    public void insert(CarportNo carportNo) {
        carportNoMapper.insert(carportNo);
    }

    @Override
    public List<CarportNo> list(CarportNo carportNo) {
        return carportNoMapper.list(carportNo);
    }

    @Override
    public List<CarportVo> getAllCarportNo(Long communityId) {
        return carportNoMapper.getAllCarportNo(communityId);
    }

    @Override
    public CarportNo get(Long carportNoId) {
        return carportNoMapper.get(carportNoId);
    }

    @Override
    public Integer updateCheckCount(CarportNo carportNo) {
        return carportNoMapper.updateCheckCount(carportNo);
    }

    @Override
    public Integer addCheckCount(CarportNo carportNo) {
        return carportNoMapper.addCheckCount(carportNo);
    }

    @Override
    public void delete(Long carportNoId) {
        carportNoMapper.delete(carportNoId);
    }

    @Override
    public void update(CarportNo carportNo) {
        carportNoMapper.update(carportNo);
    }
}
