package com.wr.estate.service.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.customer.HouseKeepMapper;
import com.wr.estate.service.HouseKeepService;
import com.wr.remote.estate.housekeep.vo.HouseKeepDetailVO;
import com.wr.remote.estate.housekeep.vo.HouseKeepListVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 荀彧
 * @description 管家管理service
 * @date 2022-10-13
 */
@Service
public class HouseKeepServiceImpl implements HouseKeepService {

    @Resource
    private HouseKeepMapper houseKeepMapper;


    @Override
    public List<HouseKeepListVO> pageList(HouseKeepListVO houseKeepListVO) {
        return houseKeepMapper.pageList(houseKeepListVO);
    }

    @Override
    @Transactional
    public int del(Long majordomoId) {
        return houseKeepMapper.del(majordomoId);
    }

    @Override
    public List<Map<String, Object>> postSelect() {
        return houseKeepMapper.postSelect();
    }

    public HouseKeepListVO queryDetail(Long majordomoId) {
        return houseKeepMapper.queryDetail(majordomoId);
    }

    @Override
    public Integer updateKeepList(HouseKeepListVO houseKeepListVO) {
        Integer i = 0;
        houseKeepListVO.setUpdateBy(SecurityUtils.getUsername());
        i = houseKeepMapper.updateKeepList(houseKeepListVO);
        return i;
    }

    @Override
    public List<HouseKeepListVO> selectBuild(Long communityId) {
        return houseKeepMapper.selectBuild(communityId);
    }

    @Override
    public List<HouseKeepListVO> selectStaff(HouseKeepListVO houseKeepListVO) {

        List<HouseKeepListVO> houseKeepListVOS = houseKeepMapper.selectStaff(houseKeepListVO);
        return houseKeepListVOS;
    }

    @Override
    public HouseKeepListVO selectPhone(Long staffId) {
        return houseKeepMapper.selectPhone(staffId);
    }

    @Override
    @Transactional
    public int insertBuild(HouseKeepListVO houseKeepListVO) {
        return houseKeepMapper.insertBuild(houseKeepListVO);
    }

    @Override
    public String checkBuildUnique(HouseKeepListVO houseKeepListVO) {
        Integer unique = houseKeepMapper.checkBuildUnique(houseKeepListVO);
        return unique > 0 ? UserConstants.NOT_UNIQUE : UserConstants.UNIQUE;
    }
}
