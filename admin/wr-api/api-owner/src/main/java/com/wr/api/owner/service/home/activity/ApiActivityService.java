package com.wr.api.owner.service.home.activity;


import com.wr.api.owner.entity.home.activity.ApiActivity;
import com.wr.api.owner.entity.home.activity.ApiActivityOwner;
import com.wr.api.owner.entity.home.activity.vo.apiActivityVo;
import com.wr.api.owner.entity.home.notice.Invest;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区活动
 */
public interface ApiActivityService {


    /**
     * 查列表
     * @param apiActivity
     * @return
     */
    List<ApiActivity> getList(ApiActivity apiActivity);

    /**
     * 查社区活动 详情
     * @param apiActivity
     * @return
     */
    apiActivityVo getInfo(ApiActivity apiActivity);


    /**
     * 新增
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


    ApiActivityOwner getOwnerStatus(ApiActivityOwner apiActivityOwner);


    ApiActivity activityStatus(ApiActivity apiActivity);

    int checkVoluntaryIsJoin(ApiActivity apiActivity);

    /**
     * 参与活动的物资List
     * @param activityId 查询条件
     * @return 对象列表
     */
    List<ApiActivity> selectJoinSupplyList(Long activityId);
}
