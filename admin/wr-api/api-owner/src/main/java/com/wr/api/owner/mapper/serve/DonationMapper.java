package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.DonationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 21:13:44
 * @Desc: 服务-捐赠公告 mapper
 */
public interface DonationMapper {

    /**
     * 获取捐赠公告列表
     * @param communityId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.DonationVO>
     */
    List<DonationVO> getDonationAll(@Param("communityId") Long communityId);

    /**
     * 获取捐赠公告详情
     * @param donationId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.DonationVO>
     */
    DonationVO getDonationById(@Param("donationId") Long donationId);
}
