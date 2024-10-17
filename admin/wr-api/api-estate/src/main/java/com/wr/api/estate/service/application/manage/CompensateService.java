package com.wr.api.estate.service.application.manage;

import com.wr.api.estate.entity.vo.application.manage.CompensateVo;

/**
 * @Author: lvzy
 * @Date: 2022-11-17 16:26:32
 * @Desc: 业务层
 */
public interface CompensateService {
    /**
     * 添加赔偿信息
     * @param compensateVo
     * @return
     */
    int insertCompensate (CompensateVo compensateVo);
}
