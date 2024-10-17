package com.wr.api.estate.service.application.info;

import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:04:22
 * @Desc: 业务层
 */
public interface TenantService {

    /**
     * 列表
     * @param tenantVo
     * @return
     */
    List<TenantVo> getList(TenantVo tenantVo);

    /**
     * 详情
     * @param tenantVo
     * @return
     */
    AjaxResult getDetail(TenantVo tenantVo);

    /**
     * 业主总数
     * @param communityId
     * @return
     */
    int getTenantCount(Long communityId);
}
