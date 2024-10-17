package com.wr.estate.mapper.customer.Venue;

import com.wr.estate.entity.VenueActEneity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/24
 * @Description:
 * @FileName: VenueActMapper
 * @History:
 */
@Repository
public interface VenueActMapper {

    /**
     * 查询场馆活动
     *
     * @param activityId 场馆活动主键
     * @return 场馆活动
     */
    public VenueActEneity getAct(Long activityId);

    /**
     * 查询场馆活动列表
     *
     * @param busVenueAct 场馆活动
     * @return 场馆活动集合
     */
    public List<VenueActEneity> getlist(VenueActEneity busVenueAct);

    /**
     * 新增场馆活动
     *
     * @param busVenueAct 场馆活动
     * @return 结果
     */
    public int add(VenueActEneity busVenueAct);

    /**
     * 修改场馆活动
     *
     * @param busVenueAct 场馆活动
     * @return 结果
     */
    public int updateVenueAct(VenueActEneity busVenueAct);

    /**
     * 删除场馆活动
     *
     * @param activityId 场馆活动主键
     * @return 结果
     */
    public int del(Long activityId);

    /**
     * 批量删除场馆活动
     *
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int delforeach(Long[] activityIds);

    /**
     *
     *根据社区id获取场馆活动
     */
    List<VenueActEneity> getact(Long communityId);



    int getActivityName(@Param("communityId") Long communityId, @Param("activityName") String activityName);




}
