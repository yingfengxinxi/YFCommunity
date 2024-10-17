package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.ApiGoodsOut;
import com.wr.api.owner.entity.vo.serve.ApiGoodsOutVo;
import com.wr.remote.estate.Image;
import com.wr.remote.estate.manage.Passage;

import java.util.List;

/**
 * 物品出门
 * @Author: lvzy
 * @Date: 2022-11-15 10:55:03
 * @Desc:物品出门
 */
public interface GoodsOutMapper {

    /**
     * delete by primary key
     * @param outId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long outId);

    /**
     * insert record to table selective
     * @param goodsOutVo the record
     * @return insert count
     */
    Long insertSelective(ApiGoodsOutVo goodsOutVo);

    /**
     * select by primary key
     * @param outId primary key
     * @return object by primary key
     */
    ApiGoodsOut selectByPrimaryKey(Long outId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiGoodsOut record);

    /**
     * 查找出入口
     * @param communityId
     * @return List
     */
    List<Passage> passageList(Long communityId);

    /**
     * 分页
     * @param accountId
     * @return
     */
    List<ApiGoodsOutVo> listPage(Long accountId);

    /**
     * 图片URL
     * @param outId
     * @return
     */
    List<Image> selectImageUrl(Long outId);

    /**
     * 详情查看
     * @param outId
     * @return
     */
    ApiGoodsOutVo detail(Long outId);
}