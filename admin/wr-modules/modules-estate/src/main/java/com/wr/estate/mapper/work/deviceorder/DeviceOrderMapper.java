package com.wr.estate.mapper.work.deviceorder;

import java.util.List;

import com.wr.estate.entity.vo.DeviceCodeVO;

import com.wr.estate.entity.vo.DeviceOrderVO;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.deviceorder.DeviceOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 设备维修工单Mapper接口
 *
 * @author 典韦
 * @date 2022-10-27
 */
public interface DeviceOrderMapper {
    /**
     * 查询设备维修工单
     *
     * @param orderId 设备维修工单主键
     * @return 设备维修工单
     */
    public DeviceOrderVO selectDeviceOrderByOrderId(Long orderId);

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
     * @param deviceType 设备维修工单 communityId 小区
     * @return 设备维修工单集合
     */
    public List<DeviceCodeVO> selectDeviceOrderCodeList(@Param("deviceType") String deviceType, @Param("communityId") Long communityId);

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
     * 删除设备维修工单
     *
     * @param orderId 设备维修工单主键
     * @return 结果
     */
    public int deleteDeviceOrderByOrderId(Long orderId);

    /**
     * 批量删除设备维修工单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceOrderByOrderIds(Long[] orderIds);

    /**
     * @return java.lang.Integer
     * @Description 查询工单是否异常 0 否 1 是
     * @Param [orderType, orderId] 工单类型  工单id
     * @Date 2022/11/4 19:57
     * @Author DIANWEI
     **/
    public Integer getAbnormalCount(@Param("orderType") String orderType, @Param("orderId") Long orderId);

    /**
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @Description 查询工单协助人信息
     * @Param [orderType, orderId] 工单类型  工单id
     * @Date 2022/11/5 9:24
     * @Author DIANWEI
     **/
    public List<Staff> selectAssistList(@Param("orderType") String orderType, @Param("orderId") Long orderId);

}
