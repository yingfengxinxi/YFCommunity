package com.wr.estate.mapper.financial;

import com.wr.remote.domain.PropertyFeeBuilding;
import com.wr.remote.domain.PropertyFeeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/3
 * @Description:
 * @FileName: PropertyFeeMapper
 * @History:
 */
@Repository
public interface PropertyFeeMapper {
    /**
     * 查询物业缴费设置
     *
     * @param propertyFeeId 物业缴费设置主键
     * @return 物业缴费设置
     */
    public PropertyFeeEntity selectBusPropertyFeeByCommunityId(String propertyFeeId);

    /**
     * 查询物业缴费设置列表
     *
     * @param propertyFee 物业缴费设置
     * @return 物业缴费设置集合
     */
    public List<PropertyFeeEntity> selectBusPropertyFeeList(PropertyFeeEntity propertyFee);


    /**
     * @param buildingId
     * @param communityId
     * @return
     */
    public Integer getByBuildingIdPropertyFeeCount(@Param("buildingId") Long buildingId, @Param("communityId") Long communityId);

    /**
     * 新增物业缴费设置
     *
     * @param propertyFee 物业缴费设置
     * @return 结果
     */
    public int insertBusPropertyFee(PropertyFeeEntity propertyFee);

    /**
     * @param propertyFeeId
     * @return
     */
    public String getByPropertyFeeIdBuildingInfo(@Param("propertyFeeId") String propertyFeeId);

    /**
     * @param propertyFeeId
     */
    public void deleteByPropertyFeeBuilding(@Param("propertyFeeId") String propertyFeeId);

    public int insertBusPropertyFeeBuilding(PropertyFeeBuilding propertyFeeBuilding);

    /**
     * 修改物业缴费设置
     *
     * @param propertyFee 物业缴费设置
     * @return 结果
     */
    public int updateBusPropertyFee(PropertyFeeEntity propertyFee);

    /**
     * 删除物业缴费设置
     *
     * @param propertyFeeId 物业缴费设置主键
     * @return 结果
     */
    public int deleteBusPropertyFeeByCommunityId(@Param("propertyFeeId") String propertyFeeId);

    /**
     * @param propertyFeeId
     */
    void clearByPropertyFeeIdTime(@Param("propertyFeeId") String propertyFeeId);

    /**
     * 批量删除物业缴费设置
     *
     * @param communityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusPropertyFeeByCommunityIds(Long[] communityIds);

    int getxqCount(Long communityId);

    /**
     * @param communityId
     * @param feeType
     * @param payMethod
     * @param propertyFeeId
     * @param buildingIds
     * @return
     */
    Integer getCommunityIdFeeTypeCount(@Param("communityId") Long communityId, @Param("feeType") String feeType, @Param("payMethod") String payMethod, @Param("propertyFeeId") String propertyFeeId, @Param("buildingIds") List<String> buildingIds);

    /**
     * @return
     */
    List<String> getEntTimeFeeIdList();

    /**
     * @param propertyFeeId
     * @return
     */
    Integer getPropertyIsPayCount(@Param("propertyFeeId") String propertyFeeId);

    /**
     * @return
     */
    List<PropertyFeeEntity> getAllPropertyFeeList();
}
