package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.ApiBorrow;

/**
 * 借用申请表
 * @Author: lvzy
 * @Date: 2022-11-14 20:17:04
 * @Desc:借用申请表
 */
public interface BorrowMapper {

    /**
     * delete by primary key 借用申请表
     * @param borrowId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long borrowId);

    /**
     * insert record to table selective 借用申请表
     * @param record the record
     * @return insert count
     */
    Long insertSelective(ApiBorrow record);

    /**
     * select by primary key 借用申请表
     * @param borrowId primary key
     * @return object by primary key
     */
    ApiBorrow selectByPrimaryKey(Long borrowId);

    /**
     * update record selective 借用申请表
     * @param record the updated record
     * @return update count
     */
    Integer updateByPrimaryKeySelective(ApiBorrow record);
}