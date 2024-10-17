package com.wr.api.owner.service.impl.home.location;

import com.wr.api.owner.entity.dto.ApiComplaintDTO;
import com.wr.api.owner.entity.home.location.ApiComplaint;
import com.wr.api.owner.entity.home.location.ApiOrderImage;
import com.wr.api.owner.entity.home.location.RecordOwnerVo;
import com.wr.api.owner.entity.vo.location.*;
import com.wr.api.owner.entity.home.location.ParkAudit;
import com.wr.api.owner.mapper.home.location.ApiOrderImageMapper;
import com.wr.api.owner.mapper.home.location.ReportForRepairMapper;
import com.wr.api.owner.service.home.location.ReportForRepairService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: lvzy
 * @Date: 2022-12-01 11:14:01
 * @Desc:
 */
@Service
public class ReportForRepairServImpl implements ReportForRepairService {

    @Resource
    private ReportForRepairMapper reportForRepairMapper;

    @Resource
    private ApiOrderImageMapper orderImageMapper;

    @Override
    public List<LinkedHashMap<String, Object>> homeTypeList(Long estateId) {
        return reportForRepairMapper.homeTypeList(estateId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> disasterTypeList(Long estateId) {
        return reportForRepairMapper.disasterTypeList(estateId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> parkTypeList(Long estateId) {
        return reportForRepairMapper.parkTypeList(estateId);
    }

    @Transactional(rollbackFor = Exception.class)
    /**
     * 查询灾害报事
     * @param disasterVo
     * @return
     */
    @Override
    public List<DisasterVo> getDisastrousList(DisasterVo disasterVo) {
        //查询待审核
        List<DisasterVo> list = reportForRepairMapper.getList(disasterVo);
        //查询全部灾害报事申请的图片
        list.forEach(x -> x.setImageUrl(reportForRepairMapper.getImageS(2, x.getAuditId())));
        return list;
    }

    /**
     * 添加灾害报事
     *
     * @param disasterVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult addAisastrous(DisasterVo disasterVo) {
        List<ApiOrderImage> imageList = new ArrayList<>();
        //添加灾害报事申请表
        //获取新增的主键
        disasterVo.setCreateTime(new Date());
        disasterVo.setAuditName(disasterVo.getOrderName());
        //添加灾害报事申请表
       Integer num = reportForRepairMapper.addAudit(disasterVo);
        //添加图片表
        disasterVo.setImageType("2");
        disasterVo.setTableId(disasterVo.getAuditId());
        disasterVo.getImageList().forEach(arr -> {
            ApiOrderImage apiOrderImage = new ApiOrderImage();
            apiOrderImage.setImageUrl(arr);
            apiOrderImage.setTableId(disasterVo.getAuditId());
            apiOrderImage.setImageType("2");
            imageList.add(apiOrderImage);
        });
        // 批量新增图片
        orderImageMapper.insertList(imageList);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();

    }

    /**
     * 添加工单评价
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult addOrderEnquiry(ApiComplaintDTO apiComplaint) {
        //  类型：0=业主表扬，1=业主投诉
        apiComplaint.setOrderType("3");
        apiComplaint.setCreateTime(new Date());
        int row = reportForRepairMapper.addEnquiry(apiComplaint);
        if (row > 0) {
            //修改工单评价状态
            apiComplaint.setUpdateBy(apiComplaint.getAccountName());
            int update = reportForRepairMapper.update(apiComplaint);
            //添加工单执行表
            RecordOwnerVo orderRecord = new RecordOwnerVo();
            orderRecord.setOrderType("8");
            orderRecord.setOrderId(apiComplaint.getOrderId());
            orderRecord.setDataType("0");
            orderRecord.setOrderResult("灾害申请评价");
            orderRecord.setUserName(apiComplaint.getAccountName());
            int record = reportForRepairMapper.insertOrderRecord(orderRecord);
            if (update > 0 && record > 0) {
                return AjaxResult.success("添加工单评价成功");
            }
        }
        return AjaxResult.error("添加工单评价失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int savePark(ParkAudit parkAudit) {

        int i = reportForRepairMapper.savePark(parkAudit);
        if (i > 0) {
            /**
             * 工单图片
             */
            if (StringUtils.isNotNull(parkAudit.getImages()) && parkAudit.getImages().length > 0) {
                reportForRepairMapper.saveOrderImage(parkAudit.getImages(), "1", parkAudit.getAuditId());
            }
            return 1;
        } else {
            return 0;
        }


    }

    @Override
    public List<ParkOrderVO> searchParkByOwnerId(Long ownerId) {
        return reportForRepairMapper.searchParkByOwnerId(ownerId);
    }

    @Override
    public ParkAuditVO detailParkByAuditId(Long auditId) {
        ParkAuditVO vo = reportForRepairMapper.detailParkByAuditId(auditId);
        vo.setImages(reportForRepairMapper.orderImages("1", auditId));
        ParkAuditVO parkVO = reportForRepairMapper.detailParkOrderByAuditId(auditId);
        if (StringUtils.isNotNull(parkVO)) {
            vo.setOrderNo(parkVO.getOrderNo());
            vo.setMasterName(parkVO.getMasterName());
            vo.setMasterPhone(parkVO.getMasterPhone());
            vo.setPlanEnd(parkVO.getPlanEnd());
            vo.setPlanStart(parkVO.getPlanStart());
            vo.setExecuteName(parkVO.getExecuteName());
            vo.setOrderId(parkVO.getOrderId());
            vo.setExecutePhone(parkVO.getExecutePhone());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int appraisePark(Map<String, Object> params) {

        ApiComplaint complaint = new ApiComplaint();
        complaint.setCommunityId(Long.valueOf(params.get("communityId") + ""));
        complaint.setAccountId(Long.valueOf(params.get("accountId") + ""));
        complaint.setComplaintType(params.get("complaintType") + "");
        complaint.setOrderType("2");
        complaint.setOrderId(Long.valueOf(params.get("orderId") + ""));
        complaint.setOrderNo(params.get("orderNo") + "");
        complaint.setComplaintUrl(params.get("complaintUrl") + "");
        complaint.setRemark(params.get("remark") + "");
        complaint.setCreateTime(new Date(System.currentTimeMillis()));
        reportForRepairMapper.addEnquiry(complaint);
        RecordOwnerVo orderRecord = new RecordOwnerVo();
        orderRecord.setOrderType("7");
        orderRecord.setOrderId(complaint.getOrderId());
        orderRecord.setDataType("0");
        orderRecord.setOrderResult("工单评价");
        orderRecord.setUserName(params.get("by") + "");
        reportForRepairMapper.insertOrderRecord(orderRecord);
        return reportForRepairMapper.appraisePark(params);
    }

    /**
     * 根据工单状态查询详情
     *
     * @param
     * @return
     */
    @Override
    public DisasterVo aisastrouInfo(Long orderId) {
        DisasterVo all = reportForRepairMapper.getAll(orderId);
        //查询评价信息
        List<ApiComplaint> complaint = reportForRepairMapper.getComplaint(orderId);
        //查询评价图片
        String[] images = reportForRepairMapper.getImageUrlsOrder(orderId);
        all.setImageUrls(images);
        all.setApiComplaint(complaint);
        return all;
    }

    /**
     * 查询灾害类型
     *
     * @param disasterVo
     * @return
     */
    @Override
    public AjaxResult getDisaster(DisasterVo disasterVo) {
        List<DisasterVo> list = reportForRepairMapper.getDisName(disasterVo);
        return AjaxResult.success(list);
    }


    /**
     * 园区报事集合
     *
     * @return
     */
    @Override
    public List<MaintainVo> selectListParkAuditPage(Integer ownerId,Integer communityId) {
        return reportForRepairMapper.selectListParkAuditPage(ownerId,communityId);
    }

    /**
     * 园区报事单个详细
     *
     * @return
     */
    @Override
    public MaintainVo selectListParkAuditOne(Long auditId) {
        return reportForRepairMapper.selectListParkAudit(auditId);
    }

    @Override
    public MaintainVo selectListdisasterAudit(Long auditId) {
        return reportForRepairMapper.selectListdisasterAudit(auditId);
    }

    /**
     * 添加园区工单评价
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult addOrderpark(ApiComplaintDTO apiComplaint) {
        //  类型：0=业主表扬，1=业主投诉
        apiComplaint.setOrderType("2");
        apiComplaint.setCreateTime(new Date());
        int row = reportForRepairMapper.addEnquiry(apiComplaint);
        if (row > 0) {
            //修改工单评价状态
            apiComplaint.setUpdateBy(apiComplaint.getAccountName());
            int update = reportForRepairMapper.updatepark(apiComplaint);
            //添加工单执行表
            RecordOwnerVo orderRecord = new RecordOwnerVo();
            orderRecord.setOrderType("7");
            orderRecord.setOrderId(apiComplaint.getOrderId());
            orderRecord.setDataType("0");
            orderRecord.setOrderResult("园区申请评价");
            orderRecord.setUserName(apiComplaint.getAccountName());
            int record = reportForRepairMapper.insertOrderRecord(orderRecord);
            if (update > 0 && record > 0) {
                return AjaxResult.success("添加工单评价成功");
            }
        }
        return AjaxResult.error("添加工单评价失败");
    }
}
