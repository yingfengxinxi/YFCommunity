package com.wr.govern.service.association;

import com.wr.remote.govern.association.Donation;
import com.wr.remote.govern.association.vo.DonationVO;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-28 09:32:43
 * @Desc: 业务捐赠接口层
 */
public interface DonationService {

    /**
     * 获取业主捐赠列表
     * @param donation
     * @return
     */
    List<Donation> getList(Donation donation);

    /**
     * 添加业主捐赠列表
     * @param donationVO
     * @return
     */
    Integer addDonation(DonationVO donationVO);

    /**
     * 修改业主捐赠信息
     * @param donation
     * @return
     */
    Integer updateDonation(DonationVO donation);

    /**
     * 业主捐赠信息详情
     * @param donationId
     * @return
     */
    DonationVO getDonationDetail(Long donationId);

    /**
     * 删除业主捐赠信息
     * @param donationId
     * @return
     */
    Integer delDonation(Long donationId);
}
