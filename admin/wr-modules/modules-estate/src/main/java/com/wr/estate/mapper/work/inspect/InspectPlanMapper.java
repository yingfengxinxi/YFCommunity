package com.wr.estate.mapper.work.inspect;

import com.wr.remote.work.inspect.InspectPlan;
import com.wr.remote.work.inspect.Timing;
import com.wr.remote.work.vo.InspectPlanVo;
import com.wr.remote.work.vo.InspectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 巡检计划
 * @Author: lvzy
 * @Date: 2022-10-27 09:35:25
 * @Desc: 巡检计划
 */
@Mapper
public interface InspectPlanMapper {

    /**
     * 新增
     * @param inspectPlan
     * @return
     */
    int insert(InspectPlanVo inspectPlan);

    /**
     * 单表详情
     * @param planId
     * @return
     */
    InspectPlan selectByPrimaryKey(Long planId);

    /**
     * 修改
     * @param inspectPlan
     * @return
     */
    Integer updateByPrimaryKey(InspectPlanVo inspectPlan);


    /**
     * 分页查询
     * @param inspectPlanVo
     * @return
     */
    List<InspectPlanVo> listPage(InspectPlanVo inspectPlanVo);

    /**
     * 改变状态 是否生成工单：0=否，1=是
     * @param inspectPlanVo
     * @return
     */
    Integer changeStatus(InspectPlanVo inspectPlanVo);

    /**
     * 详情内容查看
     * @param planId
     * @return
     */
    InspectPlanVo detail(Long planId);

    /**
     * 详情内容 -> 设备详情
     * @param planId
     * @return
     */
    List<InspectPlanVo> detailList(Long planId);

    /**
     * 详情 工单生成历史列表
     * @param planId
     * @return
     */
    List<InspectPlanVo> generateList(Long planId);

    /**
     * 新增时获取巡检类型
     * @param estateId
     * @return
     */
    List<InspectPlanVo> getTourNameList(String estateId);

    /**
     * 新增时设备列表
     * @return
     */
    List<InspectPlanVo> deviceNo(Long communityId);

    /**
     * 点击修改查详情2
     * @param planId
     * @return
     */
    InspectPlanVo updateDetail(Long planId);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    Integer remove(Long id);

    /**
     * 查找工单分配方式
     * @param communityId
     * @return
     */
    String setAllotWay(@Param("communityId") Long communityId);

    /**
     * 查找可以生成的计划
     * @return
     */
    List<Timing> selectTimingList();

    /**
     * 查找是否有关联的数据
     * @param planId
     * @return
     */
    InspectVo selectRelationPlan(Long planId);
}
