package com.wr.govern.service.security;

import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.PreventVO;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:18:11
 * @Desc: 防控信息接口层
 */
public interface PreventService {

    /**
     * 获取防控信息集合
     * @param prevent
     * @return com.wr.remote.govern.security.Prevent
     */
    List<Prevent> getList(Prevent prevent);

    /**
     * 添加防控
     * @param prevent
     * @return java.lang.Integer
     */
    Integer addPrevent(PreventVO prevent);

    /**
     * 防空详情
     * @param preventId
     * @return com.wr.remote.govern.security.vo.PreventVO
     */
    PreventVO getPreventDetail(Long preventId);

    /**
     * 修改防空信息
     * @param prevent
     * @return java.lang.Integer
     */
    Integer updatePrevent(PreventVO prevent);

    /**
     * 删除防空信息
     * @param preventId
     * @return java.lang.Integer
     */
    Integer delPrevent(Long preventId);

}
