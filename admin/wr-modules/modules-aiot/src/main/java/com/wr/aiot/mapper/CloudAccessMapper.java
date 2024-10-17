package com.wr.aiot.mapper;

import com.wr.remote.aiot.CloudAccess;
import com.wr.remote.aiot.Door;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:35:34
 * @Desc: 云+门禁数据层
 */
public interface CloudAccessMapper {
    /**
     * 查询云+门禁数据
     * @param cloudAccess 门禁信息
     * @return java.util.List<com.wr.remote.aiot.CloudAccess>
     */
    List<CloudAccess> selectCloudAccessList(CloudAccess cloudAccess);

   /**
    * 添加设备
    * @param cloudAccess 门禁数据
    * @return java.lang.Integer
    */
    Integer insertCloudAccess(CloudAccess cloudAccess);

    /**
     * 根据id查询数据
     * @param accessId 门禁id
     * @return com.wr.remote.aiot.Access
     */
    CloudAccess selectCloudAccessById(Long accessId);

    /**
     * 根据ids批量查询
     * @author yx
     * @date 2022/11/17 16:42
     * @param accessIds 设备Id
     * @return java.util.List<com.wr.remote.aiot.CloudAccess>
     */
    List<CloudAccess> selectCloudAccessByIds(List<Long> accessIds);

    /**
     * 更新门禁
     * @param cloudAccess 门禁信息
     * @return java.lang.Integer
     */
    Integer updateCloudAccess(CloudAccess cloudAccess);

    /**
     * 查询未设置设备二维码的设备id集
     * @return java.lang.Long[]
     */
    List<CloudAccess> selectIdsOfNotQrCode();

    /**
     * 批量设置门禁二维码
     * @param accessList 门禁信息
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(@Param("accessList") List<CloudAccess> accessList);

    /**
     * 删除门禁
     * @param ids 门禁id信息
     * @return java.lang.Integer
     */
    Integer removeCloudAccess(Long[] ids);

    /**
     * 查询小区下未绑定门禁的门禁点
     * @param cloudAccess 门禁
     * @return java.util.List<com.wr.remote.aiot.Door>
     */
    List<Door> selectUnbindDoors(CloudAccess cloudAccess);

}
