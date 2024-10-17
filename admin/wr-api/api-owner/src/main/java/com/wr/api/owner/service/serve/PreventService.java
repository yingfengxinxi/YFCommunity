package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.PreventVO;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.PreventSafety;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 10:39:57
 * @Desc: 服务-防控信息业务
 */
public interface PreventService {

    /**
     * 查看本小区的防控信息
     * @param communityId 小区id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.PreventVO>
     */
    List<PreventSafety> getPreventAll(Long communityId);

    /**
     * 查看防控信息详情
     * @param preventId 主键id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.PreventVO>
     */
    PreventSafety getPreventById(Long preventId);

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
    Integer addPrevent(com.wr.remote.govern.security.vo.PreventVO prevent);

    /**
     * 防空详情
     * @param preventId
     * @return com.wr.remote.govern.security.vo.PreventVO
     */
    com.wr.remote.govern.security.vo.PreventVO getPreventDetail(Long preventId);

    /**
     * 修改防空信息
     * @param prevent
     * @return java.lang.Integer
     */
    Integer updatePrevent(com.wr.remote.govern.security.vo.PreventVO prevent);

    /**
     * 删除防空信息
     * @param preventId
     * @return java.lang.Integer
     */
    Integer delPrevent(Long preventId);
}
