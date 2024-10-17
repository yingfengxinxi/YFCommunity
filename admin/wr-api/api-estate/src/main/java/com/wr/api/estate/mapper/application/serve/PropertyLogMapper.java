package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.application.serve.PropertyLog;
import com.wr.api.estate.entity.vo.application.serve.PropertyLogListVo;
import com.wr.api.estate.entity.vo.application.serve.RescueVO;
import com.wr.remote.estate.manage.contract.Owner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 15:36:49
 * @Desc: 物业缴费库管
 */
@Mapper
public interface PropertyLogMapper {

    /**
     * 获取物业缴费记录集合
     *
     * @param ownerId
     * @return
     */
    List<PropertyLog> selectPropertyLogList(Long ownerId);

    /**
     * 获取业主集合
     *
     * @param owner
     * @return
     */
    List<Owner> selectOwnerList(Owner owner);

    /**
     * @param propertyLogListVo
     * @return
     */
    List<PropertyLogListVo> getPropertyList(PropertyLogListVo propertyLogListVo);

    /**
     *
     * @param logId
     * @return
     */
    PropertyLogListVo getByLogIdInfo(@Param("logId") Long logId);
}
