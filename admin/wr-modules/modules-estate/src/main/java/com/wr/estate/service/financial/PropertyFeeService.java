package com.wr.estate.service.financial;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.PropertyFeeEntity;

import java.text.ParseException;
import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/3
 * @Description:
 * @FileName: PropertyFeeService
 * @History:
 */
public interface PropertyFeeService {


    /**
     * 查询物业缴费设置
     *
     * @param propertyFeeId 物业缴费设置主键
     * @return 物业缴费设置
     */
    public AjaxResult selectBusPropertyFeeByCommunityId(String propertyFeeId);

    /**
     * @param propertyFeeId
     * @return
     */
    public String getByPropertyFeeIdBuildingInfo(String propertyFeeId);

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
    public Integer getByBuildingIdPropertyFeeCount(Long buildingId, Long communityId);


    /**
     * @return
     */
    List<PropertyFeeEntity> getAllPropertyFeeList();

    /**
     * 新增物业缴费设置
     *
     * @param propertyFee 物业缴费设置
     * @return 结果
     */
    public AjaxResult insertBusPropertyFee(PropertyFeeEntity propertyFee) throws ParseException;

    /**
     * 生成账单
     *
     * @param propertyFee
     * @param buildingId
     * @throws Exception
     */
    public void generatePropertyBill(PropertyFeeEntity propertyFee, String buildingId) throws Exception;


    /**
     * 未交房物业费
     *
     * @param propertyFee
     * @param buildingId
     * @throws Exception
     */
    public void unpaidPropertyGeneratePropertyBill(PropertyFeeEntity propertyFee, String buildingId) throws Exception;

    /**
     * 修改物业缴费设置
     *
     * @param propertyFee 物业缴费设置
     * @return 结果
     */
    public AjaxResult updateBusPropertyFee(PropertyFeeEntity propertyFee) throws ParseException;

    public void updatePropertyFeeStatus(PropertyFeeEntity propertyFee);

    /**
     * 批量删除物业缴费设置
     *
     * @param communityIds 需要删除的物业缴费设置主键集合
     * @return 结果
     */
    public int deleteBusPropertyFeeByCommunityIds(Long[] communityIds);

    /**
     * 删除物业缴费设置信息
     *
     * @param propertyFeeId 物业缴费设置主键
     * @return 结果
     */
    public int deleteBusPropertyFeeByCommunityId(String propertyFeeId);

    /**
     * @param propertyFeeId
     */
    void clearByPropertyFeeIdTime(String propertyFeeId);

    public void deleteByPropertyFeeBuilding(String propertyFeeId);

    List<String> getEntTimeFeeIdList();

    /**
     *
     */
    public void generatePropertyBillTask();

    /**
     * 未交房生成账单
     */
    public void unpaidPropertyGeneratePropertyBill();
}
