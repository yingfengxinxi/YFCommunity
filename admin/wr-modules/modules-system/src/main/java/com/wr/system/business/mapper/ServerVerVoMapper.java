package com.wr.system.business.mapper;

import com.wr.remote.domain.RoleVer;
import com.wr.remote.domain.ServiceVerMenu;
import com.wr.system.business.domain.ServerVerVo;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-09 12:01:48
 * @Desc: 服务版本数据层
 */
public interface ServerVerVoMapper {

    /**
     * 查询寻数据列表
     * @return
     */
    List<ServerVerVo> selectServerVerVoList();

    /**
     * 根据名称查询
     * @param versionName
     * @return
     */
    ServerVerVo checkRoleNameUnique(String versionName);

    /**
     * 添加数据
     * @param version
     * @return
     */
    Integer insertServerVerVo(ServerVerVo version);

    /**
     * 批量添加数据
     * @param list
     * @return
     */
    Integer batchServiceVerMenu(List<ServiceVerMenu> list);

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
     * 删除与菜单关联
     * @param versionId
     * @return
     */
    Integer deleteRoleVerMenuByVersionId(Long versionId);

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


    /**
     * 根据版本id 查询角色id
     * @param versionId
     * @return
     */
    List<Long> selectRoleIdsByVersionId(Long versionId);
}
