package com.wr.estate.service.impl.customer.Venue;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.VenueRuleEntity;
import com.wr.estate.mapper.customer.Venue.VenueRuleMapper;
import com.wr.estate.service.customer.Venue.VenueRuleService;
import com.wr.remote.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueRuleServiceImpl
 * @History:
 */
@Service
public class VenueRuleServiceimpl implements VenueRuleService {
    @Autowired
    private VenueRuleMapper venueRuleMapper;
    @Override
    public VenueRuleEntity selectVenueRuleByRuleId(Long ruleId) {
        return null;
    }

    @Override
    public List<VenueRuleEntity> selectVenueRuleList(VenueRuleEntity venueRule) {
        return venueRuleMapper.selectVenueRuleList(venueRule);
    }

    @Override
    public VenueRuleEntity selectVenueRule(Long estateId) {
        return venueRuleMapper.selectVenueRule(estateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertVenueRule(VenueRuleEntity venueRule) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        //获取该用户所属estate
        venueRule.setCreateOrder("0");
        venueRule.setGapHour(0L);
        venueRule.setEstateId(sysUser.getEstateId());
        return venueRuleMapper.insertVenueRule(venueRule);
    }

    @Override
    public int updateVenueRule(VenueRuleEntity venueRule) {
        return venueRuleMapper.updateVenueRule(venueRule);
    }

    @Override
    public int deleteVenueRuleByRuleIds(Long[] ruleIds) {
        return 0;
    }

    @Override
    public int deleteVenueRuleByRuleId(Long ruleId) {
        return 0;
    }
}
