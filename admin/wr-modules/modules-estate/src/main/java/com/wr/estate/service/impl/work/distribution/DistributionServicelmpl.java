package com.wr.estate.service.impl.work.distribution;

import com.wr.remote.work.distribution.Distribution;
import com.wr.estate.mapper.work.distribution.DistributionMapper;
import com.wr.estate.service.work.distribution.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/25 10:15
 * @Version 1.5
 */
@Service
public class DistributionServicelmpl implements DistributionService {

    @Autowired
    private DistributionMapper distributionMapper;
    @Override
    public List<Distribution> selectList(Distribution distribution) {
        List<Distribution> list=distributionMapper.selectList(distribution);
        for (Distribution allot : list) {
            String [] userTypes = distributionMapper.selectListAllot(allot.getAllotId());
            allot.setUserTypes(userTypes);
        }
        return list;
    }

    @Override
    @Transactional
    public int insertAllot(Distribution distribution) {
        String[] user=distribution.getUserTypes();
        int row =distributionMapper.selectAllot(distribution);
        if (row>0){
            return -1;
        }else {
            distributionMapper.insertAllot(distribution);
            for (String s : user) {
                distribution.setWorkPost(s);
                int row1= distributionMapper.insertAllotPost(distribution);
            }
            return 1;
        }

    }

    @Override
    @Transactional
    public int updateAllot(Distribution distribution) {
        String[] user=distribution.getUserTypes();
        distributionMapper.deleteAllot(distribution);
        for (String s : user) {
            distribution.setWorkPost(s);
            distributionMapper.insertAllotPost(distribution);
        }
        return distributionMapper.updateAllot(distribution);
    }

    @Override
    public int selectAllot(Distribution distribution) {
        int row=distributionMapper.selectAllot(distribution);
        return row;
    }

    @Override
    public Distribution selectAllotById(Long allotId) {
        Distribution distribution=distributionMapper.selectAllotById(allotId);
        distribution.setUserTypes(distributionMapper.selectListAllot(allotId));
        distribution.setCommunityName(distributionMapper.selectName(distribution.getCommunityId()));
        return distribution;
    }
}
