package com.wr.estate.mapper.activity;

import com.wr.remote.estate.activity.ActiviteManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:11:50
 * @Desc:
 */
public interface ActiviteManageMapper {

    /**
     * 查询看板数据表列表
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    List<ActiviteManage> selectList(ActiviteManage activiteManage);

    /**
     * 活动类型列表
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    List<ActiviteManage> selectTypeList(ActiviteManage activiteManage);

    /**
     * 新增活动
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    int insertActivite(ActiviteManage activiteManage);

    /**
     * 新增楼栋表
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    int insertBuilding(ActiviteManage activiteManage);

    /**
     * 编辑活动
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    int updateActivity(ActiviteManage activiteManage);

    /**
     * 编辑活动
     *
     * @param activityId 查询条件
     * @return 对象列表
     */
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

    /**
     * 楼栋号List
     *
     * @param activityId 查询条件
     * @return 对象列表
     */
    List<ActiviteManage> selectBuildingList(Long activityId);

    /**
     * 新增楼栋号List
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    List<ActiviteManage> selectBuildingListById(Map<String, Object> params);

    /**
     * 参与活动的楼栋号List
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    List<ActiviteManage> selectJoinBuildingList(ActiviteManage activiteManage);

    /**
     * 根据小区跟楼栋号查询业主List
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    int selectOwnerList(Long activityId);

    /**
     * 新增参与人员表
     *
     * @param activiteManage 查询条件
     * @return 对象列表
     */
    int insertOwner(ActiviteManage activiteManage);

    /**
     * 根据活动名称查询活动是否存在
     *
     * @param activiteId 查询条件
     * @return 对象列表
     */
    int selectActivityName(@Param("activityName") String activityName, @Param("communityId") Long communityId, @Param("activityId") Long activityId);

    /**
     * 删除
     *
     * @param activiteId 查询条件
     * @return 对象列表
     */
    int deleteById(Long activiteId);


    List<Long> getBuilding(Long activityId);

    List<ActiviteManage> getAllBuilding(Long activityId);

    List<ActiviteManage> getAll(Long activityId);

    /**
     * 根据活动id查询物资关联表
     *
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
     *
     * @param activityId
     */
    void deleteAct(Long activityId);
}
