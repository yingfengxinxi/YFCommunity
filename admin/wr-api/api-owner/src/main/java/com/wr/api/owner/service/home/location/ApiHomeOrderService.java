package com.wr.api.owner.service.home.location;

import com.wr.api.owner.entity.dto.ApiComplaintDTO;
import com.wr.api.owner.entity.dto.ApiHomeOrderDTO;
import com.wr.api.owner.entity.vo.location.ApiHomeOrderVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 居家维修工单表
 * @Author: lvzy
 * @Date: 2022-12-01 13:41:15
 * @Desc:居家维修工单表
 */
public interface ApiHomeOrderService {

    /**
     * 分页展示
     * @param apiHomeOrderVo
     * @return
     */
    List<ApiHomeOrderVo> listPage(ApiHomeOrderVo apiHomeOrderVo);

    /**
     * 详情展示
     * @param orderId
     * @return
     */
    AjaxResult detail(Long orderId);

    /**
     * 新增 - 居家维修
     * @param orderDTO
     * @return
     */
    AjaxResult save(ApiHomeOrderDTO orderDTO);

    /**
     * 评价 - 居家维修
     * @param complaintDTO
     * @return
     */
    AjaxResult comment(ApiComplaintDTO complaintDTO);

    /**
     * 根据物业ID查维修类型
     * @param estateId
     * @return
     */
    AjaxResult selectRepair(Long estateId);
}
