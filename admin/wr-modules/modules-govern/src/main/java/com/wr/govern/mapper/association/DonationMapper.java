package com.wr.govern.mapper.association;

import com.wr.remote.govern.association.Donation;
import com.wr.remote.govern.association.vo.DonationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-28 09:33:42
 * @Desc: 业务捐赠DAO层
 */
@Mapper
public interface DonationMapper {


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
    DonationVO getDonationDetail(@Param("donationId")Long donationId,@Param("userName") String userName);

    /**
     * 删除业主捐赠信息
     * @param donationId
     * @return
     */
    Integer delDonation(Long donationId);

    /**
     * 添加关联表
     * @param donationId
     * @param communityIds
     * @return
     */
    Integer addDonationRang(@Param("donationId")Long donationId,@Param("communityIds") Long[] communityIds);

    /**
     * 查询关联表信息
     * @param donationId
     * @param
     * @return
     */
    Long[] getDonationRangInfo(Long donationId);

    /**
     * 删除关联表信息
     * @param donationId
     * @return
     */
    Integer removeDonationRang(Long donationId);

}
