//package com.wr.estate.service.work.nightPatrol;
//
//import com.wr.remote.work.nightPatrol.Watchman;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderDetailVO;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderListVO;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderQueryVO;
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
//public interface NightPatrolWorkOrderService {
//
//    /**
//     * 巡更工单分页列表
//     *
//     * @param query 查询条件
//     * @return java.util.List<com.wr.remote.work.nightPatrol.vo.NightPatrolOrderListVO>
//     * @author yx
//     * @date 2022/11/3 17:39
//     */
//    List<NightPatrolOrderListVO> listPage(NightPatrolOrderQueryVO query);
//
//    /**
//     * 查询巡更工单
//     *
//     * @param watchmanId 巡更工单主键
//     * @return 巡更工单
//     */
//    Watchman selectWatchmanByWatchmanId(Long watchmanId);
//
//    /**
//     * 查询巡更工单列表
//     *
//     * @param watchman 巡更工单
//     * @return 巡更工单集合
//     */
//    List<Watchman> selectWatchmanList(Watchman watchman);
//
//    /**
//     * 新增巡更工单
//     *
//     * @param watchman 巡更工单
//     * @return 结果
//     */
//    void insertWatchman (Watchman watchman);
//
//    /**
//     * 修改巡更工单
//     *
//     * @param watchman 巡更工单
//     * @return 结果
//     */
//    int updateWatchman(Watchman watchman);
//
//    /**
//     * 分配负责人
//     *
//     * @param watchman 负责人信息
//     * @author yx
//     * @date 2022/11/3 13:37
//     */
//    void updateStaff(Watchman watchman);
//
//    /**
//     * 批量删除巡更工单
//     *
//     * @param watchmanIds 需要删除的巡更工单主键集合
//     * @return 结果
//     */
//    int deleteWatchmanByWatchmanIds(Long[] watchmanIds);
//
//    /**
//     * 删除巡更工单信息
//     *
//     * @param watchmanId 巡更工单主键
//     * @return 结果
//     */
//    int deleteWatchmanByWatchmanId(Long watchmanId);
//
//    /**
//     * 根据id查询详情
//     *
//     * @param id 工单id
//     * @return com.wr.remote.work.nightPatrol.vo.NightPatrolOrderDetailVO
//     * @author yx
//     * @date 2022/11/4 11:33
//     */
//    NightPatrolOrderDetailVO getDetail(Long id);
//
//}
