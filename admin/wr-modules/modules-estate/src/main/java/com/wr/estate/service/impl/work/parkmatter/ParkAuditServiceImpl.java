package com.wr.estate.service.impl.work.parkmatter;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.distribution.DistributionMapper;
import com.wr.estate.mapper.work.orderLedger.ParkLedgerBillMapper;
import com.wr.estate.mapper.work.parkmatter.ParkAuditMapper;
import com.wr.estate.mapper.work.parkmatter.ParkOrderMapper;
import com.wr.estate.service.manage.StaffService;
import com.wr.estate.service.work.parkmatter.ParkAuditService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.util.WorkOrderUtil;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.parkmatter.ParkOrder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 18:31:54
 * @Desc:
 */
@Service
public class ParkAuditServiceImpl implements ParkAuditService {

    @Resource
    private ParkAuditMapper parkAuditMapper;
    @Resource
    private ParkOrderMapper parkOrderMapper;
    @Resource
    private ParkLedgerBillMapper parkLedgerBillMapper;
    @Resource
    private DistributionMapper distributionMapper;
    @Resource
    private StaffService staffService;

    @Override
    public List<LinkedHashMap<String, Object>> search(Map<String, Object> params) {
        return parkAuditMapper.search(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult changeState(Map<String, Object> params) {
        params.put("by", SecurityUtils.getUsername());
        Long communityId = Long.valueOf(String.valueOf(params.get("communityId")));
        //工单通过审核生成工单
        if ("1".equals(String.valueOf(params.get("state")))) {
            // 分配方式
            Distribution allot = distributionMapper.getAllotByType(communityId, OrderConstants.PARK_ORDER);
            if (ObjectUtils.isEmpty(allot)) {
                return AjaxResult.error("操作失败，未获取到工单分配方式！");
            }

            ParkOrder parkOrder = new ParkOrder();
            Long staffId = 0L;
            String allotStatus = "0";
            if ("0".equals(allot.getAllotWay())){
                // 获取工单空闲人员
                Staff staff = staffService.getStaffsId(allot.getAllotId(), communityId);
                if (ObjectUtils.isNotEmpty(staff)) {
                    staffId = staff.getStaffId();
                    allotStatus = "1";
                }
            }
            parkOrder.setStaffId(staffId);
            parkOrder.setAllotStatus(allotStatus);
            parkOrder.setCommunityId(communityId);
            parkOrder.setAuditId(Long.valueOf(String.valueOf(params.get("auditId"))));
            parkOrder.setOrderName(String.valueOf(params.get("orderName")));
            parkOrder.setCreateBy(SecurityUtils.getUsername());
            parkOrder.setOrderNo(WorkOrderUtil.getOrderNo("YQ"));
            parkOrder.setPlanEnds(DateUtils.getNextDate(1));
            // 分配方式
            parkOrder.setAllotWay(allot.getAllotWay());
            // 提交人备注
            String remark = String.valueOf(params.get("remark"));
            parkOrder.setRemark(remark);
            // 添加工单
            parkOrderMapper.save(parkOrder);
        }
        // 修改业主申请状态
        int num = parkAuditMapper.changeState(params);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @Override
    public LinkedHashMap<String, Object> detail(Long auditId) {
        LinkedHashMap<String, Object> detail = parkAuditMapper.detail(auditId);
        detail.put("orderImg", parkLedgerBillMapper.queryOrderImg(new HashMap<String, Object>(2) {{
            put("id", Long.valueOf(detail.get("audit_id") + ""));
            put("type", "1");
        }}));
        return detail;
    }
}
