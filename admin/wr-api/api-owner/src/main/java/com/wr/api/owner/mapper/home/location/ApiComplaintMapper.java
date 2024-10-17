package com.wr.api.owner.mapper.home.location;

import com.wr.api.owner.entity.home.location.ApiComplaint;

/**
 * 投诉表扬表
 * @Author: lvzy
 * @Date: 2022-12-02 10:46:16
 * @Desc:投诉表扬表
 */
public interface ApiComplaintMapper {

    /**
     * 删除
     * @param complaintId
     * @return
     */
    int deleteByPrimaryKey(Long complaintId);

    /**
     * 新增
     * @param record
     * @return
     */
    int insertSelective(ApiComplaint record);

    /**
     * 详情
     * @param complaintId
     * @return
     */
    ApiComplaint selectByPrimaryKey(Long complaintId);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ApiComplaint record);
}