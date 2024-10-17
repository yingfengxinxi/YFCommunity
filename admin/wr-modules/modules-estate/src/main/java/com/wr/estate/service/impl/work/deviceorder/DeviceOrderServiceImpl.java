package com.wr.estate.service.impl.work.deviceorder;


import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.DeviceCodeVO;
import com.wr.estate.entity.vo.DeviceOrderVO;
import com.wr.estate.mapper.work.deviceorder.DeviceOrderMapper;
import com.wr.estate.mapper.work.distribution.DistributionMapper;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.service.manage.StaffService;
import com.wr.estate.service.work.deviceorder.DeviceOrderService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.util.WorkOrderUtil;
import com.wr.remote.work.deviceorder.DeviceOrder;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.inspect.OrderRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 设备维修工单Service业务层处理
 *
 * @author 典韦
 * @date 2022-10-27
 */
@Service
public class DeviceOrderServiceImpl implements DeviceOrderService {
    @Autowired
    private DeviceOrderMapper deviceOrderMapper;
    @Autowired
    private DistributionMapper distributionMapper;
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    @Autowired
    private StaffService staffService;

    /**
     * 查询设备维修工单
     *
     * @param orderId 设备维修工单主键
     * @return 设备维修工单
     */
    @Override
    public DeviceOrder selectDeviceOrderByOrderId(Long orderId) {
        DeviceOrderVO deviceOrder = deviceOrderMapper.selectDeviceOrderByOrderId(orderId);
        //是否异常 0 否 1 是
        Integer abnormalCount = deviceOrderMapper.getAbnormalCount("6", deviceOrder.getOrderId());
        deviceOrder.setAbnormal(abnormalCount.toString());
        return deviceOrder;
    }

    /**
     * 查询设备维修工单列表
     *
     * @param deviceOrder 设备维修工单
     * @return 设备维修工单
     */
    @Override
    public List<DeviceOrderVO> selectDeviceOrderList(DeviceOrderVO deviceOrder) {
        //列表查询
        List<DeviceOrderVO> deviceOrderList = deviceOrderMapper.selectDeviceOrderList(deviceOrder);
        return deviceOrderList;
    }

    @Override
    public List<DeviceCodeVO> selectDeviceOrderCodeList(String deviceType, Long communityId) {
        return deviceOrderMapper.selectDeviceOrderCodeList(deviceType, communityId);
    }

    /**
     * 新增设备维修工单
     *
     * @param deviceOrder 设备维修工单
     * @return 结果
     */
    @Override
    public int insertDeviceOrder(DeviceOrder deviceOrder) {

        //生成设备维修工单编号
        deviceOrder.setOrderNo(WorkOrderUtil.getOrderNo("SB"));
        //根据小区 和 工单类型 查询工单分配 详情信息
        Distribution distribution = distributionMapper.getAllotByType(deviceOrder.getCommunityId(), "6");
        if (StringUtils.isNull(distribution)) {
            return -1;
        }
        deviceOrder.setAllotWay(distribution.getAllotWay());
        deviceOrder.setCreateBy(SecurityUtils.getUsername());

        //新增设备维修工单
        int num = deviceOrderMapper.insertDeviceOrder(deviceOrder);
        if (num == 0) {
            return 0;
        }
        DeviceOrder deviceOrderAllot = new DeviceOrder();
        //自动分配时 执行表添加一条数据
        if ("0".equals(deviceOrder.getAllotWay())) {
            //查询工单空闲人员
            Staff staff = staffService.getStaffsId(distribution.getAllotId(), deviceOrder.getCommunityId());
            if (StringUtils.isNull(staff)) {
                deviceOrder.setStaffId(0L);
                deviceOrderAllot.setAllotStatus("0");
            } else {
                deviceOrder.setStaffId(staff.getStaffId());
                deviceOrder.setAllotStatus("1");
                deviceOrderAllot.setAllotStatus("1");
            }
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrderType("6");
            orderRecord.setOrderId(deviceOrder.getOrderId());
            orderRecord.setUserName(SecurityUtils.getUsername());
            orderRecord.setRelevant("");
            orderRecord.setDataType("0");
            orderRecord.setOrderResult("自动分配");
            orderRecordMapper.insertSelective(orderRecord);
            // 修改分配状态
            deviceOrderAllot.setOrderId(deviceOrder.getOrderId());
            deviceOrderAllot.setStaffId(deviceOrder.getStaffId());
            int numAllot = deviceOrderMapper.updateDeviceOrder(deviceOrderAllot);
            if (numAllot == 0) {
                return -2;
            }
        }

        return num;
    }

    @Test
    public void test() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 1、普通的时间转换
        String string = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        System.out.println(string);
        Random random = new Random();

        int i = random.nextInt(2);
        System.out.println(i);
    }

    /**
     * 修改设备维修工单
     *
     * @param deviceOrder 设备维修工单
     * @return 结果
     */
    @Override
    public int updateDeviceOrder(DeviceOrder deviceOrder) {
        deviceOrder.setAllotStatus("1");
        return deviceOrderMapper.updateDeviceOrder(deviceOrder);
    }

    /**
     * 批量删除设备维修工单
     *
     * @param orderIds 需要删除的设备维修工单主键
     * @return 结果
     */
    @Override
    public int deleteDeviceOrderByOrderIds(Long[] orderIds) {
        return deviceOrderMapper.deleteDeviceOrderByOrderIds(orderIds);
    }

    /**
     * 删除设备维修工单信息
     *
     * @param orderId 设备维修工单主键
     * @return 结果
     */
    @Override
    public int deleteDeviceOrderByOrderId(Long orderId) {
        return deviceOrderMapper.deleteDeviceOrderByOrderId(orderId);
    }

    /**
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @Description 查询工单协助人信息
     * @Param [orderType, orderId] 工单类型  工单id
     * @Date 2022/11/5 9:24
     * @Author DIANWEI
     **/
    @Override
    public List<Staff> selectAssistList(String orderType, Long orderId) {
        return deviceOrderMapper.selectAssistList(orderType, orderId);
    }
}
