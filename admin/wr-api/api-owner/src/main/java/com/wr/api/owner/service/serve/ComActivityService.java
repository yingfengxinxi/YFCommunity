package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.dto.ComAcDTO;
import com.wr.api.owner.entity.vo.serve.ComAcVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-16 09:49:56
 * @Desc:
 */
public interface ComActivityService {
    /**
     * 查询本小区的活动
     * @param ownerId
     * @return
     */
    List<ComAcDTO> searchByCommunityId(Long ownerId, Long communityId);

    /**
     * 查询我参与的活动详情
     * @param comAcVo
     * @return
     */
    ComAcVo getComActivityInfo(ComAcVo comAcVo);

    /**
     * 签到
     * @param
     * @return
     */
    AjaxResult updateSignTime(ComAcVo comAcVo);
}
