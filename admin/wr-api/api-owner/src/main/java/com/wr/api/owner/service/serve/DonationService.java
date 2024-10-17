package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.DonationVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 21:22:06
 * @Desc: 服务-捐赠公告 业务
 */
public interface DonationService {

    /**
     * 获取捐赠公告列表
     * @param communityId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.DonationVO>
     */
    List<DonationVO> getDonationAll(Long communityId);

    /**
     * 获取捐赠公告详情
     * @param donationId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.DonationVO>
     */
    DonationVO getDonationById(Long donationId);
}
