package com.wr.api.owner.mapper.serve;

import com.wr.remote.domain.ApiSocialComment;

/**
 * 社交评论记录表
 * @Author: lvzy
 * @Date: 2022-11-16 15:58:38
 * @Desc:社交评论记录表
 */
public interface SocialCommentMapper {
    /**
     * delete by primary key
     * @param commentId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long commentId);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ApiSocialComment record);

    /**
     * select by primary key
     * @param commentId primary key
     * @return object by primary key
     */
    ApiSocialComment selectByPrimaryKey(Long commentId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiSocialComment record);
}