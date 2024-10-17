package com.wr.estate.mapper.work.inspect;

import com.wr.remote.work.inspect.PlanDevice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 巡检计划-设备表
 *
 * @Author: lvzy
 * @Date: 2022-12-07 10:26:06
 * @Desc:巡检计划-设备表
 */
@Mapper
public interface PlanDeviceMapper {

    int insertSelective(PlanDevice planDevice);

    /**
     * 批量新增巡检设备
     *
     * @param planDevices 巡检设备信息
     */
    void insertPlanDeviceBatch(List<PlanDevice> planDevices);

    /**
     * 查找数量
     *
     * @param inspectDevice
     * @return
     */
    Integer selectNum(PlanDevice inspectDevice);

    /**
     * 删除
     *
     * @param planId
     * @return
     */
    Integer remove(Long planId);

    /**
     * 查计划的设备
     *
     * @param planId 计划ID
     * @return List
     */
    List<PlanDevice> selectListByPlanId(Long planId);
}