package com.wr.api.owner.service.home.advice;

import com.wr.api.owner.entity.home.advice.Complaint;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-15 15:49:52
 * @Desc:
 */
public interface ComplaintService {
    /**
     * 查询投诉表扬列表
     *
     * @param complaint
     * @return
     */
    List<Complaint> selectComplaintList(Complaint complaint);


    /**
     * 根据id查投诉表扬详情
     *
     * @param complaintId
     * @return
     */
    Complaint getComplaintById(Long complaintId);

    /**
     * 新增投诉表扬
     *
     * @param complaint
     * @return
     */
    Integer insertComplaint(Complaint complaint);

}
