package com.wr.estate.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wr.estate.mapper.ComplanintMapper;
import com.wr.estate.service.ComplanintService;
import com.wr.remote.estate.custom.vo.ComplaintDeatilVO;
import com.wr.remote.estate.custom.vo.ComplanintPraiseVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComplanintServiceImpl implements ComplanintService {

    @Resource
    private ComplanintMapper complanintMapper;

    @Override
    public List<ComplanintPraiseVO> getAllComplanint(ComplanintPraiseVO complanintPraiseVO) {
        return complanintMapper.getAllComplanint(complanintPraiseVO);
    }

    @Override
    public ComplaintDeatilVO getDetailById(Long complaintId) {
        int complaintType= Integer.parseInt(complanintMapper.selectType(complaintId));
        if (complaintType==0){
            return complanintMapper.getComplaintNullById(complaintId);
        }if (complaintType==1){
            ComplaintDeatilVO complaintDeatilVO = complanintMapper.getComplaintById(complaintId);
            return complaintDeatilVO;
        }if(complaintType==2){
            ComplaintDeatilVO complaintDeatilVO = complanintMapper.getParkById(complaintId);
            return complaintDeatilVO;
        }if (complaintType==3){
            ComplaintDeatilVO complaintDeatilVO = complanintMapper.getDisasterById(complaintId);
            return complaintDeatilVO;
        }
//        JSONObject jsonObject = new JSONObject();
//        ComplaintDeatilVO complaintDeatilVO = complanintMapper.getComplaintById(complaintId);
//        jsonObject.put("complaintId",complaintDeatilVO.getComplaintId());
//        jsonObject.put("title",complaintDeatilVO.getTitle());
//        jsonObject.put("complaintType",complaintDeatilVO.getComplaintType());
//        jsonObject.put("accountName",complaintDeatilVO.getAccountName());
//        jsonObject.put("phone",complaintDeatilVO.getLoginName());
//        jsonObject.put("createTime",complaintDeatilVO.getCreateTime());
//        jsonObject.put("remark",complaintDeatilVO.getRemark());
//        jsonObject.put("pictureUrl",complaintDeatilVO.getComplaintUrl());
//        jsonObject.put("orderId",complaintDeatilVO.getOrderId());
//        // TODO： 工单表未创建，待补充
//
//        return jsonObject;
        return complanintMapper.getComplaintNullById(complaintId);
    }

    @Override
    public Integer upPublicStatus(Long complaintId) {
        return complanintMapper.upPublicStatus(complaintId);
    }

    @Override
    public JSONObject getPublicDetails(Long complaintId) {
        JSONObject jsonObject = new JSONObject();
        ComplaintDeatilVO complaintDeatilVO = complanintMapper.getComplaintById(complaintId);
        jsonObject.put("complaintId",complaintDeatilVO.getComplaintId());
        jsonObject.put("title",complaintDeatilVO.getTitle());
        jsonObject.put("complaintType",complaintDeatilVO.getComplaintType());
        jsonObject.put("accountName",complaintDeatilVO.getAccountName());
        jsonObject.put("phone",complaintDeatilVO.getLoginName());
        jsonObject.put("createTime",complaintDeatilVO.getCreateTime());
        jsonObject.put("remark",complaintDeatilVO.getRemark());
        jsonObject.put("pictureUrl",complaintDeatilVO.getComplaintUrl());
        jsonObject.put("orderId",complaintDeatilVO.getOrderId());
        jsonObject.put("publicTime",complaintDeatilVO.getPublicTime());
        jsonObject.put("status",complaintDeatilVO.getPublicStatus());
        jsonObject.put("communityName",complaintDeatilVO.getCommunityName());
        return jsonObject;
    }
}
