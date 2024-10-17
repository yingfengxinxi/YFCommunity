package com.wr.api.estate.web.order;

import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.api.estate.entity.vo.order.OrderStaffVO;
import com.wr.api.estate.service.order.OrderCommonService;
import com.wr.api.estate.service.order.OrderService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-15 10:59:36
 * @Desc: 工单controller
 */
@RestController
@RequestMapping("/v2/order")
public class OrderController extends BaseController {

    @Resource(name = "orderServiceMap")
    Map<Integer, OrderService> orderServiceMap;

    @Autowired
    private OrderCommonService orderCommonService;

    /**
     * 工单列表移动端查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    @AuthRequired
    @GetMapping("/listPage")
    public TableDataInfo listPage(OrderQueryVO query) {
        if (query.getOrderType() == null || query.getStaffId() == null || query.getCommunityId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }

        startPage();
        List<OrderListVO> list = orderServiceMap.get(query.getOrderType()).listPage(query);
        return getDataTable(list);
    }

    /**
     * 查询工单协助人员、转交人列表
     *
     * @param order 工单信息
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/22 15:18
     */
    @AuthRequired
    @GetMapping("/listStaffs")
    public AjaxResult listStaffs(OrderQueryVO order) {
        if (order.getOrderType() == null || order.getCommunityId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        List<OrderStaffVO> list = orderCommonService.listLeisureStaffs(order);
        return AjaxResult.success(list);
    }

    /**
     * 查询协助人数
     * @author yx
     * @date 2022/12/23 11:55
     * @param order 工单信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/helpNumber")
    public AjaxResult helpNumber(OrderQueryVO order) {
        if (order.getOrderType() == null || order.getCommunityId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        Integer number = orderCommonService.helpNumber(order);
        return AjaxResult.success(ObjectUtils.isEmpty(number) ? 999 : number);
    }

    /**
     * 工单详情
     *
     * @param query 查询条件
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/15 11:16
     */
    @AuthRequired
    @GetMapping("/detail")
    public AjaxResult detail(OrderQueryVO query) {
        if (query.getOrderType() == null || query.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        Object obj = orderServiceMap.get(query.getOrderType()).detail(query);
        return AjaxResult.success(obj);
    }

    /**
     * 抢单
     *
     * @param query 抢单参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/15 19:12
     */
    @AuthRequired
    @GetMapping("/robOrder")
    @ApiLog(title = "工单-抢单", businessType = BusinessType.UPDATE)
    public AjaxResult robOrder(OrderQueryVO query) {
        if (query.getOrderType() == null ||
                query.getStaffId() == null ||
                StringUtils.isEmpty(query.getStaffName()) ||
                query.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        orderServiceMap.get(query.getOrderType()).robOrder(query);
        return AjaxResult.success();
    }

    /**
     * 接单
     *
     * @param query 接单参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/15 19:13
     */
    @AuthRequired
    @GetMapping("/receiveOrder")
    @ApiLog(title = "工单-接单", businessType = BusinessType.UPDATE)
    public AjaxResult receiveOrder(OrderQueryVO query) {
        if (query.getOrderType() == null ||
                query.getStaffId() == null ||
                StringUtils.isEmpty(query.getStaffName()) ||
                query.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        orderServiceMap.get(query.getOrderType()).receiveOrder(query);
        return AjaxResult.success();
    }

    /**
     * 开始执行
     *
     * @param query
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/16 14:30
     */
    @AuthRequired
    @GetMapping("/startPerform")
    @ApiLog(title = "工单-开始执行", businessType = BusinessType.UPDATE)
    public AjaxResult startPerform(OrderQueryVO query) {
        if (query.getOrderType() == null ||
                query.getStaffId() == null ||
                StringUtils.isEmpty(query.getStaffName()) ||
                query.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        orderServiceMap.get(query.getOrderType()).startPerform(query);
        return AjaxResult.success();
    }

    /**
     * 申请转让
     *
     * @param query 转让参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/16 14:31
     */
    @AuthRequired
    @GetMapping("/transfer")
    @ApiLog(title = "工单-申请转让", businessType = BusinessType.UPDATE)
    public AjaxResult transfer(OrderQueryVO query) {
        if (query.getOrderType() == null ||
                query.getStaffId() == null ||
                query.getTargetId() == null ||
                query.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }

        orderServiceMap.get(query.getOrderType()).transfer(query);
        return AjaxResult.success();
    }

    /**
     * 请求协助
     *
     * @param query 请求参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/16 14:33
     */
    @AuthRequired
    @GetMapping("/assist")
    @ApiLog(title = "工单-请求协助", businessType = BusinessType.UPDATE)
    public AjaxResult assist(OrderQueryVO query) {
        if (query.getOrderType() == null ||
                query.getStaffId() == null ||
                query.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        orderServiceMap.get(query.getOrderType()).assist(query);
        return AjaxResult.success();
    }

    /**
     * 签到
     *
     * @param order 签到参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/16 14:33
     */
    @AuthRequired
    @PostMapping("/signIn")
    @ApiLog(title = "工单-签到", businessType = BusinessType.UPDATE)
    public AjaxResult signIn(@RequestBody OrderQueryVO order) {
        if (order.getOrderType() == null ||
                order.getStaffId() == null ||
                order.getId() == null ||
                order.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        orderServiceMap.get(order.getOrderType()).signIn(order);
        return AjaxResult.success();
    }

    /**
     * 完成工单
     *
     * @param order 工单信息
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/16 14:34
     */
    @AuthRequired
    @PostMapping("/finish")
    @ApiLog(title = "工单-完成工单", businessType = BusinessType.UPDATE)
    public AjaxResult finish(@RequestBody OrderQueryVO order) {
        if (order.getOrderType() == null || order.getStaffId() == null || order.getOrderId() == null) {
            throw new ServiceException("参数异常！", 1000);
        }
        orderServiceMap.get(order.getOrderType()).finish(order);
        return AjaxResult.success();
    }


    /**
     * 查询工单权限
     *
     * @param workPost    工作职位
     * @param communityId 社区id
     * @return {@code AjaxResult}
     */
    @AuthRequired
    @GetMapping("/permission")
    public AjaxResult selectOrderPermission(String workPost, Long communityId){
        List<Integer> orderPermission = orderCommonService.selectOrderPermission(workPost, communityId);
        return AjaxResult.success(orderPermission);
    }

}
