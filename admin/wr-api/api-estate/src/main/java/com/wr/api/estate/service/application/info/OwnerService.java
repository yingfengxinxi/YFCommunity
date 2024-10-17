package com.wr.api.estate.service.application.info;

import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:04:22
 * @Desc: 业务层
 */
public interface OwnerService {

    /**
     * 列表
     * @param ownerVo
     * @return
     */
    List<OwnerVo> getList(OwnerVo ownerVo);

    /**
     * 详情
     * @param ownerVo
     * @return
     */
    AjaxResult getDetail(OwnerVo ownerVo);

    /**
     * 业主总数
     * @param communityId
     * @return
     */
    int getOwnerCount(Long communityId);
}
