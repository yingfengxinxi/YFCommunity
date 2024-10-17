package com.wr.estate.service.center.personnelfilemanage;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.dto.AuthDto;
import com.wr.estate.entity.dto.TenantFileDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-17 16:11:46
 * @Desc:
 */
public interface TenantFileService {
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
     * 续约租客
     *
     * @param contractStart
     * @param contractEnd
     * @param ids
     * @return
     */
    int updateContract(Date contractStart,
                       Date contractEnd,
                       String ids);

    /**
     * 人员授权查询数据
     *
     * @param communityId 小区id
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    AjaxResult selectDeviceList(Long communityId);

    /**
     * 人员授权
     *
     * @param authDto
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    AjaxResult authTenant(AuthDto authDto);

    /**
     * @return
     */
    List<Long> getContractEndRoomIds();

    /**
     * @param tenantId
     * @return
     */
    TenantFileDto getById(Long tenantId);

}
