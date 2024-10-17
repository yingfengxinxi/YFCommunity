package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.ApiGoods;
import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;

import java.util.List;

/**
 * 物品借用
 * @Author: lvzy
 * @Date: 2022-11-14 13:44:10
 * @Desc: 物品借用
 */
public interface GoodsMapper {

    /**
     * delete by primary key
     * @param goodsId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long goodsId);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ApiGoods record);

    /**
     * select by primary key
     * @param goodsId primary key
     * @return object by primary key
     */
    ApiGoods selectByPrimaryKey(Long goodsId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiGoods record);

    /**
     * 根据小区ID查物品借用列表
     * @param communityId 小区ID
     * @return List
     */
    List<ApiGoodsVo> listPage(Long communityId);

    /**
     * 详情查看
     * @param goodsId
     * @return
     */
    ApiGoodsVo detail(Long goodsId);

    /**
     * 改借用状态
     * @param goodsId
     * @return
     */
    Integer updateById(Long goodsId);
}