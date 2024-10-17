package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.ApiRemind;

/**
 * 借用提醒记录表
 * @Author: lvzy
 * @Date: 2022-11-14 20:12:55
 * @Desc:借用提醒记录表
 */
public interface RemindMapper {

    /**
     * delete by primary key
     * @param remindId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long remindId);

    /**
     * insert 借用提醒记录表
     * @param record the record
     * @return insert count
     */
    int insertSelective(ApiRemind record);

    /**
     * select 借用提醒记录 详情
     * @param remindId primary key
     * @return object by primary key
     */
    ApiRemind selectByPrimaryKey(Long remindId);

    /**
     * update 借用提醒记录表
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiRemind record);
}