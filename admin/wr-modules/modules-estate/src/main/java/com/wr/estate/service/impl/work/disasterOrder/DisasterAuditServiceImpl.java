package com.wr.estate.service.impl.work.disasterOrder;

import cn.hutool.core.date.DateUtil;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.disasterOrder.DisasterAuditMapper;
import com.wr.estate.mapper.work.disasterOrder.DisasterOrderMapper;
import com.wr.estate.mapper.work.disasterOrder.OrderRecordBusMapper;
import com.wr.estate.mapper.work.distribution.DistributionMapper;
import com.wr.estate.mapper.work.inspect.OrderAuditMapper;
import com.wr.estate.service.manage.StaffService;
import com.wr.estate.service.work.disasterOrder.DisasterAuditService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.order.vo.OrderAuditDetailVO;
import com.wr.remote.estate.order.vo.OrderAuditQueryVO;
import com.wr.remote.util.WorkOrderUtil;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.inspect.OrderAudit;
import com.wr.remote.work.vo.BusDisasterAuditVo;
import com.wr.remote.work.vo.BusDisasterOrderVo;
import com.wr.remote.work.vo.OrderRecordVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: xuShu
 * @Date: 2022-10-28 14:00:28
 * @Desc:
 */
@Service
public class DisasterAuditServiceImpl implements DisasterAuditService {

    @Autowired
    private DisasterAuditMapper disasterAuditMapper;

    @Autowired
    private OrderAuditMapper auditMapper;

    @Autowired
    private OrderRecordBusMapper orderRecordBusMapper;

    @Autowired
    private DisasterOrderMapper disasterOrderMapper;

    @Autowired
    private DistributionMapper distributionMapper;
    @Autowired
    private StaffService staffService;

    /**
     * 分页查询灾害报事审核
     *
     * @param busDisaster
     * @return
     */
    @Override
    public List<BusDisasterAuditVo> disasterAuditService(BusDisasterAuditVo busDisaster) {
        busDisaster.setOrderType(OrderConstants.DISASTER_ORDER);
        List<BusDisasterAuditVo> list = disasterAuditMapper.getList(busDisaster);
        list.forEach(e -> {
            List<String> imageList = disasterAuditMapper.getImageList(e.getAuditId());
            e.setImageList(imageList);
        });
        return list;
    }

    /**
     * 审核通过
     *
     * @param busDisasterAuditVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult upOwnerAuditStatus(BusDisasterAuditVo busDisasterAuditVo) {
        Long communityId = busDisasterAuditVo.getCommunityId();
        // 获取分配方式
        Distribution allot = distributionMapper.getAllotByType(communityId, OrderConstants.DISASTER_ORDER);
        if (ObjectUtils.isEmpty(allot)) {
            return AjaxResult.error("操作失败，未获取到工单分配方式！");
        }
        BusDisasterOrderVo busDisasterOrderVo = new BusDisasterOrderVo();
        Long staffId = 0L;
        String allotStatus = "0";
        if (ObjectUtils.isNotEmpty(allot) && "0".equals(allot.getAllotWay())) {
            // 获取工单空闲人员
            Staff staff = staffService.getStaffsId(allot.getAllotId(), communityId);
            if (ObjectUtils.isNotEmpty(staff)) {
                staffId = staff.getStaffId();
                allotStatus = "1";
            }
        }

        // 添加灾害工单

        // 生成灾害工单编号
        busDisasterOrderVo.setOrderNo(WorkOrderUtil.getOrderNo("ZH"));
        busDisasterOrderVo.setOrderName(busDisasterAuditVo.getAuditName());
        busDisasterOrderVo.setStaffId(staffId);
        busDisasterOrderVo.setAllotStatus(allotStatus);
        busDisasterOrderVo.setCommunityId(communityId);
        busDisasterOrderVo.setAuditId(busDisasterAuditVo.getAuditId());
        busDisasterOrderVo.setPlanStart(DateUtils.getNowDate());
        busDisasterOrderVo.setPlanEnd(DateUtils.getNextDate(1));
        // 分配方式
        busDisasterOrderVo.setAllotWay(allot.getAllotWay());
        busDisasterOrderVo.setExecuteStatus(busDisasterAuditVo.getExecuteStatus());
        int num = disasterOrderMapper.insertOrder(busDisasterOrderVo);
        // 修改灾害申请状态
        disasterAuditMapper.updateById(busDisasterAuditVo.getAuditId(), SecurityUtils.getLoginUser().getSysUser().getUserName());
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult upStaffAuditStatus(BusDisasterAuditVo busDisasterAuditVo) {
        // 查询审核信息
        OrderAudit info = auditMapper.getInfo(busDisasterAuditVo.getAuditId());
        if (StringUtils.isNull(info)) {
            return AjaxResult.error("操作失败，未获取到工单审核信息！");
        }
        OrderAuditQueryVO query = new OrderAuditQueryVO();
        query.setOrderType(info.getOrderType());
        query.setAuditId(info.getAuditId());
        // 查询审核额外信息
        //OrderAuditDetailVO detail = orderAuditService.getDetail(query);

        OrderRecordVo orderRecordVo = new OrderRecordVo();
        String username = SecurityUtils.getUsername();
        // 工单执行情况记录
        orderRecordVo.setOrderType(OrderConstants.DISASTER_ORDER);
        orderRecordVo.setOrderId(busDisasterAuditVo.getOrderId());
        orderRecordVo.setDataType("1");
        orderRecordVo.setOrderResult("0".equals(busDisasterAuditVo.getAuditType()) ? "工单转交" : "工单协助");
        orderRecordVo.setUserName(username);
        orderRecordVo.setCreateTime(new Date());
        // 添加工单执行情况记录
        orderRecordBusMapper.insertBusRecord(orderRecordVo);
        // 灾害申请审核
        int num = auditMapper.updateById(busDisasterAuditVo.getAuditId());
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 驳回
     */
    @Override
    public Integer rejectStatus(BusDisasterAuditVo busDisasterAuditVo) {
        busDisasterAuditVo.setAuditBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
        //当通过的status为0是业主驳回
        if (busDisasterAuditVo.getStatus().equals("0")) {
            //调用工单执行表添加方法
            insertRecord(busDisasterAuditVo);
            return disasterAuditMapper.updateByReject(busDisasterAuditVo);
        } else {
            insertRecord(busDisasterAuditVo);
            return auditMapper.updateByIdEmp(busDisasterAuditVo);
        }

    }

    /**
     * 分页查询灾害报事审核员工申请
     *
     * @param busDisasterAuditVo
     * @return
     */
    @Override
    public List<BusDisasterAuditVo> listPage(BusDisasterAuditVo busDisasterAuditVo) {
        return disasterAuditMapper.getEmpList(busDisasterAuditVo);
    }

    /**
     * 查询工单异常
     *
     * @param id
     * @return
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryError(Long id) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("type", 8);
        map.put("id", id);
        return disasterAuditMapper.queryError(map);
    }

    /**
     * 驳回方法向工单执行表添加数据
     * @param busDisasterAuditVo
     * @return: void
     * @date: 2023/1/10 9:42
     * @author: SJiang
     **/
    public void insertRecord(BusDisasterAuditVo busDisasterAuditVo) {
        OrderRecordVo orderRecordVo = new OrderRecordVo();
        String username = SecurityUtils.getUsername();
        //通过并向工单执行情况表添加一条数据
        orderRecordVo.setOrderType(OrderConstants.DISASTER_ORDER);
        orderRecordVo.setOrderId(busDisasterAuditVo.getOrderId());
        orderRecordVo.setDataType("1");
        orderRecordVo.setOrderResult("灾害报事工单申请: 驳回");
        orderRecordVo.setUserName(username);
        orderRecordVo.setCreateTime(new Date());
        //添加
        orderRecordBusMapper.insertBusRecord(orderRecordVo);
    }
}
