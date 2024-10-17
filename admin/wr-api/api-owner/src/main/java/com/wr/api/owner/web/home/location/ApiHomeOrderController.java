package com.wr.api.owner.web.home.location;

import com.wr.api.owner.entity.dto.ApiComplaintDTO;
import com.wr.api.owner.entity.dto.ApiHomeOrderDTO;
import com.wr.api.owner.entity.home.location.ApiHomeRepair;
import com.wr.api.owner.entity.vo.location.ApiHomeOrderVo;
import com.wr.api.owner.service.home.location.ApiHomeOrderService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 居家维修工单表
 * @Author: lvzy
 * @Date: 2022-12-01 13:41:01
 * @Desc:居家维修工单表
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/park")
public class ApiHomeOrderController extends BaseController {

    private final ApiHomeOrderService apiHomeOrderService;

    @GetMapping("/listPage")
    @AuthRequired
//    @EncryptResponse
    public TableDataInfo listPage(ApiHomeOrderVo apiHomeOrderVo) {
        startPage();
        return getDataTable(apiHomeOrderService.listPage(apiHomeOrderVo));
    }

    @GetMapping("/detail")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult detail(Long orderId) {
        return apiHomeOrderService.detail(orderId);
    }

    @GetMapping("/selectSave")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult selectRepair(ApiHomeRepair homeRepair) {
        return apiHomeOrderService.selectRepair(homeRepair.getEstateId());
    }

    @PostMapping("/save")
    @AuthRequired
//    @EncryptResponse
    @ApiLog(title = "新增居家维修", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody ApiHomeOrderDTO orderDTO) {
        return apiHomeOrderService.save(orderDTO);
    }

    @PostMapping("/comment")
    @AuthRequired
//    @EncryptResponse
    @ApiLog(title = "居家维修评价", businessType = BusinessType.INSERT)
    public AjaxResult comment(@RequestBody ApiComplaintDTO complaintDTO) {
        return apiHomeOrderService.comment(complaintDTO);
    }
}
