package com.wr.system.business.mapper;

import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.CommunityWechat;
import com.wr.system.business.domain.CommunityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-16 15:46:31
 * @Desc:
 */
public interface CommunityMapper {

    /**
     * 查询小区管理
     *
     * @param communityId 小区管理ID
     * @return 小区管理
     */
    CommunityVo selectCommunityVoById(Long communityId);

    /**
     * 根据表名和小区id查询 通用查询
     *
     * @param communityTable 表名
     * @param communityId    小区管理ID
     * @return 小区管理
     */
    Integer communityTableColumnsById(@Param("communityTable") String communityTable, @Param("communityId") Long communityId);

    /**
     * 查询小区管理列表
     *
     * @param communityVo 小区管理
     * @return 小区管理集合
     */
    List<CommunityVo> selectCommunityVoList(CommunityVo communityVo);

    /*
     * 查询所有小区
     * */
    List<CommunityVo> selectCommunityVo();

    /**
     * 新增小区管理
     *
     * @param communityVo 小区管理
     * @return 结果
     */
    Integer insertCommunityVo(CommunityVo communityVo);

    /**
     * 新增海康配置管理
     *
     * @param communityHik 海康配置管理
     * @return 结果
     */
    Integer insertCommunityHikVo(CommunityHik communityHik);

    /**
     * 新增微信配置管理
     *
     * @param communityWechat 微信配置管理
     * @return 结果
     */
    Integer insertCommunityWechatVo(CommunityWechat communityWechat);

    /**
     * 修改小区管理
     *
     * @param communityVo 小区管理
     * @return 结果
     */
    Integer updateCommunityVo(CommunityVo communityVo);

    /**
     * 修改海康配置管理
     *
     * @param communityHik 海康配置管理
     * @return 结果
     */
    Integer updateCommunityHikVo(CommunityHik communityHik);

    /**
     * 修改微信配置管理
     *
     * @param communityWechat 微信配置管理
     * @return 结果
     */
    Integer updateCommunityWechatVo(CommunityWechat communityWechat);

    /**
     * 删除小区管理
     *
     * @param communityId 小区管理ID
     * @return 结果
     */
    Integer deleteCommunityVoById(Long communityId);

    /**
     * 删除小区相关配置
     *
     * @param communityTable 表名 communityId 小区管理ID
     * @return 结果
     */
    Integer deleteCommunityTableVoById(@Param("communityTable") String communityTable, @Param("communityId") Long communityId);


    /**
     * 根据小区id查询海康配置
     * @param communityId 小区id
     * @return com.wr.common.customize.util.CommunityHik
     */
    CommunityHik selectHikByCommunityId(Long communityId);
}
