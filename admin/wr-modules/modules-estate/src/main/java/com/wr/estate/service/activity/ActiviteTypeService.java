package com.wr.estate.service.activity;

import com.wr.remote.estate.activity.ActiviteType;

import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:39:49
 * @Desc:
 */
public interface ActiviteTypeService {

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
     * @param ids 查询条件
     * @return 对象列表
     */
    int removeActiviteById(Long[] ids);
    /**
     * 根据活动名称查询活动是否存在
     *
     * @param typeName 查询条件
     * @return 对象列表
     */
    int selectTypeName(String typeName,Long communityId);
}
