package com.wr.api.owner.service.estateapi.impl;

import com.wr.api.owner.entity.estateapi.BusCommunityCare;
import com.wr.api.owner.mapper.estateapi.BusCommunityCareMapper;
import com.wr.api.owner.service.estateapi.BusCommunityCareService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.Date;
import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:48:47
 * @Desc: 社区关怀
 */
@Service
public class BusCommunityCareServiceImpl implements BusCommunityCareService {

    @Autowired
    private BusCommunityCareMapper communityCareMapper;

    @Override
    public List<BusCommunityCare> getList(BusCommunityCare communityCare) {
        return communityCareMapper.getList(communityCare);
    }

    @Override
    public BusCommunityCare getById(Long id) {
        return communityCareMapper.getById(id);
    }

    @Override
    public void insert(BusCommunityCare communityCare) throws ServiceException {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        communityCare.setEstateId(estateId);
        communityCare.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        if ("1".equals(communityCare.getStatus())) {
            communityCare.setEventReleaseTime(new Date());
            communityCare.setEventReleaseBy(communityCare.getCreateBy());
        }
        communityCareMapper.insert(communityCare);
    }

    @Override
    public void update(BusCommunityCare communityCare) {
        communityCare.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        if ("1".equals(communityCare.getStatus())) {
            communityCare.setEventReleaseTime(new Date());
            communityCare.setEventReleaseBy(communityCare.getUpdateBy());
        } else {
            communityCare.setEventReleaseTime(null);
            communityCare.setEventReleaseBy("");
        }
        communityCareMapper.update(communityCare);
    }

    @Override
    public void delete(Long id) {

        communityCareMapper.delete(id);
    }

    /**
     * @param status
     * @param id
     */
    @Override
    public void updateByIdStatus(String status, Long id) {
        Date date = new Date();
        Long userId = SecurityUtils.getUserId();
        String eventReleaseBy = String.valueOf(userId);
        if ("0".equals(status)) {
            date = null;
            eventReleaseBy = "";
        }

        communityCareMapper.updateByIdStatus(date, eventReleaseBy, status, id);
    }
}
