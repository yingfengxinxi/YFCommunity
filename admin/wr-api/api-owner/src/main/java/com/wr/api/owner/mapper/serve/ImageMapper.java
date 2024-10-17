package com.wr.api.owner.mapper.serve;


import com.wr.remote.estate.Image;

/**
 * 图片表
 * @Author: lvzy
 * @Date: 2022-11-15 10:56:06
 * @Desc:图片表
 */
public interface ImageMapper {

    /**
     * delete by primary key
     * @param imageId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long imageId);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Image record);

    /**
     * select by primary key
     * @param imageId primary key
     * @return object by primary key
     */
    Image selectByPrimaryKey(Long imageId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Image record);
}