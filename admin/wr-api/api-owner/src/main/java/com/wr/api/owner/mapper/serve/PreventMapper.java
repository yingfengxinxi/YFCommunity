package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.PreventVO;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.PreventSafety;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 10:21:03
 * @Desc: 服务-防控信息mapper
 */
public interface PreventMapper {

    /**
     * 查看本小区的防控信息
     * @param communityId 小区id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.PreventVO>
     */
    List<PreventSafety> getPreventAll(@Param("communityId") Long communityId);

    /**
     * 查看防控信息详情
     * @param preventId 主键id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.PreventVO>
     */
    PreventSafety getPreventById(@Param("preventId") Long preventId);

    /**
     * 获取防控信息集合
     * @param prevent
     * @return com.wr.remote.govern.security.Prevent
     */
    List<Prevent> getList(Prevent prevent);

    /**
     * 添加防控信息
     * @param prevent
     * @return java.lang.Long
     */
    Integer addPrevent(com.wr.remote.govern.security.vo.PreventVO prevent);

    /**
     * 添加防控信息表
     * @param preventId communityIds
     * @return java.lang.Long
     */
    Integer addPreventRang(@Param("preventId") Long preventId,@Param("communityIds")Long[] communityIds);

    /**
     * 防空详情
     * @param preventId
     * @return com.wr.remote.govern.security.vo.PreventVO
     */
    com.wr.remote.govern.security.vo.PreventVO getPreventDetail(Long preventId);

    /**
     * 获取关联的小区id
     * @param preventId
     * @return java.lang.Long
     */
    Long[] getPreventRangInfo(Long preventId);

    /**
     * 修改防控信息
     * @param prevent
     * @return java.lang.Integer
     */
    Integer updatePrevent(com.wr.remote.govern.security.vo.PreventVO prevent);

    /**
     * 删除关联表
     * @param preventId
     * @return java.lang.Integer
     */
    Integer removePreventRang(Long preventId);

    /**
     * 删除防控信息
     * @param preventId
     * @return java.lang.Integer
     */
    Integer delPrevent(Long preventId);
}
