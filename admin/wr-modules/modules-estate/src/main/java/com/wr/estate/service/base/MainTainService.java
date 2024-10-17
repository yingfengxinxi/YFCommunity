package com.wr.estate.service.base;


import com.wr.remote.estate.base.BusRepairWork;

import java.util.List;

/**
 * 维修工种管理服务层
 * 
 * @author WuKong
 * @date 2022-10-11
 */
public interface MainTainService
{

    /**
     * 查询维修工种管理
     *
     * @param workId 主键
     * @return 维修信息
     */
    BusRepairWork selectBusRepairWorkId(Long workId);

    /**
     * 查询维修工种管理列表
     *
     * @param busRepairWork 维修信息
     * @return 集合
     */
    List<BusRepairWork> selectBusRepairWorkList(BusRepairWork busRepairWork);

    /**
     * 新增维修工种管理
     *
     * @param busRepairWork 维修信息
     * @return 结果
     */
    int insertBusRepairWork(BusRepairWork busRepairWork);

    /**
     * 修改维修工种管理
     *
     * @param busRepairWork 维修信息
     * @return 结果
     */
    int updateBusRepairWork(BusRepairWork busRepairWork);

    /**
     * 删除维修工种管理
     *
     * @param workId 主键
     * @return 结果
     */
    int deleteBusRepairWorkId(Long workId);

    /**
     * 批量删除维修工种管理
     *
     * @param workIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBusRepairWorkIds(Long[] workIds);
}
