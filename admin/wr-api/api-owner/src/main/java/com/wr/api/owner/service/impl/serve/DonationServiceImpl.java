package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.DonationVO;
import com.wr.api.owner.mapper.serve.DonationMapper;
import com.wr.api.owner.service.serve.DonationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 21:22:54
 * @Desc: 服务-捐赠公告 业务实现
 */
@Service
@Slf4j
public class DonationServiceImpl implements DonationService {
    @Autowired
    private DonationMapper donationMapper;

    @Override
    public List<DonationVO> getDonationAll(Long communityId) {
        return donationMapper.getDonationAll(communityId);
    }

    @Override
    public DonationVO getDonationById(Long donationId) {
        return donationMapper.getDonationById(donationId);
    }
}
