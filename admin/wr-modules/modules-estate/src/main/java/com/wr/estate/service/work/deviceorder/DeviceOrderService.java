package com.wr.estate.service.work.deviceorder;

import com.wr.estate.entity.vo.DeviceCodeVO;
import com.wr.estate.entity.vo.DeviceOrderVO;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.deviceorder.DeviceOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备维修工单Service接口
 * 
 * @author 典韦
 * @date 2022-10-27
 */
public interface DeviceOrderService
{
    /**
     * 查询设备维修工单
     * 
     * @param orderId 设备维修工单主键
     * @return 设备维修工单
     */
    public DeviceOrder selectDeviceOrderByOrderId(Long orderId);

    /**
     * 查询设备维修工单列表
     * 
     * @param deviceOrder 设备维修工单
     * @return 设备维修工单集合
     */
    public List<DeviceOrderVO> selectDeviceOrderList(DeviceOrderVO deviceOrder);
    /**
     * 查询设备维修工单列表
     *
     * @param deviceType 设备类型
     * @return 设备维修工单集合
     */
    public List<DeviceCodeVO> selectDeviceOrderCodeList(String deviceType, Long communityId);

    /**
     * 新增设备维修工单
     * 
     * @param deviceOrder 设备维修工单
     * @return 结果
     */
    public int insertDeviceOrder(DeviceOrder deviceOrder);

    /**
     * 修改设备维修工单
     * 
     * @param deviceOrder 设备维修工单
     * @return 结果
     */
    public int updateDeviceOrder(DeviceOrder deviceOrder);

    /**
     * 批量删除设备维修工单
     * 
     * @param orderIds 需要删除的设备维修工单主键集合
     * @return 结果
     */
    public int deleteDeviceOrderByOrderIds(Long[] orderIds);

    /**
     * 删除设备维修工单信息
     * 
     * @param orderId 设备维修工单主键
     * @return 结果
     */
    public int deleteDeviceOrderByOrderId(Long orderId);
    /**
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @Description 查询工单协助人信息
     * @Param [orderType, orderId] 工单类型  工单id
     * @Date 2022/11/5 9:24
     * @Author DIANWEI
     **/
    public List<Staff> selectAssistList(String orderType,Long orderId);

}
