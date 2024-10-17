package com.wr.api.estate.service.application.security;

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
     * 防空详情
     * @param preventId
     * @return com.wr.remote.govern.security.vo.PreventVO
     */
    PreventVO getPreventDetail(Long preventId);

}
