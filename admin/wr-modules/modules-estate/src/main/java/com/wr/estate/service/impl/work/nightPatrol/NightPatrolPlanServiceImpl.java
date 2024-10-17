package com.wr.estate.service.impl.work.nightPatrol;

import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.distribution.DistributionMapper;
import com.wr.estate.mapper.work.nightPatrol.WatchmanMapper;
import com.wr.estate.mapper.work.nightPatrol.WatchmanPlanMapper;
import com.wr.estate.mapper.work.nightPatrol.WatchmanPointMapper;
import com.wr.estate.mapper.work.patrol.PointMapper;
import com.wr.estate.service.manage.StaffService;
import com.wr.estate.service.work.nightPatrol.NightPatrolPlanService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.util.WorkOrderUtil;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.nightPatrol.Watchman;
import com.wr.remote.work.nightPatrol.WatchmanPlan;
import com.wr.remote.work.nightPatrol.vo.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 巡更计划
 *
 * @Author: 奔波儿灞
 * @Date: 2022-10-27 14:19:53
 * @Desc:
 */
@Service
public class NightPatrolPlanServiceImpl implements NightPatrolPlanService {

    @Autowired
    private WatchmanPlanMapper watchmanPlanMapper;
    @Autowired
    private WatchmanMapper watchmanMapper;
    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private WatchmanPointMapper watchmanPointMapper;

    @Autowired
    private DistributionMapper distributionMapper;

    @Autowired
    private StaffService staffService;

    /**
     * 查询巡更计划
     *
     * @param planId 巡更计划主键
     * @return 巡更计划
     */
    @Override
    public WatchmanPlan selectWatchmanPlanByPlanId(Long planId) {
        return watchmanPlanMapper.selectWatchmanPlanByPlanId(planId);
    }

    /**
     * 查询巡更计划列表
     *
     * @param watchmanPlan 巡更计划
     * @return 巡更计划
     */
    @Override
    public List<WatchmanPlan> selectWatchmanPlanList(WatchmanPlan watchmanPlan) {
        return watchmanPlanMapper.selectWatchmanPlanList(watchmanPlan);
    }

    /**
     * 新增巡更计划
     *
     * @param watchmanPlan 巡更计划
     * @return 结果
     */
    @Override
    public int insertWatchmanPlan(WatchmanPlan watchmanPlan) {
        String username = SecurityUtils.getUsername();
        watchmanPlan.setCreateTime(DateUtils.getNowDate());
        watchmanPlan.setCreateBy(username);
        watchmanPlan.setPlanOrder("0");
        int count = watchmanPlanMapper.countByName(watchmanPlan);
        if (count > 0) {
            throw new ServiceException("计划名称已存在！");
        }

        return watchmanPlanMapper.insertWatchmanPlan(watchmanPlan);
    }

    /**
     * 修改巡更计划
     *
     * @param watchmanPlan 巡更计划
     * @return 结果
     */
    @Override
    public int updateWatchmanPlan(WatchmanPlan watchmanPlan) {
        watchmanPlan.setUpdateTime(DateUtils.getNowDate());

        int count = watchmanPlanMapper.countByName(watchmanPlan);
        if (count > 0) {
            throw new ServiceException("计划名称已存在！");
        }

        return watchmanPlanMapper.updateWatchmanPlan(watchmanPlan);
    }

    /**
     * 批量删除巡更计划
     *
     * @param planIds 需要删除的巡更计划主键
     * @return 结果
     */
    @Override
    public int deleteWatchmanPlanByPlanIds(Long[] planIds) {
        return watchmanPlanMapper.deleteWatchmanPlanByPlanIds(planIds);
    }

    /**
     * 删除巡更计划信息
     *
     * @param planId 巡更计划主键
     * @return 结果
     */
    @Override
    public int deleteWatchmanPlanByPlanId(Long planId) {
        return watchmanPlanMapper.deleteWatchmanPlanByPlanId(planId);
    }

    /**
     * 巡更计划页面分页查询列表
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.work.nightPatrol.vo.NightPatrolPlanListVO>
     * @author yx
     * @date 2022/11/1 14:59
     */
    @Override
    public List<NightPatrolPlanListVO> listPage(NightPatrolPlanQueryVO query) {
        return watchmanPlanMapper.listPage(query);
    }

    /**
     * 修改巡更计划生成工单状态
     *
     * @param watchmanPlan 修改参数
     * @author yx
     * @date 2022/11/1 16:45
     */
    @Override
    public void updateNightPatrolPlanOrder(WatchmanPlan watchmanPlan) {
        watchmanPlanMapper.updateNightPatrolPlanOrder(watchmanPlan);
    }

    /**
     * 根据计划id查询计划详情
     *
     * @param planId 计划id
     * @return com.wr.remote.work.nightPatrol.vo.NightPatrolPlanDetailVO
     * @author yx
     * @date 2022/11/2 13:43
     */
    @Override
    public NightPatrolPlanDetailVO getDetailById(Long planId) {
        NightPatrolPlanDetailVO detail = watchmanPlanMapper.getDetailById(planId);
        // 查询工单生成历史
        List<NightPatrolPlanOrderHistoriesVO> nightPatrolPlanOrderHistories = watchmanMapper.listNightPatrolPlanOrderHistories(planId);
        detail.setNightPatrolPlanOrderList(nightPatrolPlanOrderHistories);
        // 查询巡更点列表
        List<NightPatrolPlanPointListVO> nightPatrolPlanPointList = pointMapper.listByRouteId(detail.getRouteId());
        detail.setNightPatrolPlanPointList(nightPatrolPlanPointList);
        return detail;
    }

    /**
     * 定时生成工单
     *
     * @author yx
     * @date 2022/12/8 13:42
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void scheduledCreateOrder() {
        // 查询所有开启的定时计划
        List<WatchmanPlan> watchmanPlans = watchmanPlanMapper.selectPlanByPlanOrder(0);
        if (CollectionUtils.isEmpty(watchmanPlans)) {
            return;
        }
        // 查询所有计划生成的工单最新一个
        List<Watchman> watchmanList = watchmanMapper.selectTimingOrderList();
        Map<Long, Watchman> planMap = watchmanList.stream().collect(Collectors.toMap(Watchman::getPlanId, t -> t));
        // 新增列表
        List<Watchman> watchmanInserts = new ArrayList<>();
        Date currentDate = new Date();
        for (WatchmanPlan watchmanPlan : watchmanPlans) {
            switch (watchmanPlan.getPlanModel()) {
                // 一日多查情况
                case "0":
                    watchmanInserts.add(getWatchman(watchmanPlan));
                    break;
                // 多日一查情况
                case "1":
                    // 校验上一次工单的生成时间和本次相差时间
                    Watchman lastWatchman = planMap.get(watchmanPlan.getPlanId());
                    if (ObjectUtils.isEmpty(lastWatchman)) {
                        watchmanInserts.add(getWatchman(watchmanPlan));
                        break;
                    }
                    // 存在工单，校验时间
                    Long differ = DateUtils.getDayCount(lastWatchman.getCreateTime(), currentDate);
                    if (differ >= watchmanPlan.getPlanNum()) {
                        watchmanInserts.add(getWatchman(watchmanPlan));
                    }
                    break;
                default:
                    break;

                // 批量新增工单
            }
        }

        if (!CollectionUtils.isEmpty(watchmanInserts)){
            watchmanMapper.insertBatch(watchmanInserts);

            List<Long> watchmanIds = watchmanInserts.stream().map(Watchman::getWatchmanId).collect(Collectors.toList());
            // 批量新增巡更点
            watchmanPointMapper.insertBatchByIds(watchmanIds);
        }
    }

    private Watchman getWatchman(WatchmanPlan watchmanPlan) {
        Distribution allot = distributionMapper.getAllotByType(watchmanPlan.getCommunityId(), "1");
        Long staffId = 0L;
        if (watchmanPlan.getAllotWay().equals("0")) {
            // 分配方式
            if (!ObjectUtils.isEmpty(allot)) {
                // 获取工单空闲人员
                Staff staff = staffService.getStaffsId(allot.getAllotId(), watchmanPlan.getCommunityId());
                if (ObjectUtils.isNotEmpty(staff)) {
                    staffId = staff.getStaffId();
                }
            }
        }
        // 计算拼接开始时、结束时间
        Date startTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getDate() + " " + watchmanPlan.getStartTime());
        Date endTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getDate() + " " + watchmanPlan.getEndTime());

        String orderNo = WorkOrderUtil.getOrderNo("XG");
        // 直接生成工单
        Watchman watchamn = new Watchman();
        watchamn.setCommunityId(watchmanPlan.getCommunityId());
        watchamn.setWatchmanNo(orderNo);
        watchamn.setWatchmanName("巡更工单");
        watchamn.setTourId(watchmanPlan.getTourId());
        watchamn.setRouteId(watchmanPlan.getRouteId());
        watchamn.setDataSource("0");
        watchamn.setPlanId(watchmanPlan.getPlanId());
        watchamn.setAllotWay(ObjectUtils.isEmpty(allot) ? "1" : allot.getAllotWay());
        watchamn.setPatrolNum(watchmanPlan.getPlanNum());
        watchamn.setGapTime(watchmanPlan.getGapTime());
        watchamn.setStartTime(startTime);
        watchamn.setEndTime(endTime);
        watchamn.setStaffId(staffId);
        watchamn.setAssistNum(0L);
        watchamn.setTransferStatus("0");
        watchamn.setAllotStatus(staffId == 0 ? "0" : "1");
        watchamn.setAcceptStatus("0");
        watchamn.setExecuteStatus("0");
        return watchamn;
    }
}
