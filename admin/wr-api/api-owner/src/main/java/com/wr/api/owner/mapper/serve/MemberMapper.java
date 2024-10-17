package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.ApiMember;

/**
 * 党员管理
 * @Author: lvzy
 * @Date: 2022-11-17 20:41:29
 * @Desc:党员管理
 */
public interface MemberMapper {

    /**
     * delete by primary key
     * @param memberId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long memberId);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ApiMember record);

    /**
     * select by primary key
     * @param memberId primary key
     * @return object by primary key
     */
    ApiMember selectByPrimaryKey(Long memberId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiMember record);
}