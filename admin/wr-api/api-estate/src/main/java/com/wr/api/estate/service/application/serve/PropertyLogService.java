package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.application.serve.PropertyLog;
import com.wr.api.estate.entity.vo.application.serve.PropertyLogListVo;
import com.wr.api.estate.entity.vo.application.serve.RescueVO;
import com.wr.remote.estate.manage.contract.Owner;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 15:35:00
 * @Desc: 物业缴费接口层
 */
public interface PropertyLogService {

    /**
     * 获取物业缴费记录集合
     * @param ownerId
     * @return
     */
    List<PropertyLog> selectPropertyLogList(Long ownerId);

    /**
     * 获取业主集合
     * @param owner
     * @return
     */
    List<Owner> selectOwnerList(Owner owner);

    /**
     *
     * @param propertyLogListVo
     * @return
     */
    List<PropertyLogListVo> getPropertyList(PropertyLogListVo propertyLogListVo);

    /**
     *
     * @param logId
     * @return
     */
    PropertyLogListVo getByLogIdInfo(Long logId);
}
