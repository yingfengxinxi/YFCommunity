//package com.wr.estate.web.work.inspect;
//
//import com.wr.common.core.web.controller.BaseController;
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.common.core.web.page.TableDataInfo;
//import com.wr.common.log.annotation.Log;
//import com.wr.common.log.enums.BusinessType;
//import com.wr.common.security.annotation.Logical;
//import com.wr.common.security.annotation.RequiresPermissions;
//import com.wr.estate.service.work.inspect.OrderAuditService;
//import com.wr.remote.estate.order.vo.OrderAuditDetailVO;
//import com.wr.remote.estate.order.vo.OrderAuditListVO;
//import com.wr.remote.estate.order.vo.OrderAuditQueryVO;
//import com.wr.remote.work.inspect.OrderAudit;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 工单协助审核
// *
// * @Author: lvzy
// * @Date: 2022-10-31 20:03:30
// * @Desc: 工单协助审核
// */
//@RestController
//@RequestMapping("/order/audit")
//public class OrderAuditController extends BaseController {
//
//    @Autowired
//    private OrderAuditService service;
//
//    @GetMapping("/listPage")
//    public TableDataInfo listPage(OrderAuditQueryVO query) {
//        startPage();
//        List<OrderAuditListVO> listPage = service.listPage(query);
//        return getDataTable(listPage);
//    }
//
//    /**
//     * 驳回
//     *
//     * @param audit
//     * @return
//     */
//    @RequiresPermissions(value = {"patrol:audit:reject", "inspect:audit:reject", "nightPatrol:audit:reject",
//            "decorate:audit:reject", "work:orderaudit:reject", "work:deviceOrder:reject",
//            "work:disasterAudit:reject", "work:parkaudit:reject", "work:sanitation:cleanersAudit:reject"},
//            logical = Logical.OR)
//    @Log(title = "员工工单驳回", businessType = BusinessType.UPDATE)
//    @PostMapping("/reject")
//    public AjaxResult reject(@RequestBody OrderAudit audit) {
//        service.reject(audit);
//        return success();
//    }
//
//    /**
//     * 同意
//     *
//     * @param audit
//     * @return
//     */
//    @RequiresPermissions(value = {"patrol:audit:adopt", "nightPatrol:audit:adopt", "inspect:audit:adopt",
//            "work:sanitation:cleanersAudit:adopt", "decorate:audit:adopt", "work:orderaudit:adopt",
//            "work:deviceOrder:adopt", "work:disasterAudit:adopt", "work:parkaudit:adopt"},
//            logical = Logical.OR)
//    @Log(title = "员工工单通过", businessType = BusinessType.UPDATE)
//    @PostMapping("/adopt")
//    public AjaxResult adopt(@RequestBody OrderAudit audit) {
//        service.adopt(audit);
//        return success();
//    }
//
//    /**
//     * 查询详情
//     *
//     * @param query
//     * @return com.wr.common.core.web.domain.AjaxResult
//     * @author yx
//     * @date 2022/11/4 19:24
//     */
//    @GetMapping("/getDetail")
//    public AjaxResult getDetail(OrderAuditQueryVO query) {
//        OrderAuditDetailVO detail = service.getDetail(query);
//        return AjaxResult.success(detail);
//    }
//
//
//}
