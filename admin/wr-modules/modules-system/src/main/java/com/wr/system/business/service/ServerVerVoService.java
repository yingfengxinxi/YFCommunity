package com.wr.system.business.service;

import com.wr.remote.domain.RoleVer;
import com.wr.system.business.domain.ServerVerVo;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-09 12:01:21
 * @Desc:
 */
public interface ServerVerVoService {
    /**
     * 查询寻数据列表
     * @return
     */
    List<ServerVerVo> selectServerVerVoList();

    /**
     * 校验名称是否唯一
     * @param version
     * @return
     */
    String checkNameUnique(ServerVerVo version);

    /**
     * 添加数据
     * @param version
     * @return
     */
    Integer insertServerVerVo(ServerVerVo version);

    /**
     * 根据id查询数据
     * @param versionId
     * @return
     */
    ServerVerVo getServerVerVoById(Long versionId);

    /**
     * 根据ID查询菜单树信息
     * @param versionId
     * @return
     */
    List<Long> selectMenuListByVersionId(Long versionId);

    /**
     * 修改数据
     * @param version
     * @return
     */
    Integer updateServerVerVo(ServerVerVo version);

    /**
     * 根据versionId 查询是否绑定物业管理员相关角色
     * @param versionId
     * @return
     */
    List<RoleVer> selectRoleVersByVersionId(Long versionId);

    /**
     * 根据id删除数据
     * @param versionId
     * @return
     */
    Integer deleteServerVerVoById(Long versionId);

}
