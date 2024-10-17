package com.wr.estate.web.work.disasterOrder;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.disasterOrder.DisasterAuditService;
import com.wr.estate.service.work.disasterOrder.OrderRecordService;
import com.wr.remote.work.vo.BusDisasterAuditVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-10-28 13:27:39
 * @Desc: 灾害报事审核
 */
@RestController
@RequestMapping("/disasterAudit")
public class DisasterAuditController extends BaseController {
    @Autowired
    private DisasterAuditService disasterAuditService;

    /**
     * 分页查询灾害报事审核业主申请
     *
     * @param busDisaster
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(BusDisasterAuditVo busDisaster) {
        startPage();
        List<BusDisasterAuditVo> list = disasterAuditService.disasterAuditService(busDisaster);
        return getDataTable(list);
    }

    /**
     * 分页查询灾害报事审核员工申请
     *
     * @param busDisasterAuditVo
     * @return
     */
    @GetMapping("/listPage")
    public TableDataInfo listPage(BusDisasterAuditVo busDisasterAuditVo) {
        startPage();
        busDisasterAuditVo.setOrderType(OrderConstants.DISASTER_ORDER);
        List<BusDisasterAuditVo> listPage = disasterAuditService.listPage(busDisasterAuditVo);
        return getDataTable(listPage);
    }


    /**
     * 审核通过
     *
     * @param busDisasterAuditVo
     * @return
     */
    @RequiresPermissions("work:disasterAudit:pass")
    @PostMapping("/pass")
    public AjaxResult passDisasterAudit(@RequestBody BusDisasterAuditVo busDisasterAuditVo) {
        // 审核类型：0=业主，1=员工
        String status = busDisasterAuditVo.getStatus();
        AjaxResult ajaxResult = AjaxResult.error();
        if ("0".equals(status)) {
            ajaxResult = disasterAuditService.upOwnerAuditStatus(busDisasterAuditVo);
        } else if ("1".equals(status)) {
            ajaxResult = disasterAuditService.upStaffAuditStatus(busDisasterAuditVo);
        }
        return ajaxResult;
    }

    /**
     * 驳回
     *
     * @param busDisasterAuditVo
     * @return
     */
    @RequiresPermissions("work:disasterAudit:reject")
    @PostMapping("/reject")
    public AjaxResult rejectDisasterAudit(@RequestBody BusDisasterAuditVo busDisasterAuditVo) {

        //根据id修改bus_disaster_audit的audit_status状态值
        Integer row = disasterAuditService.rejectStatus(busDisasterAuditVo);
        if (row > 0) {
            return toAjax(row);
        } else {
            return AjaxResult.error("错误");
        }
    }

    /**
     * 查询工单异常
     *
     * @param id
     * @return
     */
    @GetMapping("/queryError")
    public AjaxResult queryError(Long id) {

        return AjaxResult.success(disasterAuditService.queryError(id));
    }


}
