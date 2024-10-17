package com.wr.estate.web.work.disasterOrder;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.work.disasterOrder.DisasterOrderService;
import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.vo.BusDisasterOrderVo;
import com.wr.remote.work.vo.OrderRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-01 15:48:16
 * @Desc:
 */
@RestController
@RequestMapping("/disasterOrder")
public class DisasterOrderController extends BaseController {
    @Autowired
    private DisasterOrderService disasterOrderService;

    /**
     * 查询灾害报事工单列表
     * @param busDisasterOrderVo
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo getDisasterOrderList(BusDisasterOrderVo busDisasterOrderVo){
        startPage();
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        busDisasterOrderVo.setEstateId(estateId);
        List<BusDisasterOrderVo> disasterOrderList = disasterOrderService.getDisasterOrderList(busDisasterOrderVo);
        return getDataTable(disasterOrderList);
    }

    /**
     * 灾害报事执行人详情
     * @param busDisasterOrderVo
     * @return
     */
    @GetMapping("/listInfo")
    @Log(title = "灾害报事执行人详情", businessType = BusinessType.OTHER)
    public AjaxResult tableInfo(BusDisasterOrderVo busDisasterOrderVo){
        List<BusDisasterOrderVo> disasterOrderList = disasterOrderService.getInfoList(busDisasterOrderVo);
        return AjaxResult.success(disasterOrderList);
    }

    /**
     * 修改灾害报事工单
     * @param busDisasterOrderVo
     * @return
     */
    @RequiresPermissions("work:disasterOrder:updateOrAdd")
    @Log(title = "修改灾害报事工单", businessType = BusinessType.UPDATE)
    @PostMapping("/updateOrAdd")
    public AjaxResult updateAndAddOrder(@RequestBody BusDisasterOrderVo busDisasterOrderVo){
        return AjaxResult.success(disasterOrderService.updateOrder(busDisasterOrderVo));
    }

    /**
     * 获取执行人
     * @param communityId
     * @return
     */
    @GetMapping("/nameList/{communityId}")
    public AjaxResult getNameList(@PathVariable Long communityId){
        return AjaxResult.success(disasterOrderService.getNameList(communityId));
    }



    /**
     * 确定分配
     * @param busDisasterOrderVo
     * @return
     */
    @RequiresPermissions("work:disasterOrder:shareUpdate")
    @PostMapping("/shareUpdate")
    @Log(title = "确定分配", businessType = BusinessType.UPDATE)
    public AjaxResult shareUpdate(@RequestBody BusDisasterOrderVo busDisasterOrderVo) {
        return disasterOrderService.shareUpdate(busDisasterOrderVo);
    }

    /**
     * 根据id删除灾害工单信息
     * @param orderId
     * @return
     */
    @RequiresPermissions("work:disasterOrder:deleteByOrder/{orderId}")
    @Log(title = "根据id删除灾害工单信息",businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteByOrder/{orderId}")
    public AjaxResult deleteByOrder(@PathVariable Long orderId){
        int row= disasterOrderService.deleteByOrder(orderId);
        return AjaxResult.success(row);
    }

    /**
     * 获取执行情况详情
     * @param orderId
     * @return
     */
    @GetMapping("/deatilRecord/{orderId}")
    public AjaxResult deatilListThree(@PathVariable Long orderId) {
        List<OrderRecordVo> busDisasterOrderVos = disasterOrderService.deatilListThree(orderId);
        return AjaxResult.success(busDisasterOrderVos);
    }
    /**
     * 获取执行情况详情 通用
     *
     * @param orderId
     * @return
     */
    @GetMapping("/detailRecordGeneral")
    public AjaxResult detailListThreeGeneral(@RequestParam("orderType") String orderType, @RequestParam("orderId") Long orderId) {
        List<OrderRecord> busDisasterOrderVos = disasterOrderService.detailListThreeGeneral(orderId, orderType);
        return AjaxResult.success(busDisasterOrderVos);
    }
}
