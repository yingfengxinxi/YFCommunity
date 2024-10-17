package com.wr.estate.mapper.activity;


import com.wr.remote.estate.activity.ActiviteType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:11:50
 * @Desc:
 */
public interface ActiviteTypeMapper {

    /**
     * 查询看板数据表列表
     *
     * @param activiteType 查询条件
     * @return 对象列表
     */
    List<ActiviteType> selectList(ActiviteType activiteType);

    /**
     * 新增活动
     *
     * @param activiteType 查询条件
     * @return 对象列表
     */
    int insertActivite(ActiviteType activiteType);
    /**
     * 编辑活动
     *
     * @param activiteType 查询条件
     * @return 对象列表
     */
    int updateActiviteById(ActiviteType activiteType);
    /**
     * 根据ID查询信息
     *
     * @param typeId 查询条件
     * @return 对象列表
     */
    ActiviteType selectInfoById(Long typeId);
    /**
     * 删除
     *
     * @param typeId 查询条件
     * @return 对象列表
     */
    int removeActiviteById(Long typeId);
    /**
     * 根据活动名称查询活动是否存在
     *
     * @param typeName 查询条件
     * @return 对象列表
     */
    int selectTypeName(@Param("typeName") String typeName, @Param("communityId") Long communityId);



}
