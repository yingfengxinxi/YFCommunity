package com.wr.estate.service.impl.activity;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.estate.mapper.activity.ActiviteManageMapper;
import com.wr.estate.service.activity.ActiviteManageService;
import com.wr.remote.estate.activity.ActiviteManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-11 15:10:22
 * @Desc:
 */
@Service
public class ActiviteManageServicceImpl implements ActiviteManageService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ActiviteManageMapper activiteManageMapper;

    @Override
    public List<ActiviteManage> selectList(ActiviteManage activiteManage) {
        List<ActiviteManage> activiteManages = activiteManageMapper.selectList(activiteManage);
        if (CollectionUtils.isNotEmpty(activiteManages)) {
            activiteManages.stream().forEach(manage -> {
                manage.setActState(stateName(manage.getActState()));
            });
        }
        return activiteManages;
    }

    private String stateName(String state) {
        if ("0".equals(state)) {
            state = "进行中";
        }
        if ("1".equals(state)) {
            state = "未开始";
        }
        if ("2".equals(state)) {
            state = "已结束";
        }
        return state;
    }

    @Override
    public List<ActiviteManage> selectTypeList(ActiviteManage activiteManage) {
        return activiteManageMapper.selectTypeList(activiteManage);
    }

    @Override
    public int insertActivite(ActiviteManage activiteManage) {
        return activiteManageMapper.insertActivite(activiteManage);
    }

    @Override
    public int updateActivity(ActiviteManage activiteManage) {
        return activiteManageMapper.updateActivity(activiteManage);
    }

    @Override
    public ActiviteManage getActivityById(Long activityId) {
        return activiteManageMapper.getActivityById(activityId);
    }

    @Override
    public ActiviteManage selectJoinPeople(Long activityId) {
        return activiteManageMapper.selectJoinPeople(activityId);
    }

    @Override
    public List<ActiviteManage> selectJoinPeopleList(ActiviteManage activiteManage) {
        return activiteManageMapper.selectJoinPeopleList(activiteManage);
    }

    @Override
    public List<ActiviteManage> selectBuildingList(Long activityId) {
        return activiteManageMapper.selectBuildingList(activityId);
    }

    @Override
    public List<ActiviteManage> selectBuildingListById(ActiviteManage activiteManage) {
        return activiteManageMapper.getAllBuilding(activiteManage.getCommunityId());
    }

    @Override
    public List<ActiviteManage> selectBuildingListById1(ActiviteManage activiteManage) {
        List<Long> buildingIds = activiteManageMapper.getBuilding(activiteManage.getActivityId());
        if (buildingIds.size() == 0) {
            buildingIds.add((long) -1);
        }
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("communityId", activiteManage.getCommunityId());
        params.put("buildingIds", buildingIds);
        return activiteManageMapper.selectBuildingListById(params);
    }

    @Override
    public int insertBuilding(ActiviteManage activiteManage) {
        return activiteManageMapper.insertBuilding(activiteManage);
    }



    @Override
    public List<ActiviteManage> selectJoinBuildingList(ActiviteManage activiteManage) {
        return activiteManageMapper.selectJoinBuildingList(activiteManage);
    }

    @Override
    public int selectOwnerList(Long activityId) {
        return activiteManageMapper.selectOwnerList(activityId);
    }

    @Override
    public int insertOwner(ActiviteManage activiteManage) {
        return activiteManageMapper.insertOwner(activiteManage);
    }

    @Override
    public int selectActivityName(String activityName,Long communityId,Long activityId) {
        return activiteManageMapper.selectActivityName(activityName,communityId,activityId);
    }

    @Override
    public int deleteById(Long activiteId) {
        return activiteManageMapper.deleteById(activiteId);
    }

    @Override
    public List<ActiviteManage> selectBuildingListBy(Long activityId) {
        return activiteManageMapper.getAll(activityId);
    }

    /**
     * 根据活动id查询物资关联表
     *
     * @param activityId
     * @return
     */
    @Override
    public List<ActiviteManage> getDistributionLists(Long activityId) {
        return activiteManageMapper.getDistributionLists(activityId);
    }

    /**
     * 删除物资关联表
     *
     * @param activityId
     */
    @Transactional
    @Override
    public void deleteSupply(Long activityId) {
        activiteManageMapper.deleteSupply(activityId);
    }

    /**
     * 删除活动-楼栋关联表
     *
     * @param activityId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAct(Long activityId) {
        activiteManageMapper.deleteAct(activityId);
    }
}
