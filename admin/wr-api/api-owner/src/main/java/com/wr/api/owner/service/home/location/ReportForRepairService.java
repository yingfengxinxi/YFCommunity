package com.wr.api.owner.service.home.location;

import com.wr.api.owner.entity.dto.ApiComplaintDTO;
import com.wr.api.owner.entity.vo.location.DisasterVo;
import com.wr.api.owner.entity.vo.location.MaintainVo;
import com.wr.api.owner.entity.vo.location.ParkAuditVO;
import com.wr.common.core.web.domain.AjaxResult;

import com.wr.api.owner.entity.home.location.ParkAudit;
import com.wr.api.owner.entity.vo.location.ParkOrderVO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-12-01 11:07:48
 * @Desc:
 */
public interface ReportForRepairService {

    /**
     *  查询居家维修事故类型
     * @param estateId
     * @return
     */
    List<LinkedHashMap<String,Object>> homeTypeList(Long estateId);

    /**
     * 查询灾害报事类型
     * @param estateId
     * @return
     */
    List<LinkedHashMap<String,Object>> disasterTypeList(Long estateId);


    /**
     * 查询园区报事类型
     * @param  estateId
     * @return
     */
    List<LinkedHashMap<String,Object>> parkTypeList(Long estateId);

    /**
     * 新增园区报事工单申请
     * @param parkAudit
     * @return
     */
    int savePark(ParkAudit parkAudit);

    /**
     * 查询灾害报事
     * @param disasterVo
     * @return
     */
    List<DisasterVo> getDisastrousList(DisasterVo disasterVo);

    /**
     * 添加灾害报事
     * @param disasterVo
     * @return
     */
    AjaxResult addAisastrous(DisasterVo disasterVo);

    /**
     * 添加工单评价
     * @param apiComplaint
     * @return
     */
    AjaxResult addOrderEnquiry(ApiComplaintDTO apiComplaint);

    /**
     * 添加园区工单评价
     * @param apiComplaint
     * @return
     */
    AjaxResult addOrderpark(ApiComplaintDTO apiComplaint);

    /**
     * 园区报事工单
     * @param ownerId
     * @return
     */
    List<ParkOrderVO> searchParkByOwnerId(Long ownerId);

    /**
     * 园区工单详情
     * @param auditId
     * @return
     */
    ParkAuditVO detailParkByAuditId(Long auditId);

    /**
     * 改变 园区工单评价
     * @param params
     * @return
     */
    int appraisePark(Map<String,Object> params);

    /**
     * 根据工单状态查询详情
     * @param orderId
     * @return
     */
    DisasterVo aisastrouInfo(Long orderId);

    /**
     *
     * @param disasterVo
     * @return
     */
    AjaxResult getDisaster(DisasterVo disasterVo);


    /**
     * 园区报事集合接口
     * @param
     * @return
     */
    List<MaintainVo> selectListParkAuditPage(Integer ownerId,Integer communityId);


    /**
     * 园区报事单个详细
     * @param auditId
     * @return
     */
    MaintainVo selectListParkAuditOne(Long auditId);


    /**
     * 灾害报时
     * @param auditId
     * @return
     */
    MaintainVo selectListdisasterAudit(Long auditId);
}
