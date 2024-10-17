package com.wr.api.estate.mapper.application.security;


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
     *
     * @param prevent
     * @return com.wr.remote.govern.security.Prevent
     */
    List<Prevent> getList(Prevent prevent);

    /**
     * @param estateId
     * @return
     */
    List<String> getByEstateIdPreventId(@Param("estateId") Long estateId, @Param("communityId") Long communityId);

    /**
     * 防空详情
     *
     * @param preventId
     * @return com.wr.remote.govern.security.vo.PreventVO
     */
    PreventVO getPreventDetail(Long preventId);
}