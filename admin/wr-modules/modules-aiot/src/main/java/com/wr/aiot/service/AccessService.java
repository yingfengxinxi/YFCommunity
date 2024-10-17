package com.wr.aiot.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Access;
import com.wr.remote.aiot.Door;

import java.util.Date;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 18:51:43
 * @Desc: 门禁信息业务处理
 */
public interface AccessService {
    /**
     * 查询门禁数据
     *
     * @param access 门禁信息
     * @return java.util.List<com.wr.remote.aiot.Access>
     */
    List<Access> selectAccessList(Access access);

    /**
     * 同步门禁设备
     *
     * @param communityId 小区id
     * @return java.lang.Integer
     */
    AjaxResult syncAccessData(Long communityId) throws Exception;

    /**
     * 更新设备状态
     *
     * @param access 门禁设备
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult checkOnline(Access access) throws Exception;

    /**
     * 根据id查询数据
     *
     * @param accessId 门禁id
     * @return com.wr.remote.aiot.Access
     */
    Access selectAccessById(Long accessId);

    /**
     * 更新门禁
     *
     * @param access 门禁信息
     * @return java.lang.Integer
     */
    Integer updateAccess(Access access);

    /**
     * 批量更新门禁
     *
     * @param access 门禁信息
     * @return java.lang.Integer
     */
    Integer batchUpdateAccess(Access access);

    /**
     * 批量设置门禁设备二维码
     *
     * @param access 门禁id
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(Access access);

    /**
     * 合法卡比对事件订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult subscribeLegalCard(Long communityId);


    /**
     * 合法卡比对事件取消订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult unsubscribeLegalCard(Long communityId);

    /**
     * 查询小区下未绑定门禁的门禁点
     *
     * @param access 门禁
     * @return java.util.List<com.wr.remote.aiot.Door>
     */
    List<Door> selectUnbindDoors(Access access);

}
