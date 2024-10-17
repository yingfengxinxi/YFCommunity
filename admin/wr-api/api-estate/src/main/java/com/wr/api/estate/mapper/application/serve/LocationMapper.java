package com.wr.api.estate.mapper.application.serve;

import com.wr.remote.domain.FacilitieEquipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 11:54:49
 * @Desc: 资产定位库管层
 */
@Mapper
public interface LocationMapper {

    /**
     * 获取设备名称
     * @param indexCode 设备唯一标识
     * @param type      设备类型
     * @return
     */
    Map<String,Object> getDeviceName(@Param("indexCode") String indexCode, @Param("type") String type);

    /**
     * 更改设备地址
     * @param tableName 表名
     * @param indexCode 设备唯一标识
     * @param address 地址
     * @return
     */
    Integer updateAddress(@Param("tableName") String tableName, @Param("indexCode") String indexCode, @Param("address") String address);

    FacilitieEquipment getFacilitieById(@Param("id") String id);
}
