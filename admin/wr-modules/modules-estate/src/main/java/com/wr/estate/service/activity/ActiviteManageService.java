package com.wr.estate.service.activity;

import com.wr.remote.estate.activity.ActiviteManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:39:49
 * @Desc:
 */
public interface ActiviteManageService {

    List<ActiviteManage> selectList(ActiviteManage activiteManage);

    List<ActiviteManage> selectTypeList(ActiviteManage activiteManage);

    int insertActivite(ActiviteManage activiteManage);

    int updateActivity(ActiviteManage activiteManage);

    ActiviteManage getActivityById(Long activityId);

    /**
     * 活动基本信息
     *
     * @param activityId 查询条件
     * @return 对象列表
     */
    ActiviteManage selectJoinPeople(@Param("activityId") Long activityId);

    /**
     * 参与人员List
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    List<ActiviteManage> selectJoinPeopleList(ActiviteManage activiteManage);

    List<ActiviteManage> selectBuildingList(Long activityId);

    List<ActiviteManage> selectBuildingListById(ActiviteManage activiteManage);
    List<ActiviteManage> selectBuildingListById1(ActiviteManage activiteManage);

    int insertBuilding(ActiviteManage activiteManage);



    List<ActiviteManage> selectJoinBuildingList(ActiviteManage activiteManage);


    int selectOwnerList(Long activityId);

    int insertOwner(ActiviteManage activiteManage);

    int selectActivityName(String activityName,Long communityId,Long activityId);

    int deleteById(Long activiteId);

    List<ActiviteManage> selectBuildingListBy(Long activityId);

    /**
     * @param activityId
     * @return
     */
    List<ActiviteManage> getDistributionLists(Long activityId);

    /**
     * 删除物资关联表
     *
     * @param activityId
     */
    void deleteSupply(Long activityId);

    /**
     * 删除活动-楼栋关联表
     * @param activityId
     */
    void deleteAct(Long activityId);
}
