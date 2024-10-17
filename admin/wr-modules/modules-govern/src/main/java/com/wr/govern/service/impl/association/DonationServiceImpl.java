package com.wr.govern.service.impl.association;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.association.DonationMapper;
import com.wr.govern.service.CommunityService;
import com.wr.govern.service.association.DonationService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.association.Donation;
import com.wr.remote.govern.association.vo.DonationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-28 09:33:01
 * @Desc: 业务捐赠业务层
 */
@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationMapper donationMapper;


    @Override
    public List<Donation> getList(Donation donation) {
        return donationMapper.getList(donation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addDonation(DonationVO donationVO) {
        donationVO.setCreateBy(SecurityUtils.getUsername());
        donationVO.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        Integer result = donationMapper.addDonation(donationVO);

        Integer rangResult = saveDonationRang(donationVO.getDonationId(), donationVO.getCommunityIds());
        return result+rangResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateDonation(DonationVO donation) {
        //修改数据
        donation.setUpdateBy(SecurityUtils.getUsername());
        Integer result = donationMapper.updateDonation(donation);
        //修改关联表
        removeDonationRang(donation.getDonationId());
        saveDonationRang(donation.getDonationId(),donation.getCommunityIds());
        return result;
    }

    @Override
    public DonationVO getDonationDetail(Long donationId) {
        DonationVO donationDetail = donationMapper.getDonationDetail(donationId, SecurityUtils.getUsername());
        Long[] ids= donationMapper.getDonationRangInfo(donationDetail.getDonationId());
        donationDetail.setCommunityIds(ids);
        return donationDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer delDonation(Long donationId) {
        //删除主表
        Integer mainResult = donationMapper.delDonation(donationId);
        //删除关联表
        Integer rangResult = removeDonationRang(donationId);
        return mainResult+rangResult;
    }

    /**
     * 添加关联表
     * @param donationId
     * @param communityIds
     * @return
     */
    public Integer saveDonationRang(Long donationId,Long[] communityIds){
        return donationMapper.addDonationRang(donationId,communityIds);
    }

    /**
     * 删除关联表
     * @param donationId
     * @return
     */
    public Integer removeDonationRang(Long donationId){
        return donationMapper.removeDonationRang(donationId);
    }
}
