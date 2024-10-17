package com.wr.api.estate.mapper.application.info;

import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.api.estate.entity.vo.application.serve.RentContractVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:04:22
 * @Desc: 数据层
 */
public interface TenantMapper {
    /**
     * 列表
     *
     * @param tenantVo
     * @return
     */
    List<TenantVo> getList(TenantVo tenantVo);

    /**
     * 表单详情
     *
     * @param tenantVo
     * @return
     */
    TenantVo getDetail(TenantVo tenantVo);

    RentContractVo getRentContractDetail(@Param("tenantId") Long tenantId);

    /**
     * 资产信息
     * @param tenantVo
     * @return
     */
    //TenantVo getCar(TenantVo tenantVo);

    /**
     * 车辆信息
     *
     * @param tenantVo
     * @return
     */
    List<TenantVo> getCar(TenantVo tenantVo);

    /**
     * 车位信息
     *
     * @param tenantVo
     * @return
     */
    List<TenantVo> getCarport(TenantVo tenantVo);

    /**
     * 业主总数
     *
     * @param communityId
     * @return
     */
    int getTenantCount(Long communityId);

    /**
     * 房产信息
     *
     * @param tenantVo
     * @return
     */
    TenantVo getHouse(TenantVo tenantVo);

}
