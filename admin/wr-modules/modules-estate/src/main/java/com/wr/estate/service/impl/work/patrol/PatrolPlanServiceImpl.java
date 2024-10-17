//package com.wr.estate.service.impl.work.patrol;
//
//import com.wr.common.core.exception.ServiceException;
//import com.wr.common.core.utils.DateUtils;
//import com.wr.common.security.utils.SecurityUtils;
//import com.wr.estate.mapper.work.distribution.DistributionMapper;
//import com.wr.estate.mapper.work.patrol.PatrolMapper;
//import com.wr.estate.mapper.work.patrol.PatrolPlanMapper;
//import com.wr.estate.service.manage.StaffService;
//import com.wr.estate.service.work.patrol.PatrolPlanService;
//import com.wr.remote.estate.manage.Staff;
//import com.wr.remote.util.WorkOrderUtil;
//import com.wr.remote.work.distribution.Distribution;
//import com.wr.remote.work.patrol.Patrol;
//import com.wr.remote.work.patrol.PatrolPlan;
//import com.wr.remote.work.patrol.vo.PatrolOrderListVO;
//import com.wr.remote.work.patrol.vo.PatrolPlanDetailVO;
//import com.wr.remote.work.patrol.vo.PatrolPlanListVO;
//import com.wr.remote.work.patrol.vo.PatrolPlanQueryVO;
//import org.apache.commons.lang3.ObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * 巡查计划
// *
// * @Author: 奔波儿灞
// * @Date: 2022-10-27 14:19:53
// * @Desc:
// */
//@Service
//public class PatrolPlanServiceImpl implements PatrolPlanService {
//    @Autowired
//    private PatrolPlanMapper patrolPlanMapper;
//
//    @Autowired
//    private PatrolMapper patrolMapper;
//
//    @Autowired
//    private DistributionMapper distributionMapper;
//
//    @Autowired
//    private StaffService staffService;
//
//    /**
//     * 查询巡查计划
//     *
//     * @param planId 巡查计划主键
//     * @return 巡查计划
//     */
//    @Override
//    public PatrolPlan selectPatrolPlanByPlanId(Long planId) {
//        return patrolPlanMapper.selectPatrolPlanByPlanId(planId);
//    }
//
//    /**
//     * 查询巡查计划列表
//     *
//     * @param patrolPlan 巡查计划
//     * @return 巡查计划
//     */
//    @Override
//    public List<PatrolPlan> selectPatrolPlanList(PatrolPlan patrolPlan) {
//        return patrolPlanMapper.selectPatrolPlanList(patrolPlan);
//    }
//
//    /**
//     * 新增巡查计划
//     *
//     * @param patrolPlan 巡查计划
//     * @return 结果
//     */
//    @Override
//    public int insertPatrolPlan(PatrolPlan patrolPlan) {
//        String username = SecurityUtils.getUsername();
//        patrolPlan.setCreateBy(username);
//        patrolPlan.setUpdateBy(username);
//        patrolPlan.setPlanOrder("0");
//        int count = patrolPlanMapper.countByName(patrolPlan);
//        if (count > 0) {
//            throw new ServiceException("计划名称已存在！");
//        }
//
//        return patrolPlanMapper.insertPatrolPlan(patrolPlan);
//    }
//
//    /**
//     * 修改巡查计划
//     *
//     * @param patrolPlan 巡查计划
//     * @return 结果
//     */
//    @Override
//    public int updatePatrolPlan(PatrolPlan patrolPlan) {
//        patrolPlan.setUpdateTime(DateUtils.getNowDate());
//        int count = patrolPlanMapper.countByName(patrolPlan);
//        if (count > 0) {
//            throw new ServiceException("计划名称已存在！");
//        }
//
//        return patrolPlanMapper.updatePatrolPlan(patrolPlan);
//    }
//
//    /**
//     * 批量删除巡查计划
//     *
//     * @param planIds 需要删除的巡查计划主键
//     * @return 结果
//     */
//    @Override
//    public int deletePatrolPlanByPlanIds(Long[] planIds) {
//        return patrolPlanMapper.deletePatrolPlanByPlanIds(planIds);
//    }
//
//    /**
//     * 删除巡查计划信息
//     *
//     * @param planId 巡查计划主键
//     * @return 结果
//     */
//    @Override
//    public int deletePatrolPlanByPlanId(Long planId) {
//        return patrolPlanMapper.deletePatrolPlanByPlanId(planId);
//    }
//
//    /**
//     * 修改巡查计划生成工单状态
//     *
//     * @param patrolPlan 工单修改信息
//     * @author yx
//     * @date 2022/10/31 16:30
//     */
//    @Override
//    public void updatePatrolPlanOrder(PatrolPlan patrolPlan) {
//        patrolPlanMapper.updatePatrolPlanOrder(patrolPlan);
//    }
//
//    /**
//     * 巡查计划分页列表
//     *
//     * @param query 查询条件
//     * @return java.util.List<com.wr.remote.work.patrol.vo.PatrolPlanListVO>
//     * @author yx
//     * @date 2022/10/31 13:42
//     */
//    @Override
//    public List<PatrolPlanListVO> listPage(PatrolPlanQueryVO query) {
//        return patrolPlanMapper.listPage(query);
//    }
//
//    /**
//     * 详情
//     *
//     * @param planId 计划id
//     * @return com.wr.remote.work.patrol.vo.PatrolPlanDetailVO
//     * @author yx
//     * @date 2022/11/1 11:20
//     */
//    @Override
//    public PatrolPlanDetailVO detail(Long planId) {
//        PatrolPlanDetailVO detail = patrolPlanMapper.getDetailById(planId);
//        List<PatrolOrderListVO> patrolPlanOrderHistories = patrolMapper.listPatrolPlanOrderHistories(planId);
//        detail.setPatrolOrderList(patrolPlanOrderHistories);
//        return detail;
//    }
//
//    /**
//     * 定时生成工单
//     *
//     * @author yx
//     * @date 2022/12/8 11:08
//     */
//    @Override
//    public void scheduledCreateOrder() {
//        // 查询所有开启的定时计划
//        List<PatrolPlan> patrolPlans = patrolPlanMapper.selectPlanByPlanOrder(0);
//        if (CollectionUtils.isEmpty(patrolPlans)) {
//            return;
//        }
//        // 查询所有计划生成的工单最新一个
//        List<Patrol> patrols = patrolMapper.selectTimingOrderList();
//        Map<Long, Patrol> planMap = patrols.stream().collect(Collectors.toMap(Patrol::getPlanId, t -> t));
//        // 新增列表
//        List<Patrol> patrolInserts = new ArrayList<>();
//        Date currentDate = new Date();
//        for (PatrolPlan patrolPlan : patrolPlans) {
//
//
//            switch (patrolPlan.getPlanModel()) {
//                // 一日多查情况
//                case "0":
//                    patrolInserts.add(getPatrol(patrolPlan));
//                    break;
//                // 多日一查情况
//                case "1":
//                    // 校验上一次工单的生成时间和本次相差时间
//                    Patrol lastPatrol = planMap.get(patrolPlan.getPlanId());
//                    if (ObjectUtils.isEmpty(lastPatrol)) {
//                        patrolInserts.add(getPatrol(patrolPlan));
//                        break;
//                    }
//                    // 存在工单，校验时间
//                    Long differ = DateUtils.getDayCount(lastPatrol.getCreateTime(), currentDate);
//                    if (differ >= patrolPlan.getPlanNum()){
//                        patrolInserts.add(getPatrol(patrolPlan));
//                    }
//                    break;
//                default:
//                    break;
//
//                // 批量新增工单
//            }
//        }
//        if (!CollectionUtils.isEmpty(patrolInserts)){
//            patrolMapper.insertBatch(patrolInserts);
//        }
//    }
//
//    private Patrol getPatrol(PatrolPlan patrolPlan) {
//        Distribution allot = distributionMapper.getAllotByType(patrolPlan.getCommunityId(), "0");
//        Long staffId = 0L;
//        if (patrolPlan.getAllotWay().equals("0")) {
//            // 分配方式
//            if (!ObjectUtils.isEmpty(allot)) {
//                // 获取工单空闲人员
//                Staff staff = staffService.getStaffsId(allot.getAllotId(), patrolPlan.getCommunityId());
//                if (ObjectUtils.isNotEmpty(staff)) {
//                    staffId = staff.getStaffId();
//                }
//            }
//        }
//        // 计算拼接开始时、结束时间
//        Date startTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getDate() + " " + patrolPlan.getStartTime());
//        Date endTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getDate() + " " + patrolPlan.getEndTime());
//
//        String orderNo = WorkOrderUtil.getOrderNo("XC");
//        // 直接生成工单
//        Patrol patrol = new Patrol();
//        patrol.setCommunityId(patrolPlan.getCommunityId());
//        patrol.setPatrolNo(orderNo);
//        patrol.setPatrolName("巡查工单");
//        patrol.setTourId(patrolPlan.getTourId());
//        patrol.setPointId(patrolPlan.getPointId());
//        patrol.setDataSource("0");
//        patrol.setPlanId(patrolPlan.getPlanId());
//        patrol.setAllotWay(ObjectUtils.isEmpty(allot) ? "1" : allot.getAllotWay());
//        patrol.setPatrolNum(patrolPlan.getPlanNum());
//        patrol.setGapTime(patrolPlan.getGapTime());
//        patrol.setStartTime(startTime);
//        patrol.setEndTime(endTime);
//        patrol.setStaffId(staffId);
//        patrol.setAssistNum(0L);
//        patrol.setTransferStatus("0");
//        patrol.setAllotStatus(staffId == 0 ? "0" : "1");
//        patrol.setAcceptStatus("0");
//        patrol.setExecuteStatus("0");
//        return patrol;
//    }
//
//}
