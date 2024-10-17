package com.wr.api.owner.mapper.home.location;

import com.wr.api.owner.entity.home.location.ApiComplaint;
import com.wr.api.owner.entity.home.location.RecordOwnerVo;
import com.wr.api.owner.entity.vo.location.*;

import com.wr.api.owner.entity.home.location.ParkAudit;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-12-01 10:51:20
 * @Desc:
 */
public interface ReportForRepairMapper {
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
     *  新增工单图片 集
     * @param images
     * @param type
     * @param tableId
     * @return
     */
    int saveOrderImage(@Param("images") String[] images,@Param("type") String type,@Param("tableId") Long tableId);

    /**
     * 工单图片 集
     * @param type
     * @param tableId
     * @return
     */
    List<String> orderImages(@Param("type") String type,@Param("tableId") Long tableId);
    /**
     * 园区报事工单
     * @param ownerId
     * @return
     */
    List<ParkOrderVO> searchParkByOwnerId(Long ownerId);
    /**
     * 查询灾害报事
     * @param disasterVo
     * @return
     */
    List<DisasterVo> getList(DisasterVo disasterVo);

    /**
     * 添加灾害报事
     * @param disasterVo
     * @return
     */
    int addList(DisasterVo disasterVo);

    /**
     * 添加灾害报事申请表
     * @param disasterVo
     * @return
     */
    int addAudit(DisasterVo disasterVo);

    /**
     * 添加工单评价
     * @param apiComplaint
     * @return
     */
    int addEnquiry(ApiComplaint apiComplaint);

    /**
     * 园区工单详情
     * @param auditId
     * @return
     */
    ParkAuditVO detailParkByAuditId(Long auditId);

    ParkAuditVO detailParkOrderByAuditId(Long auditId);

    /**
     * 查询图片
     * @param i
     * @return
     */
    String getImageS(@Param("imageType") int i,@Param("orderId") Long orderId);

    /**
     * 查询审核详情
     * @param disasterId
     * @return
     */
    List<DisasterVo> getDisInfoList(Long disasterId);

    /**
     * 查询工单图片
     * @param orderId
     * @return
     */
    List<String> getOrderImages(@Param("orderId") Long orderId);

    /**
     * 查询所有的协助人id
     * @param id
     * @return
     */
    List<Long> getStaffIds(Long id);

    /**
     * 查询协助人信息
     * @param staffId
     * @return
     */
    List<StaffInfoVo> getAllStaffInfo(List<Long> staffId);


    /**
     * 查询工单评价信息
     * @param orderId
     * @return
     */
    List<ApiComplaint> getComplaint(Long orderId);

    /**
     * 查询评价图片
     * @param orderId
     * @return
     */
    String[] getImageUrlsOrder(@Param("orderId") Long orderId);

    /**
     * 改变 园区工单评价
     * @param params
     * @return
     */
    int appraisePark(Map<String,Object> params);

    /**
     * 修改 灾害工单评价
     * @param apiComplaint
     * @return
     */
    int update(ApiComplaint apiComplaint);
    /**
     * 修改 园区工单评价
     * @param apiComplaint
     * @return
     */
    int updatepark(ApiComplaint apiComplaint);

    /**
     * 工单执行情况表插入数据
     *
     * @param orderRecord
     */
    int insertOrderRecord(RecordOwnerVo orderRecord);

    /**
     * 查询灾害类型
     * @param disasterVo
     * @return
     */
    List<DisasterVo> getDisName(DisasterVo disasterVo);

    /**
     * 查询详情
     * @param orderId
     * @return
     */
    DisasterVo getAll(Long orderId);


    /**
     * 查询园区报事工单
     * @param auditId
     * @return
     */
    MaintainVo selectListParkAudit(Long auditId);


    /**
     * 查询园区报事工单
     * @param
     * @return
     */
    List<MaintainVo> selectListParkAuditPage(@Param("ownerId") Integer ownerId,@Param("communityId") Integer communityId);

    /**
     * 查询灾害报事工单
     * @param auditId
     * @return
     */
    MaintainVo selectListdisasterAudit(Long auditId);
}
