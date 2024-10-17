package com.wr.govern.mapper.security;


import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.PreventVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:18:50
 * @Desc: 防控信息DAO层
 */
@Mapper
public interface PreventMapper {

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
    Integer addPrevent(PreventVO prevent);

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
    PreventVO getPreventDetail(Long preventId);

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
    Integer updatePrevent(PreventVO prevent);

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