package com.wr.system.business.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.CommunityWechat;
import com.wr.system.business.domain.CommunityVo;

import java.util.List;


/**
 * 社区信息管理 service 层
 * @Date 2022/9/22 15:38
 * @Author DIANWEI
 **/
public interface CommunityService {
    /**
     * 查询小区管理
     *
     * @param communityId 小区管理ID
     * @return 小区管理
     */
    CommunityVo selectCommunityVoById(Long communityId);
    /**
     * 根据表名和小区id查询 通用查询
     * @param communityTable 表名
     * @param communityId 小区管理ID
     * @return 小区管理
     */
    Integer communityTableColumnsById(String communityTable,Long communityId);

    /**
     * 查询小区管理列表
     *
     * @param communityVo 小区管理
     * @return 小区管理集合
     */
    public List<CommunityVo> selectCommunityVoList(CommunityVo communityVo);

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
     * 删除小区管理信息
     *
     * @param communityId 小区管理ID
     * @return 结果
     */
    Integer deleteCommunityVoById(Long communityId);

    /**
     * 订阅信息
     * @param communityIds
     * @return
     */
    AjaxResult addSubscribe(Long[] communityIds);

    /**
     * 根据小区id查询海康配置
     * @param communityId 小区id
     * @return com.wr.common.customize.util.CommunityHik
     */
    CommunityHik selectHikByCommunityId(Long communityId);
}
