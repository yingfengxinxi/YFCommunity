package com.wr.api.owner.mapper.home.location;

import com.wr.api.owner.entity.home.location.ApiOrderImage;

import java.util.List;

/**
 * 工单图片表表
 * @Author: lvzy
 * @Date: 2022-12-01 17:26:42
 * @Desc:工单图片表表
 */
public interface ApiOrderImageMapper {

    int deleteByPrimaryKey(Long imageId);

    int insertSelective(ApiOrderImage record);

    ApiOrderImage selectByPrimaryKey(Long imageId);

    int updateByPrimaryKeySelective(ApiOrderImage record);

    /**
     * 批量新增
     * @param orderImages
     * @return
     */
    int insertList(List<ApiOrderImage> orderImages);
}