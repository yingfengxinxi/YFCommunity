package com.wr.api.owner.mapper.home.location;

import com.wr.api.owner.entity.home.location.ApiHomeRepair;

/**
 * 居家维修类型表
 * @Author: lvzy
 * @Date: 2022-12-01 14:40:54
 * @Desc:居家维修类型表
 */
public interface ApiHomeRepairMapper {

    int deleteByPrimaryKey(Long repairId);

    int insertSelective(ApiHomeRepair record);

    ApiHomeRepair selectByPrimaryKey(Long repairId);

    int updateByPrimaryKeySelective(ApiHomeRepair record);
}