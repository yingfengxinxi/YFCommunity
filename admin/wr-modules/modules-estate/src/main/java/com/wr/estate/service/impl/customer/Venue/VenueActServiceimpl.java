package com.wr.estate.service.impl.customer.Venue;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.VenueActEneity;
import com.wr.estate.mapper.customer.Venue.VenueActMapper;
import com.wr.estate.service.customer.Venue.VenueActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/24
 * @Description:
 * @FileName: VenueActServiceimpl
 * @History:
 */
@Service
public class VenueActServiceimpl implements VenueActService {
    @Autowired
    VenueActMapper venueActMapper;
    @Override
    public VenueActEneity getAct(Long activityId) {
        return venueActMapper.getAct(activityId);
    }

    @Override
    public List<VenueActEneity> getlist(VenueActEneity busVenueAct) {
        return venueActMapper.getlist(busVenueAct);
    }

    @Override
    public int add(VenueActEneity busVenueAct) {
        String username = SecurityUtils.getUsername();
        busVenueAct.setCreateBy(username);
        long cid = busVenueAct.getCommunityId();
        int row = venueActMapper.getActivityName(cid,busVenueAct.getActivityName());
        if (row == 0){
            return venueActMapper.add(busVenueAct);
        }else {
            return  -123;
        }
    }

    @Override
    public int updateVenueAct(VenueActEneity busVenueAct) {
        String username = SecurityUtils.getUsername();
        busVenueAct.setUpdateBy(username);
        return venueActMapper.updateVenueAct(busVenueAct);
    }

    @Override
    public int del(Long activityId) {
        return venueActMapper.del(activityId);
    }

    @Override
    public int delforeach(Long[] activityIds) {
        return venueActMapper.delforeach(activityIds);
    }
}
