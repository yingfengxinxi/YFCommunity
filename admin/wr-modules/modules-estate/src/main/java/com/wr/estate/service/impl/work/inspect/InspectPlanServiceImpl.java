//package com.wr.estate.service.impl.work.inspect;
//
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.common.security.utils.SecurityUtils;
//import com.wr.estate.mapper.work.inspect.InspectMapper;
//import com.wr.estate.mapper.work.inspect.InspectPlanMapper;
//import com.wr.estate.mapper.work.inspect.PlanDeviceMapper;
//import com.wr.estate.mapper.work.nightPatrol.WatchmanMapper;
//import com.wr.estate.mapper.work.patrol.PatrolMapper;
//import com.wr.estate.service.work.inspect.InspectPlanService;
//import com.wr.remote.domain.Patrol;
//import com.wr.remote.domain.SysUser;
//import com.wr.remote.work.inspect.Inspect;
//import com.wr.remote.work.inspect.PlanDevice;
//import com.wr.remote.work.inspect.Timing;
//import com.wr.remote.work.nightPatrol.Watchman;
//import com.wr.remote.work.vo.InspectPlanVo;
//import com.wr.remote.work.vo.InspectVo;
//import org.apache.commons.lang3.ObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * 巡检计划
// * @Author: lvzy
// * @Date: 2022-10-27 09:35:25
// * @Desc: 巡检计划
// */
//@Service
//public class InspectPlanServiceImpl implements InspectPlanService {
//
//    @Autowired
//    private InspectPlanMapper inspectMapper;
//
//    // 巡查工单
//    @Autowired
//    private PatrolMapper patrolMapper;
//
//    // 巡检工单
//    @Autowired
//    private InspectMapper insMapper;
//
//    // 巡更工单
//    @Autowired
//    private WatchmanMapper watchmanMapper;
//
//    // 设备
//    @Autowired
//    private PlanDeviceMapper planDeviceMapper;
//
//    @Override
//    public List<InspectPlanVo> listPage(InspectPlanVo inspectPlanVo) {
////        inspectPlanVo.setDeviceType();
//        return inspectMapper.listPage(inspectPlanVo);
//    }
//
//    @Override
//    @Transactional(readOnly = false)
//    public Integer changeStatus(InspectPlanVo inspectPlanVo) {
//        return inspectMapper.changeStatus(inspectPlanVo);
//    }
//
//    @Override
//    public InspectPlanVo detail(Long planId) {
//        return inspectMapper.detail(planId);
//    }
//
//    @Override
//    public List<InspectPlanVo> detailList(Long planId) {
//        return inspectMapper.detailList(planId);
//    }
//
//    @Override
//    public List<InspectPlanVo> generateList(Long planId) {
//        return inspectMapper.generateList(planId);
//    }
//
//    @Override
//    public List<InspectPlanVo> getTourNameList(Long communityId) {
//        // 根据小区ID获取 物业ID
//        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
//        return inspectMapper.getTourNameList(estateId.toString());
//    }
//
//    @Override
//    public List<InspectPlanVo> deviceNo(Long communityId) {
//        return inspectMapper.deviceNo(communityId);
//    }
//
//    @Override
//    @Transactional(readOnly = false)
//    public AjaxResult saveOrUpdate(InspectPlanVo inspectPlanVo) {
//        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
//        Long userId = sysUser.getUserId();
//        // 如果巡查模式为  0=一日多查  计算间隔时间
//        if ("0".equals(inspectPlanVo.getPlanModel())) {
//            // 转换时间计算
////            Double decimal = changeDateCount(inspectPlanVo.getStartTime(), inspectPlanVo.getEndTime(), inspectPlanVo.getPlanNum());
//            // 计算出的次数间隔时间
//            inspectPlanVo.setGapTime(BigDecimal.valueOf(0));
//        } else {
////            Double decimal = changeDateCount(inspectPlanVo.getStartTime(), inspectPlanVo.getEndTime(), inspectPlanVo.getPlanNum());
//            inspectPlanVo.setGapTime(BigDecimal.valueOf(0));
//        }
//        inspectPlanVo.setPlanOrder("1");
//        inspectPlanVo.setWatchmanType("0");
//        // 管理员分配
//        String allotWay = inspectMapper.setAllotWay(inspectPlanVo.getCommunityId());
//        if (ObjectUtils.allNull(allotWay)){
//            AjaxResult.error("未获取到分配方式，请先设置配置！");
//        }
//        inspectPlanVo.setAllotWay(allotWay);
//        List<PlanDevice> planDevices = new ArrayList<>();
//        if (inspectPlanVo.getPlanId() == null) {
//            // 新增
//            inspectPlanVo.setCreateBy(userId.toString());
//            inspectMapper.insert(inspectPlanVo);
//            // 新增设备 编号数组
//            inspectPlanVo.getTourListValueTwos().forEach(arr -> {
//                PlanDevice inspectDevice = new PlanDevice();
//                inspectDevice.setPlanId(inspectPlanVo.getPlanId());
//                inspectDevice.setDeviceType(arr.getDeviceType());
//                inspectDevice.setIndexCode(arr.getIndexCode());
//                planDevices.add(inspectDevice);
//            });
//            planDeviceMapper.insertPlanDeviceBatch(planDevices);
//        } else {
//            //修改
//            Date date = new Date();
//            inspectPlanVo.setUpdateBy(userId.toString());
//            inspectPlanVo.setUpdateTime(date);
//            inspectMapper.updateByPrimaryKey(inspectPlanVo);
//            planDeviceMapper.remove(inspectPlanVo.getPlanId());
//            inspectPlanVo.getTourListValueTwos().forEach(arr -> {
//                PlanDevice inspectDevice = new PlanDevice();
//                inspectDevice.setPlanId(inspectPlanVo.getPlanId());
//                inspectDevice.setDeviceType(arr.getDeviceType());
//                inspectDevice.setIndexCode(arr.getIndexCode());
//                planDevices.add(inspectDevice);
//            });
//            planDeviceMapper.insertPlanDeviceBatch(planDevices);
//        }
//        return AjaxResult.success();
//    }
//
//    @Override
//    public InspectPlanVo updateDetail(Long planId) {
//        return inspectMapper.updateDetail(planId);
//    }
//
//    @Override
//    @Transactional(readOnly = false)
//    public AjaxResult removes(Long[] ids) {
//        for (Long id : ids) {
//            inspectMapper.remove(id);
//        }
//        return AjaxResult.success();
//    }
//
//    /**
//     * 转换时间计算
//     * @param startTime 开始时间 HH:SS
//     * @param endTime   结束时间 HH:SS
//     * @param planNum   次数
//     */
//    private Double changeDateCount(String startTime, String endTime, Integer planNum) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        try {
//            long startTimes = dateFormat.parse(startTime).getTime();
//            long endTimes = dateFormat.parse(endTime).getTime();
//            Double aDouble = Double.valueOf(String.valueOf(endTimes - startTimes));
//            Double aDouble1 = Double.valueOf(String.valueOf(1000 * 60 * 60 * 24));
//            double v = ((aDouble / aDouble1) / 24) * 100;
//            String str = String.format("%.2f", v);
//            double four = Double.parseDouble(str);
//            return four;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    /**
//     * 查找可以生成的计划
//     * @return
//     */
//    @Override
//    public List<Timing> selectTimingList() {
//        return inspectMapper.selectTimingList();
//    }
//
//    /**
//     * 查找是否有关联的数据
//     * @param planId
//     * @return
//     */
//    @Override
//    public InspectVo selectRelationPlan(Long planId) {
//        return inspectMapper.selectRelationPlan(planId);
//    }
//
//    @Override
//    public Long insertPatrol(Patrol patrolExtracted) {
//        patrolMapper.insertPatrol(patrolExtracted);
//        return patrolExtracted.getPatrolId();
//    }
//
//    @Override
//    public Long insertWatchman(Watchman watchmanExtracted) {
//        watchmanMapper.insertWatchman(watchmanExtracted);
//        return watchmanExtracted.getWatchmanId();
//    }
//
//    @Override
//    public Long insertSelective(Inspect inspect) {
//        insMapper.insertSelective(inspect);
//        return inspect.getInspectId();
//    }
//
//    @Override
//    public int selectCrerateData(Long planId) {
//        return insMapper.selectCrerateData(planId);
//    }
//
//
//}
