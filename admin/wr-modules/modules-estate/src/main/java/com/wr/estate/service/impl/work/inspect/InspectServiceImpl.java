package com.wr.estate.service.impl.work.inspect;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.inspect.InspectMapper;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.service.work.inspect.InspectService;
import com.wr.estate.web.work.inspect.ExtractCode;
import com.wr.remote.domain.SysUser;
import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.vo.InspectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 巡检工单
 * @Author: lvzy
 * @Date: 2022-10-25 11:24:22
 * @Desc: 巡检工单
 */
@Service
public class InspectServiceImpl implements InspectService {

    // 巡检工单
    @Autowired
    private InspectMapper inspectMapper;

    // 工单转交记录表
    @Autowired
    private OrderRecordMapper orderRecordMapper;


    @Override
    public List<InspectVo> listPage(InspectVo inspectVo) {
        List<InspectVo> inspectVos = inspectMapper.listPage(inspectVo);
        inspectVos.forEach(arr -> {
            List<String> inspectVos1 = inspectMapper.selectDeviceList(arr.getInspectId());
            arr.setAddressName(inspectVos1.stream().sorted().collect(Collectors.joining()));
        });
        return inspectVos;
    }


    @Override
    public List<InspectVo> deviceType() {
        return inspectMapper.deviceType();
    }

    @Override
    public List<InspectVo> deviceNo(String deviceType, Long communityId) {
        return inspectMapper.deviceNo(deviceType, communityId);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult saveOrUpdate(InspectVo inspectVo) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        if (inspectVo.getInspectId() == null) {
            // 1.新增工单
            // 查分配方式
            InspectVo allotWay = inspectMapper.selectAllotWay(inspectVo.getCommunityId(), OrderConstants.INSPECT);
            if (allotWay != null) {
                inspectVo.setAllotWay(allotWay.getAllotWay());
                inspectVo.setInspectNo(new ExtractCode().sixInspectNo("XJ"));
                inspectVo.setCreateBy(userId.toString());
                inspectVo.setDataSource("1");
                // 插入数据
                inspectMapper.insertSelective(inspectVo);
                if (inspectVo.getInspectId() != null) {
                    // 2.插入设备数据
                    inspectMapper.insertDevice(inspectVo.getInspectId(), inspectVo.getDeviceType(), inspectVo.getIndexCode());
                }
                return AjaxResult.success("操作成功!");
            } else {
                return AjaxResult.error("尚未配置分配方式");
            }
        } else {
            // 修改
            inspectVo.setUpdateBy(userId.toString());
            inspectVo.setUpdateTime(new Date());
            inspectMapper.updateByPrimaryKeySelective(inspectVo);
            return AjaxResult.success();
        }
    }

    @Override
    public List<InspectVo> detailList(Long inspectId) {
        return inspectMapper.detailList(inspectId);
    }

    @Override
    public InspectVo updateDetail(Long inspectId) {
        return inspectMapper.selectDetail(inspectId);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult removes(Long[] ids) {
        for (Long id : ids) {
            inspectMapper.UpdateRemove(id);
        }
        return AjaxResult.success();
    }

    /**
     * 详情 工单执行人信息
     * @param inspectId
     * @return
     */
    @Override
    public List<InspectVo> deatilListTwo(Long inspectId) {
        String orderType = OrderConstants.INSPECT;
        return inspectMapper.deatilListTwo(inspectId, orderType);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult shareUpdate(InspectVo inspectVo) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        // 1.修改工单
        inspectVo.setAllotWay("1");
        inspectVo.setAllotStatus("1");
        inspectVo.setUpdateBy(userId.toString());
        inspectVo.setUpdateTime(new Date());
        inspectMapper.updateByPrimaryKeySelective(inspectVo);

        // 2.新增转让记录 bus_order_transfer
/*        OrderTransfer orderTransfer = new OrderTransfer();
        orderTransfer.setOrderType(OrderConstants.INSPECT);
        orderTransfer.setOrderId(inspectVo.getInspectId());
        orderTransfer.setSourceId(inspectVo.getStaffId());
        orderTransfer.setTargetId(inspectVo.getExecuteId());
        orderTransfer.setSubmitTime(date);
        orderTransfer.setAcceptType("1");
*/
        // 2.新增执行记录
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderType(OrderConstants.INSPECT);
        orderRecord.setOrderId(inspectVo.getInspectId());
        orderRecord.setOrderResult("工单分配方式: 管理员分配");
        orderRecord.setUserName(inspectVo.getUserName());

        // 判断数据是否重复
        Integer integer = orderRecordMapper.selectDataRepeat(orderRecord);
        if (integer == 0) {
            orderRecordMapper.insertSelective(orderRecord);
        } else {
            orderRecordMapper.updateByPrimaryKeySelective(orderRecord);
        }
        return AjaxResult.success();
    }

    @Override
    public List<OrderRecord> deatilListThree(Long inspectId) {

        return orderRecordMapper.deatilListThree(inspectId, OrderConstants.INSPECT);
    }

}
