package com.wr.estate.service.impl.work.fitment;

import com.wr.common.core.utils.order.OrderUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.fitment.DecorateOrderMapper;
import com.wr.estate.service.manage.StaffService;
import com.wr.estate.service.work.fitment.DecorateOrderService;
import com.wr.remote.estate.customer.decorate.Decorate;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.fitment.DecorateOrder;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-11-02 15:55:26
 * @Desc: 装修评定工单
 */
@Service
public class DecorateOrderServiceImpl implements DecorateOrderService {

    @Autowired
    private DecorateOrderMapper decorateOrderMapper;

    @Autowired
    private StaffService staffService;

    /**
     * 装修评定工单列表
     *
     * @param decorateOrder
     * @return
     */
    public List<DecorateOrder> getDecorateOrderList(DecorateOrder decorateOrder) {
        return decorateOrderMapper.getDecorateOrderList(decorateOrder);
    }

    /**
     * 查询装修列表
     *
     * @param communityId
     * @return
     */
    public AjaxResult getDecorateList(Long communityId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询装修列表
        List<Decorate> decorateList = decorateOrderMapper.getDecorateList(communityId);

        ajaxResult.put(AjaxResult.DATA_TAG, decorateList);

        return ajaxResult;
    }

    /**
     * 根据装修id查询装修详细信息
     *
     * @param decorateId
     * @return
     */
    public AjaxResult getDecorateDetail(Long decorateId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询装修详细信息
        Decorate decorate = decorateOrderMapper.getDecorateDetail(decorateId);

        ajaxResult.put(AjaxResult.DATA_TAG, decorate);

        return ajaxResult;
    }

    /**
     * 装修评定工单新增
     *
     * @param decorateOrder
     * @return
     */
    @Transactional
    public int addDecorateOrder(DecorateOrder decorateOrder) {

        //查询装修详细信息
        Decorate decorate = decorateOrderMapper.getDecorateDetail(decorateOrder.getDecorateId());
        decorateOrder.setCommunityId(decorate.getCommunityId());
        if (decorate.getFinishDate() == null) {//装修中
            decorateOrder.setOrderType("0");
            decorateOrder.setStartTime(new Date());
        } else {//装修后
            decorateOrder.setOrderType("1");
            decorateOrder.setStartTime(decorate.getFinishDate());
        }

        //查询工单分配设置
        Distribution distribution = decorateOrderMapper.getDecorateOrderDistribution(decorate.getCommunityId());
        if (distribution != null) {
            decorateOrder.setAllotWay(distribution.getAllotWay());
            decorateOrder.setAssistNum(distribution.getAssistNum());
            if ("0".equals(distribution.getAllotWay())) {
                Staff staff = staffService.getStaffsId(distribution.getAllotId(), distribution.getCommunityId());
                if (staff != null) {
                    decorateOrder.setStaffId(staff.getStaffId());
                    decorateOrder.setAllotStatus("1");
                }
            }
        }

        decorateOrder.setOrderNo(OrderUtils.getOrderNo("ZXPD"));
        decorateOrder.setCreateBy(SecurityUtils.getUsername());

        //装修评定工单新增
        return decorateOrderMapper.addDecorateOrder(decorateOrder);
    }

    /**
     * 装修评定工单删除
     *
     * @param orderIds
     * @return
     */
    @Transactional
    public int deleteDecorateOrder(Long[] orderIds) {
        return decorateOrderMapper.deleteDecorateOrder(orderIds);
    }

    /**
     * 装修评定工单整改
     *
     * @param orderId
     * @return
     */
    @Transactional
    public int rectificationDecorateOrder(Long orderId) {

        //工单执行情况表插入数据
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderType("4");
        orderRecord.setOrderId(orderId);
        orderRecord.setDataType("0");
        orderRecord.setOrderResult("装修评定工单整改");
        orderRecord.setUserName(SecurityUtils.getUsername());
        decorateOrderMapper.insertOrderRecord(orderRecord);

        return decorateOrderMapper.rectificationDecorateOrder(orderId);
    }

    /**
     * 查询装修评定工单详情
     *
     * @param orderId
     * @return
     */
    public AjaxResult getDecorateOrderDetail(Long orderId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询装修评定工单详情
        DecorateOrder decorateOrder = decorateOrderMapper.getDecorateOrderDetail(orderId);

        //查询工单协助人信息
        List<Staff> staffList = decorateOrderMapper.getStaffList(orderId);

        ajaxResult.put(AjaxResult.DATA_TAG, decorateOrder);
        ajaxResult.put("staffList", staffList);

        return ajaxResult;
    }

    /**
     * 装修评定工单修改页面查询数据
     *
     * @param orderId
     * @param communityId
     * @return
     */
    public AjaxResult getUpdateDetail(Long orderId, Long communityId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询装修列表
        List<Decorate> decorateList = decorateOrderMapper.getDecorateList(communityId);

        //装修评定工单修改页面查询数据
        DecorateOrder decorateOrder = decorateOrderMapper.getUpdateDetail(orderId);

        ajaxResult.put(AjaxResult.DATA_TAG, decorateOrder);
        ajaxResult.put("decorateList", decorateList);

        return ajaxResult;
    }

    /**
     * 装修评定工单修改
     *
     * @param decorateOrder
     * @return
     */
    @Transactional
    public int updateDecorateOrder(DecorateOrder decorateOrder) {

        //查询装修详细信息
        Decorate decorate = decorateOrderMapper.getDecorateDetail(decorateOrder.getDecorateId());
        decorateOrder.setCommunityId(decorate.getCommunityId());
        if (decorate.getFinishDate() == null) {//装修中
            decorateOrder.setOrderType("0");
            decorateOrder.setStartTime(new Date());
        } else {//装修后
            decorateOrder.setOrderType("1");
            decorateOrder.setStartTime(decorate.getFinishDate());
        }

        //查询工单分配设置
        Distribution distribution = decorateOrderMapper.getDecorateOrderDistribution(decorate.getCommunityId());
        if (distribution != null) {
            decorateOrder.setAllotWay(distribution.getAllotWay());
            decorateOrder.setAssistNum(distribution.getAssistNum());
            if ("0".equals(distribution.getAllotWay())) {
                Staff staff = staffService.getStaffsId(distribution.getAllotId(), distribution.getCommunityId());
                if (staff != null) {
                    decorateOrder.setStaffId(staff.getStaffId());
                    decorateOrder.setAllotStatus("1");
                }
            }
        }

        decorateOrder.setUpdateBy(SecurityUtils.getUsername());

        //装修评定工单修改
        return decorateOrderMapper.updateDecorateOrder(decorateOrder);
    }

    /**
     * 查询分配人员信息
     *
     * @param communityId
     * @return
     */
    public AjaxResult getShareNameList(Long communityId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询分配人员信息
        List<Staff> staffList = staffService.listLeisureBusyStaffs("4", communityId);

        ajaxResult.put(AjaxResult.DATA_TAG, staffList);

        return ajaxResult;
    }

    /**
     * 工单分配
     *
     * @param decorateOrder
     * @return
     */
    @Transactional
    public int decorateOrderDistribution(DecorateOrder decorateOrder) {

        //工单执行情况表插入数据
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderType("4");
        orderRecord.setOrderId(decorateOrder.getOrderId());
        orderRecord.setDataType("0");
        orderRecord.setOrderResult("装修评定工单分配");
        orderRecord.setUserName(SecurityUtils.getUsername());
        decorateOrderMapper.insertOrderRecord(orderRecord);

        return decorateOrderMapper.decorateOrderDistribution(decorateOrder);
    }
}
