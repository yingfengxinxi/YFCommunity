//package com.wr.estate.service.impl.work.nightPatrol;
//
//import com.wr.common.core.exception.ServiceException;
//import com.wr.common.core.utils.DateUtils;
//import com.wr.common.security.utils.SecurityUtils;
//import com.wr.estate.mapper.work.distribution.DistributionMapper;
//import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
//import com.wr.estate.mapper.work.nightPatrol.WatchmanMapper;
//import com.wr.estate.mapper.work.nightPatrol.WatchmanPointMapper;
//import com.wr.estate.mapper.work.patrol.PatrolMapper;
//import com.wr.estate.service.manage.StaffService;
//import com.wr.estate.service.work.nightPatrol.NightPatrolWorkOrderService;
//import com.wr.remote.estate.manage.Staff;
//import com.wr.remote.util.WorkOrderUtil;
//import com.wr.remote.work.distribution.Distribution;
//import com.wr.remote.work.inspect.OrderRecord;
//import com.wr.remote.work.nightPatrol.Watchman;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderDetailVO;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderListVO;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderQueryVO;
//import com.wr.remote.work.patrol.Patrol;
//import com.wr.remote.work.patrol.vo.AssistStaffListVO;
//import org.apache.commons.lang3.ObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
///**
// * 巡更工单
// *
// * @Author: 奔波儿灞
// * @Date: 2022-10-27 14:25:03
// * @Desc:
// */
//@Service
//public class NightPatrolWorkOrderServiceImpl implements NightPatrolWorkOrderService {
//
//    @Autowired
//    private WatchmanMapper watchmanMapper;
//
//    @Autowired
//    private PatrolMapper patrolMapper;
//
//    @Autowired
//    private DistributionMapper distributionMapper;
//    @Autowired
//    private OrderRecordMapper orderRecordMapper;
//
//    @Autowired
//    private StaffService staffService;
//
//    @Autowired
//    private WatchmanPointMapper watchmanPointMapper;
//
//
//    /**
//     * 巡更工单分页列表
//     *
//     * @param query 查询条件
//     * @return java.util.List<com.wr.remote.work.nightPatrol.vo.NightPatrolOrderListVO>
//     * @author yx
//     * @date 2022/11/3 17:39
//     */
//    @Override
//    public List<NightPatrolOrderListVO> listPage(NightPatrolOrderQueryVO query) {
//        return watchmanMapper.listPage(query);
//    }
//
//    /**
//     * 查询巡更工单
//     *
//     * @param watchmanId 巡更工单主键
//     * @return 巡更工单
//     */
//    @Override
//    public Watchman selectWatchmanByWatchmanId(Long watchmanId) {
//        return watchmanMapper.selectWatchmanByWatchmanId(watchmanId);
//    }
//
//    /**
//     * 查询巡更工单列表
//     *
//     * @param watchman 巡更工单
//     * @return 巡更工单
//     */
//    @Override
//    public List<Watchman> selectWatchmanList(Watchman watchman) {
//        return watchmanMapper.selectWatchmanList(watchman);
//    }
//
//    /**
//     * 新增巡更工单
//     *
//     * @param watchman 巡更工单
//     * @return 结果
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void insertWatchman(Watchman watchman) {
//        String username = SecurityUtils.getUsername();
//        watchman.setCreateBy(username);
//        watchman.setUpdateBy(username);
//        // 生成工单编号
//        String orderNo = WorkOrderUtil.getOrderNo("XG");
//        watchman.setWatchmanNo(orderNo);
//
//        // 分配方式
//        Distribution allot = distributionMapper.getAllotByType(watchman.getCommunityId(), "1");
//        if (ObjectUtils.isEmpty(allot)) {
//            throw new ServiceException("巡更无分配方式");
//        }
//        Long staffId = 0L;
//        OrderRecord orderRecord = new OrderRecord();
//        if ("0".equals(allot.getAllotWay())){
//            Staff staff = staffService.getStaffsId(allot.getAllotId(), watchman.getCommunityId());
//            if (staff != null && !ObjectUtils.isNotEmpty(staff)) {
//                staffId = staff.getStaffId();
//                orderRecord.setUserName(staff.getStaffName());
//            }
//        }
//        watchman.setStaffId(staffId);
//        watchman.setAllotWay(allot.getAllotWay());
//        watchman.setDataSource("1");
//        watchman.setAllotStatus(staffId == 0 ? "0" : "1");
//        watchmanMapper.insertWatchman(watchman);
//
//        // 批量新增巡更点
//        watchmanPointMapper.insertWatchmanPointBatch(watchman);
//
//
//        orderRecord.setOrderType("1");
//        orderRecord.setOrderId(watchman.getWatchmanId());
//        orderRecord.setRelevant("");
//        orderRecord.setDataType("0");
//        orderRecord.setOrderResult("自动分配");
//        orderRecordMapper.insertSelective(orderRecord);
//    }
//
//    /**
//     * 修改巡更工单
//     *
//     * @param watchman 巡更工单
//     * @return 结果
//     */
//    @Override
//    public int updateWatchman(Watchman watchman) {
//        watchman.setUpdateTime(DateUtils.getNowDate());
//        return watchmanMapper.updateWatchman(watchman);
//    }
//
//    /**
//     * 分配负责人
//     *
//     * @param watchman 负责人信息
//     * @author yx
//     * @date 2022/11/3 13:37
//     */
//    @Override
//    public void updateStaff(Watchman watchman) {
//        int count = watchmanMapper.updateStaff(watchman);
//        if (count == 0) {
//            throw new ServiceException("数据异常，请刷新后重试！");
//        }
//    }
//
//    /**
//     * 批量删除巡更工单
//     *
//     * @param watchmanIds 需要删除的巡更工单主键
//     * @return 结果
//     */
//    @Override
//    public int deleteWatchmanByWatchmanIds(Long[] watchmanIds) {
//        return watchmanMapper.deleteWatchmanByWatchmanIds(watchmanIds);
//    }
//
//    /**
//     * 删除巡更工单信息
//     *
//     * @param watchmanId 巡更工单主键
//     * @return 结果
//     */
//    @Override
//    public int deleteWatchmanByWatchmanId(Long watchmanId) {
//        return watchmanMapper.deleteWatchmanByWatchmanId(watchmanId);
//    }
//
//    /**
//     * 根据id查询详情
//     *
//     * @param id 工单id
//     * @return com.wr.remote.work.nightPatrol.vo.NightPatrolOrderDetailVO
//     * @author yx
//     * @date 2022/11/4 11:33
//     */
//    @Override
//    public NightPatrolOrderDetailVO getDetail(Long id) {
//        NightPatrolOrderDetailVO detail = watchmanMapper.getDetail(id);
//        String otherOrderName = "-";
//        if (StringUtils.hasLength(detail.getOrderNo())) {
//            // 查询其他工单信息
//            Patrol otherPatrol = patrolMapper.selectPatrolByPatrolNo(detail.getOrderNo());
//            otherOrderName = otherPatrol.getPatrolName();
//        }
//        detail.setOtherOrderName(otherOrderName);
//        // 查询协助人员
//        List<AssistStaffListVO> staffList = patrolMapper.listOrderAssistants("1", id, "1");
//        detail.setStaffList(staffList);
//        return detail;
//    }
//}
