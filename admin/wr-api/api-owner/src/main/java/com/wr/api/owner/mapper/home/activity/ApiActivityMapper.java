package com.wr.api.owner.mapper.home.activity;

import com.wr.api.owner.entity.home.activity.ApiActivity;
import com.wr.api.owner.entity.home.activity.ApiActivityOwner;
import com.wr.api.owner.entity.home.activity.VoluntaryUser;
import com.wr.api.owner.entity.home.activity.vo.apiActivityVo;
import com.wr.api.owner.entity.home.visitor.ApiAccount;
import com.wr.remote.estate.activity.ActiviteManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公告
 */
@Mapper
public interface ApiActivityMapper {

    /**
     * 查列表
     * @param apiActivity
     * @return
     */
    List<ApiActivity> getList(ApiActivity apiActivity);

    /**
     * 查列表
     * @param apiActivity
     * @return
     */
    apiActivityVo getInfo(ApiActivity apiActivity);

    /**
     * 修改
     * @param apiActivityOwner
     * @return
     */
    Integer add(ApiActivityOwner apiActivityOwner);

    /**
     * 修改
     * @param apiActivityOwner
     * @return
     */
    Integer update(ApiActivityOwner apiActivityOwner);

    /**
     * 查询当前用户是否已经报名
     * @param apiActivityOwner
     * @return
     */
    ApiActivityOwner getOwnerStatus(ApiActivityOwner apiActivityOwner);


    ApiActivity activityStatus(ApiActivity apiActivity);

    apiActivityVo getVoluntaryInfo(ApiActivity apiActivity);

    Integer addVoluntaryUser(VoluntaryUser voluntaryUser);

    ApiAccount selectAccountById(Long accountId);

    int checkVoluntaryIsJoin(VoluntaryUser voluntaryUser);

    /**
     * 修改活动
     * @param activityId
     * @return
     */
    Integer updateInfo(Long activityId);

    /**
     * 参与活动的物资List
     * @param activityId 查询条件
     * @return 对象列表
     */
    List<ApiActivity> selectJoinSupplyList(Long activityId);
}
