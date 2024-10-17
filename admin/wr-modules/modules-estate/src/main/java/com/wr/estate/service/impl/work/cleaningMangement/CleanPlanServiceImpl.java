package com.wr.estate.service.impl.work.cleaningMangement;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.cleaningMangement.CleanPlanMapper;
import com.wr.estate.service.work.cleaningMangement.CleanPlanService;
import com.wr.remote.work.cleaningManagement.CleanPlan;
import com.wr.remote.work.cleaningManagement.CleanPlanExample;
import com.wr.remote.work.distribution.Distribution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 16:05:06
 * @Desc: 保洁计划实现类
 */
@Service
public class CleanPlanServiceImpl implements CleanPlanService {

    @Resource
    private CleanPlanMapper cleanPlanMapper;

    /**
     * 查询保洁计划集合
     * @param cleanPlan
     * @return
     */
    @Override
    public List<CleanPlan> selectCleanPlanList(CleanPlan cleanPlan) {
        //条件类
        CleanPlanExample cleanPlanExample = new CleanPlanExample();
        CleanPlanExample.Criteria criteria = cleanPlanExample.createCriteria();
        criteria.andCommunityIdEqualTo(cleanPlan.getCommunityId());
        criteria.andDelFlagEqualTo("0");
        cleanPlanExample.setOrderByClause("plan_id DESC");
        if (cleanPlan.getPlanName()!=null && cleanPlan.getPlanName()!="")
        {
            criteria.andPlanNameLike("%"+cleanPlan.getPlanName()+"%");
        }
        if (cleanPlan.getAllotWay() != null && cleanPlan.getAllotWay()!="")
        {
            criteria.andAllotWayEqualTo(cleanPlan.getAllotWay());
        }if (cleanPlan.getPlanOrder()!=null && cleanPlan.getPlanOrder()!="")
        {
            criteria.andPlanOrderEqualTo(cleanPlan.getPlanOrder());
        }
        return cleanPlanMapper.selectByExample(cleanPlanExample);
    }

    /**
     * 根据ID查询单个保洁计划
     * @param planId
     * @return
     */
    @Override
    public CleanPlan selectCleanPlanOne(Long planId) {
        CleanPlan cleanPlan = cleanPlanMapper.selectByPrimaryKey(planId);
        return cleanPlan;
    }

    /**
     * 修改或新增保洁计划
     * @param cleanPlan
     * @return
     */
    @Override
    public int insertAndupdataCleanPlanOneand(CleanPlan cleanPlan) {
        //判断有ID没 有就是修改 没有就是新增
        if (cleanPlan.getPlanId()!=null ){
            //设置修改人和修改时间
            cleanPlan.setUpdateBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
            cleanPlan.setUpdateTime(new Date());
             int updata = cleanPlanMapper.updateByPrimaryKeySelective(cleanPlan);
             return updata;
        }else {
            //设置创建人和创建时间
            cleanPlan.setCreateBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
            cleanPlan.setCreateTime(new Date());
            //获取配置方式
            Distribution distribution = cleanPlanMapper.selectOneDistribution(cleanPlan.getCommunityId(), "3");
            if (distribution==null){
                return -2;
            }
            //设置配置方式
            cleanPlan.setAllotWay(distribution.getAllotWay());
            int insert = cleanPlanMapper.insertSelective(cleanPlan);
            return insert;
        }
    }

    /**
     * 删除单个或多个保洁计划
     * @param planIds
     */
    @Override
    public void delectCleanPlan(Long[] planIds) {
        for (Long planId : planIds) {
          cleanPlanMapper.deleteByPrimaryKey(planId);
        }
    }

    /**
     * 修改状态
     * @param planId
     * @return
     */
    @Override
    public int updataPlanOrderStatue(Long planId) {
        CleanPlan cleanPlan = cleanPlanMapper.selectByPrimaryKey(planId);
        if (cleanPlan.getPlanOrder().equals("0")){
            cleanPlan.setPlanOrder("1");
            //设置修改人和修改时间
            cleanPlan.setUpdateBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
            cleanPlan.setUpdateTime(new Date());
            //修改
            return cleanPlanMapper.updateByPrimaryKeySelective(cleanPlan);
        }else {
            cleanPlan.setPlanOrder("0");
            //设置修改人和修改时间
            cleanPlan.setUpdateBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
            cleanPlan.setUpdateTime(new Date());
            //修改
            return  cleanPlanMapper.updateByPrimaryKeySelective(cleanPlan);
        }
    }
}
