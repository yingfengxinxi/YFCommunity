package com.wr.estate.service.impl.financial;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.estate.entity.ParkPayEntity;
import com.wr.estate.mapper.financial.ParkMapper;
import com.wr.estate.service.financial.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/2
 * @Description:
 * @FileName: ParkServiceImpl
 * @History:
 */
@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    ParkMapper parkMapper;

    @Override
    public List<ParkPayEntity> selectParkPay(ParkPayEntity parkPayEntity) {
        return parkMapper.selectParkPay(parkPayEntity);
    }

    @Override
    public List<ParkDurationEntity> selectParkDuration(ParkDurationEntity parkDurationEntity) {
        return parkMapper.selectParkDuration(parkDurationEntity);
    }

    @Override
    public String getSwitchType(Long communityId) {
        return parkMapper.getSwitchType(communityId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addParkPay(ParkPayEntity parkPayEntity) {
        if (parkMapper.getParkCountByCommunityId(parkPayEntity.getCommunityId()) == 0) {
            parkPayEntity.setCreateBy(SecurityUtils.getUsername());
            return parkMapper.addParkPay(parkPayEntity);
        }
        return -2;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParkPay(ParkPayEntity parkPayEntity) {
        parkPayEntity.setUpdateBy(SecurityUtils.getUsername());
        return parkMapper.updateParkPay(parkPayEntity);
    }

    @Override
    public int addParkDuration(ParkDurationEntity parkDurationEntity) {
        int c = parkMapper.parkDurationCount(parkDurationEntity.getCommunityId());
        if (c >= 6) {
            return -1;
        }
        if (parkDurationEntity.getMaxHour() <= parkDurationEntity.getMinHour()) {
            return -3;
        }
        ParkDurationEntity parkDurationEntity1 = parkMapper.getMax(parkDurationEntity.getCommunityId());
        if (parkDurationEntity1 != null) {
            int min = parkDurationEntity1.getMinHour();
            int max = parkDurationEntity1.getMaxHour();
            if (parkDurationEntity.getMinHour() >= max && parkDurationEntity.getMaxHour() > parkDurationEntity.getMinHour()) {
                return parkMapper.addParkDuration(parkDurationEntity);
            } else {
                return -2;
            }
        } else {
            return parkMapper.addParkDuration(parkDurationEntity);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delPd(Long durationId) {
        long communityId = parkMapper.getcid(durationId);
        List<ParkDurationEntity> list1 = parkMapper.getpd(communityId);
        if (list1.size() == 1) {
            return parkMapper.delpd(communityId);
        }

        int del = parkMapper.delpd(communityId);
        if (del <= 0) {
            return 0;
        } else {
            int row = parkMapper.addParkDuration1(list1);
            return row;
        }
    }

    @Override
    public int xgfy(ParkDurationEntity parkDurationEntity) {
        return parkMapper.xgfy(parkDurationEntity);
    }
}
