package com.wr.api.owner.service.impl.home.activity;


import com.wr.api.owner.entity.home.activity.ApiActivity;
import com.wr.api.owner.entity.home.activity.ApiActivityOwner;
import com.wr.api.owner.entity.home.activity.VoluntaryUser;
import com.wr.api.owner.entity.home.activity.vo.apiActivityVo;
import com.wr.api.owner.entity.home.visitor.ApiAccount;
import com.wr.api.owner.mapper.home.activity.ApiActivityMapper;
import com.wr.api.owner.service.home.activity.ApiActivityService;
import com.wr.common.core.utils.CardNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区活动
 */
@Service
public class ApiActivityServiceImpl implements ApiActivityService {


    @Autowired
    private ApiActivityMapper apiActivityMapper;


    /**
     * 查列表
     * @param apiActivity
     * @return
     */
    @Override
    public List<ApiActivity> getList(ApiActivity apiActivity) {
        return apiActivityMapper.getList(apiActivity);
    }

    /**
     * 查社区活动 详情
     * @param apiActivity
     * @return
     */
    @Override
    public apiActivityVo getInfo(ApiActivity apiActivity) {
        //1：社区活动   2：志愿者活动
        if("1".equals(apiActivity.getTableStatus())){
            return apiActivityMapper.getInfo(apiActivity);
        }
        return apiActivityMapper.getVoluntaryInfo(apiActivity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(ApiActivityOwner apiActivityOwner) {

        //判断当前用户是否是社区活动
        if("1".equals(apiActivityOwner.getTableStatus()) && apiActivityMapper.getOwnerStatus(apiActivityOwner)==null){
            apiActivityMapper.updateInfo(apiActivityOwner.getActivityId());
            return apiActivityMapper.add(apiActivityOwner);
        }

        //判断是否是志愿者活动
        //生成VoluntaryUser实体
        VoluntaryUser voluntaryUser = newInstanceVoluntaryUser(apiActivityOwner);
        if("2".equals(apiActivityOwner.getTableStatus()) && apiActivityMapper.checkVoluntaryIsJoin(voluntaryUser)==0){
            return apiActivityMapper.addVoluntaryUser(voluntaryUser);
        }
        return 0;
    }

    /**
     * 签到
     * @param apiActivityOwner
     * @return
     */
    @Override
    @Transactional
    public Integer update(ApiActivityOwner apiActivityOwner) {
        ApiActivityOwner activity = apiActivityMapper.getOwnerStatus(apiActivityOwner);
        //判断当前用户是否参与活动， 且是否签到过
        if(activity!=null){
            if(activity.getSignUpTime()!=null){
                return 0;
            }else{
                return apiActivityMapper.update(apiActivityOwner);
            }
        }
        return 0;
    }

    @Override
    public ApiActivityOwner getOwnerStatus(ApiActivityOwner apiActivityOwner) {
        return apiActivityMapper.getOwnerStatus(apiActivityOwner);
    }

    @Override
    public ApiActivity activityStatus(ApiActivity apiActivity) {
        return apiActivityMapper.activityStatus(apiActivity);
    }




    /**
     * 生成一个VoluntaryUser
     * @param apiActivityOwner
     */
    public VoluntaryUser newInstanceVoluntaryUser(ApiActivityOwner apiActivityOwner){

        //根据accountId查询当前用户信息
       ApiAccount account = apiActivityMapper.selectAccountById(apiActivityOwner.getAccountId());


        //生成一个实体类
        VoluntaryUser voluntaryUser = new VoluntaryUser();
        voluntaryUser.setVoluntaryId(apiActivityOwner.getActivityId());
        voluntaryUser.setCommunityId(apiActivityOwner.getCommunityId());
        voluntaryUser.setUserName(account.getAccountName());
        voluntaryUser.setAvatar(account.getAvatar());
        voluntaryUser.setGender(account.getGender());
        voluntaryUser.setCardType(account.getCardType());
        voluntaryUser.setCardNo(account.getCardNo());
        voluntaryUser.setUserPhone(account.getUserPhone());
        voluntaryUser.setUserAge(CardNoUtil.countAge(account.getCardNo()));
        return voluntaryUser;
    }

    @Override
    public int checkVoluntaryIsJoin(ApiActivity apiActivity) {
         int i =0;
        //根据accountId查询当前用户信息
        ApiAccount account = apiActivityMapper.selectAccountById(apiActivity.getAccountId());

        if(account!=null){
            VoluntaryUser voluntaryUser = new VoluntaryUser();
            voluntaryUser.setCardNo(account.getCardNo());
            voluntaryUser.setVoluntaryId(apiActivity.getActivityId());
             i = apiActivityMapper.checkVoluntaryIsJoin(voluntaryUser);
            return i;
        }

            return i;
    }

    /**
     * 参与活动的物资List
     * @param activityId 查询条件
     * @return 对象列表
     */
    @Override
    public List<ApiActivity> selectJoinSupplyList(Long activityId) {
        return apiActivityMapper.selectJoinSupplyList(activityId);
    }
}
