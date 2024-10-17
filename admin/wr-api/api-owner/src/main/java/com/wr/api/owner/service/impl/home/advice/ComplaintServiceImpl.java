package com.wr.api.owner.service.impl.home.advice;

import com.wr.api.owner.entity.home.advice.Complaint;
import com.wr.api.owner.mapper.home.advice.ComplaintMapper;
import com.wr.api.owner.service.home.advice.ComplaintService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-15 15:50:43
 * @Desc: 投诉表扬业务层
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;

    @Override
    public List<Complaint> selectComplaintList(Complaint complaint) {
        return complaintMapper.selectComplaintList(complaint);
    }

    @Override
    public Complaint getComplaintById(Long complaintId) {
        return complaintMapper.getComplaintById(complaintId);
    }

    @Override
    public Integer insertComplaint(Complaint complaint) {
        return complaintMapper.insertComplaint(complaint);
    }
}
