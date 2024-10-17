package com.wr.estate.mapper.customer.Venue;

import com.wr.estate.entity.VenueRuleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueRule
 * @History:
 */
@Repository
public interface VenueRuleMapper {

    /**
     * 查询场馆活动
     *
     * @param ruleId 场馆活动主键
     * @return 场馆活动
     */
    public VenueRuleMapper selectVenueRuleByRuleId(Long ruleId);

    /**
     * 查询场馆活动列表
     *
     * @param venueRule 场馆活动
     * @return 场馆活动集合
     */
    public List<VenueRuleEntity> selectVenueRuleList(VenueRuleEntity venueRule);

    /**
     * 查看当前物业场馆预约规则
     * @param estateId
     * @return: com.wr.estate.entity.VenueRuleEntity
     * @date: 2023/4/13 17:25
     * @author: SJiang
     **/
    VenueRuleEntity selectVenueRule(@Param("estateId") Long estateId);

    /**
     * 新增场馆活动
     *
     * @param venueRule 场馆活动
     * @return 结果
     */
    public int insertVenueRule(VenueRuleEntity venueRule);

    /**
     * 修改场馆活动
     *
     * @param venueRule 场馆活动
     * @return 结果
     */
    public int updateVenueRule(VenueRuleEntity venueRule);

    ///**
    // * 删除场馆活动
    // *
    // * @param ruleId 场馆活动主键
    // * @return 结果
    // */
    //public int deleteVenueRuleByRuleId(Long ruleId);
    //
    ///**
    // * 批量删除场馆活动
    // *
    // * @param ruleIds 需要删除的数据主键集合
    // * @return 结果
    // */
    //public int deleteVenueRuleByRuleIds(Long[] ruleIds);


}
