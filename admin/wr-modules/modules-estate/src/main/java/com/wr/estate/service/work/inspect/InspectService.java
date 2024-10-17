package com.wr.estate.service.work.inspect;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.vo.InspectVo;

import java.util.List;

/**
 * 巡检工单
 * @Author: lvzy
 * @Date: 2022-10-25 11:24:01
 * @Desc: 巡检工单
 */
public interface InspectService {

    /**
     * 分页查询
     * @param inspectVo
     * @return
     */
    List<InspectVo> listPage(InspectVo inspectVo);

    /**
     * 获取设备类型
     * @return
     */
    List<InspectVo> deviceType();

    /**
     * 获取设备编号
     * @param deviceType
     * @return
     */
    List<InspectVo> deviceNo(String deviceType, Long communityId);

    /**
     * 新增或修改
     * @param inspectVo
     * @return
     */
    AjaxResult saveOrUpdate(InspectVo inspectVo);

    /**
     * 设备详情
     * @param inspectId
     * @return
     */
    List<InspectVo> detailList(Long inspectId);

    /**
     * 修改-详情展示
     * @param inspectId
     * @return
     */
    InspectVo updateDetail(Long inspectId);

    /**
     * 删除
     * @param ids
     * @return
     */
    AjaxResult removes(Long[] ids);

    /**
     * 详情 工单执行人信息
     * @param inspectId
     * @return
     */
    List<InspectVo> deatilListTwo(Long inspectId);

    /**
     * 确定分配
     * @param inspectVo
     * @return
     */
    AjaxResult shareUpdate(InspectVo inspectVo);

    /**
     * 详情下 查找执行详情
     * @param inspectId
     * @return
     */
    List<OrderRecord> deatilListThree(Long inspectId);

}
