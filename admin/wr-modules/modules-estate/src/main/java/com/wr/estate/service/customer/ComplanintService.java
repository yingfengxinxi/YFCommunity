package com.wr.estate.service;

import com.alibaba.fastjson2.JSONObject;
import com.wr.remote.estate.custom.vo.ComplaintDeatilVO;
import com.wr.remote.estate.custom.vo.ComplanintPraiseVO;

import java.util.List;

/**
 * @Author: 荀彧
 * @Date: 2022-10-11 15:37:07
 * @Desc: 投诉表扬service
 */
public interface ComplanintService {

    /**
     * 查询 表扬或投诉表格
     */
    List<ComplanintPraiseVO> getAllComplanint(ComplanintPraiseVO complanintPraiseVO);


    /**
     * 根据ID 查询详情
     * @param id 投诉表扬表主键
     * @return
     */
    ComplaintDeatilVO getDetailById(Long complaintId);

    /**
     * 公示
     * @param complaintId
     * @return
     */
    Integer upPublicStatus(Long complaintId);

    /**
     * 查看已公告详情
     * @param complaintId
     * @return
     */
    JSONObject getPublicDetails(Long complaintId);
}
