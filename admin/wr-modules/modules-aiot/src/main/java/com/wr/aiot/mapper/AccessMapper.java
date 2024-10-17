package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.wr.remote.aiot.Access;
import com.wr.remote.aiot.Camera;
import com.wr.remote.aiot.Door;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:35:34
 * @Desc: 门禁数据层
 */
public interface AccessMapper {
    /**
     * 查询门禁数据
     * @param access 门禁信息
     * @return java.util.List<com.wr.remote.aiot.Access>
     */
    List<Access> selectAccessList(Access access);

    /**
     * 修改门禁点状态
     * @param accessList 门禁数据
     * @return java.lang.Integer
     */
    Integer updateAccessOnline(@Param("accessList") List<Access> accessList);

    /**
     * 同步门禁点设备
     *
     * @param camera
     * @return
     */
    Integer insert(Access access);

    /**
     * @param access
     */
    void update(Access access);

    /**
     * @param indexCode
     * @return
     */
    Access getByIndexCodeInfo(@Param("indexCode") String indexCode);

    /**
     *
     */
    void delete();


   /**
    * 同步门禁设备
    * @param dataList 门禁数据
    * @param manufacturer 厂商
    * @param renewalBy 数据库同步人
    * @param communityId 小区id
    * @return java.lang.Integer
    */
    Integer insertOrUpdateAccess(@Param("dataList") JSONArray dataList, @Param("manufacturer") String manufacturer,
                               @Param("renewalBy") String renewalBy, @Param("communityId") Long communityId);

    /**
     * 根据id查询数据
     * @param accessId 门禁id
     * @return com.wr.remote.aiot.Access
     */
    Access selectAccessById(Long accessId);

    /**
     * 根据门禁ids查询门禁设备
     * @author yx
     * @date 2022/11/17 16:23
     * @param accessIds
     * @return com.wr.remote.aiot.Access
     */
    List<Access> selectAccessByIds(List<Long> accessIds);

    /**
     * 更新门禁
     * @param access 门禁信息
     * @return java.lang.Integer
     */
    Integer updateAccess(Access access);

    /**
     * 批量更新门禁
     * @param access 门禁信息
     * @return java.lang.Integer
     */
    Integer batchUpdateAccess(Access access);

    /**
     * 查询未设置设备二维码的设备id集
     * @return java.lang.Long[]
     */
    List<Access> selectIdsOfNotQrCode();

    /**
     * 批量设置门禁二维码
     * @param accessList 门禁信息
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(@Param("accessList") List<Access> accessList);

    /**
     * 查询小区下未绑定门禁的门禁点
     * @param access 门禁
     * @return java.util.List<com.wr.remote.aiot.Door>
     */
    List<Door> selectUnbindDoors(Access access);

}
