package com.wr.estate.mapper.center.personnelfilemanage;

import com.wr.estate.entity.dto.TenantFileDto;
import com.wr.remote.estate.manage.contract.Tenant;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-17 15:59:40
 * @Desc: 租客档案
 */
public interface TenantFileMapper {
    /**
     * 小区下的租客
     *
     * @param tenantFileDto
     * @return
     */
    List<TenantFileDto> queryByCommunityId(TenantFileDto tenantFileDto);

    /**
     * 租客 合同
     *
     * @param
     * @return
     */
    List<LinkedHashMap<String, Object>> queryContract(Long id);

    /**
     * 查询 租客
     */
    List<TenantFileDto> queryById(Long[] ids);

    /**
     * 修改租客
     *
     * @param tenantFileDto
     * @return
     */
    int update(TenantFileDto tenantFileDto);

    /**
     * @param tenantFileDto
     * @return
     */
    int save(TenantFileDto tenantFileDto);

    /**
     * 解约租客
     *
     * @param ids
     * @return
     */
    int deleted(Long[] ids);

    /**
     * 修改 account_type 为 2
     *
     * @param ids
     * @return
     */
    int updateAccountState(Long[] ids);

    /**
     * 房间人数-1
     *
     * @param tenantIds 租客id
     * @return {@link Integer}
     * @ver v1.0.0
     */
    Integer reduceLiveNumOne(Long[] tenantIds);

    /**
     * 续约租客
     *
     * @param contractStart
     * @param contractEnd
     * @param by
     * @param ids
     * @return
     */
    int updateContract(
            @Param("contractStart") Date contractStart,
            @Param("contractEnd") Date contractEnd,
            @Param("by") String by,
            @Param("ids") List<String> ids
    );

    /**
     * 根据id查询租客
     *
     * @param tenantId
     * @return {@link Tenant}
     * @ver v1.0.0
     */
    Tenant selectTenantByTenantId(Long tenantId);


    /**
     * @return
     */
    List<Long> getContractEndRoomIds();

    /**
     * @param tenantId
     * @return
     */
    TenantFileDto getById(@Param("tenantId") Long tenantId);
}
