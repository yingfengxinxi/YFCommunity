package com.wr.estate.service.impl.customer.Venue;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.VenueEntity;
import com.wr.estate.entity.vo.VenueVo;
import com.wr.estate.mapper.customer.Venue.VenueMapper;
import com.wr.estate.service.customer.Venue.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueServiceImpl
 * @History:
 */
@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueMapper venueMapper;
    @Override
    public VenueEntity selectVenueByVenueId(Long venueId) {
        return venueMapper.selectVenueByVenueId(venueId);
    }

    @Override
    public List<VenueVo> selectVenueList(VenueEntity venue) {
        return venueMapper.selectVenueList(venue);
    }

    @Override
    public int insertVenue(VenueEntity venue) {
        String username = SecurityUtils.getUsername();
        venue.setCreateBy(username);
        return venueMapper.insertVenue(venue);
    }

    @Override
    public int updateVenue(VenueEntity venue) {
        String username = SecurityUtils.getUsername();
        venue.setUpdateBy(username);
        return venueMapper.updateVenue(venue);
    }

    @Override
    public int deleteVenueByVenueIds(Long[] venueIds) {
        return venueMapper.deleteVenueByVenueIds(venueIds);
    }

    @Override
    public int deleteVenueByVenueId(Long venueId) {
        return venueMapper.deleteVenueByVenueId(venueId);
    }

    @Override
    public List<VenueVo> g(Long communityId) {
        return venueMapper.g(communityId);
    }

    @Override
    public List<VenueVo> downVenueList(VenueVo venueVo) {
        return venueMapper.downVenueList(venueVo);
    }
}
