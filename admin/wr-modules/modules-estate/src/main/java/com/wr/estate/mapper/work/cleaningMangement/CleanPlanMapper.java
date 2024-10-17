package com.wr.estate.mapper.work.cleaningMangement;

import com.wr.remote.work.cleaningManagement.CleanPlan;
import com.wr.remote.work.cleaningManagement.CleanPlanExample;
import com.wr.remote.work.distribution.Distribution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CleanPlanMapper {
    int countByExample(CleanPlanExample example);

    int deleteByExample(CleanPlanExample example);

    int insert(CleanPlan record);

    int insertSelective(CleanPlan record);

    List<CleanPlan> selectByExample(CleanPlanExample example);

    CleanPlan selectByPrimaryKey(Long planId);

    int updateByExampleSelective(@Param("record") CleanPlan record, @Param("example") CleanPlanExample example);

    int updateByExample(@Param("record") CleanPlan record, @Param("example") CleanPlanExample example);

    int updateByPrimaryKeySelective(CleanPlan record);

    int updateByPrimaryKey(CleanPlan record);

    Distribution selectOneDistribution(@Param("communityId") Long communityId, @Param("allotType")String allotType);

    int deleteByPrimaryKey(Long planId);

    List<CleanPlan>  SelectOpenPlanOrderCleanPlan();

    Distribution selectAllot(@Param("communityId") Long communityId, @Param("allotType")String allotType);

}
