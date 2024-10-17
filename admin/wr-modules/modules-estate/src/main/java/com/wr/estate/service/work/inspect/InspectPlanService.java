//package com.wr.estate.service.work.inspect;
//
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.remote.work.inspect.Inspect;
//import com.wr.remote.work.inspect.Timing;
//import com.wr.remote.work.nightPatrol.Watchman;
//import com.wr.remote.work.patrol.Patrol;
//import com.wr.remote.work.vo.InspectPlanVo;
//import com.wr.remote.work.vo.InspectVo;
//
//import java.util.List;
//
///**
// * 巡检计划
// * @Author: lvzy
// * @Date: 2022-10-27 09:35:25
// * @Desc: 巡检计划
// */
//public interface InspectPlanService {
//
//    /**
//     * 分页查询
//     * @param inspectPlanVo
//     * @return
//     */
//    List<InspectPlanVo> listPage(InspectPlanVo inspectPlanVo);
//
//    /**
//     * 改变状态 是否生成工单：0=否，1=是
//     * @param inspectPlanVo
//     * @return
//     */
//    Integer changeStatus(InspectPlanVo inspectPlanVo);
//
//    /**
//     * 详情内容查看
//     * @param planId
//     * @return
//     */
//    InspectPlanVo detail(Long planId);
//
//    /**
//     * 详情内容 -> 设备详情
//     * @param planId
//     * @return
//     */
//    List<InspectPlanVo> detailList(Long planId);
//
//    /**
//     * 详情 工单生成历史列表
//     * @param planId
//     * @return
//     */
//    List<InspectPlanVo> generateList(Long planId);
//
//    /**
//     * 新增时获取巡检类型
//     * @param communityId
//     * @return
//     */
//    List<InspectPlanVo> getTourNameList(Long communityId);
//
//    /**
//     * 新增时设备编号列表
//     * @return
//     */
//    List<InspectPlanVo> deviceNo(Long communityId);
//
//    /**
//     * 新增或修改
//     * @param inspectPlanVo
//     * @return
//     */
//    AjaxResult saveOrUpdate(InspectPlanVo inspectPlanVo);
//
//    /**
//     * 编辑展示详情
//     * @param planId
//     * @return
//     */
//    InspectPlanVo updateDetail(Long planId);
//
//    /**
//     * 删除
//     * @param ids
//     * @return
//     */
//    AjaxResult removes(Long[] ids);
//
//    /**
//     * 查找可以生成的计划
//     * @return
//     */
//    List<Timing> selectTimingList();
//
//    /**
//     * 查找是否有关联的数据
//     * @param planId
//     * @return
//     */
//    InspectVo selectRelationPlan(Long planId);
//
//    /**
//     * 新增巡查工单 并且返回 ID
//     * @param patrolExtracted
//     * @return
//     */
//    Long insertPatrol(Patrol patrolExtracted);
//
//    /**
//     * 新增巡更工单 并且返回 ID
//     * @param watchmanExtracted
//     * @return
//     */
//    Long insertWatchman(Watchman watchmanExtracted);
//
//    /**
//     * 新增巡检工单 并且返回 ID
//     * @param inspect
//     * @return
//     */
//    Long insertSelective(Inspect inspect);
//
//    /**
//     * 查找最新的数据
//     * @param planId
//     * @return
//     */
//    int selectCrerateData(Long planId);
//}
