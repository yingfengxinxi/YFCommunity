package com.wr.api.owner.mapper.serve;

import com.wr.remote.domain.ApiMessage;
import com.wr.remote.domain.vo.ApiMessageVo;

import java.util.List;

/**
 * 党建资讯
 * @Author: lvzy
 * @Date: 2022-11-15 16:37:09
 * @Desc:党建资讯
 */
public interface MessageMapper {

    /**
     * delete by primary key
     * @param messageId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long messageId);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ApiMessage record);

    /**
     * select by primary key
     * @param messageId primary key
     * @return object by primary key
     */
    ApiMessage selectByPrimaryKey(Long messageId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiMessage record);

    /**
     * 分页
     * @param communityId
     * @return
     */
    List<ApiMessage> listPage(Long communityId);

    /**
     * 详情
     * @param messageId
     * @return
     */
    ApiMessageVo detail(Long messageId);

    /**
     * 添加一次浏览次数
     * @param messageId 主键
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-04-24 15:59:47
     * @ver v1.0.0
     */
    Integer addReadNum(Long messageId);
}