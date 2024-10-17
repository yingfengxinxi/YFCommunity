package com.wr.system.business.service.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.StringUtils;

import com.wr.remote.domain.RoleVer;
import com.wr.remote.domain.ServiceVerMenu;
import com.wr.system.business.domain.ServerVerVo;
import com.wr.system.business.mapper.ServerVerVoMapper;
import com.wr.system.business.service.ServerVerVoService;
import com.wr.remote.domain.SysRoleMenu;
import com.wr.system.mapper.SysRoleMenuMapper;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-09 12:01:36
 * @Desc: 服务版本业务处理层
 */
@Service
public class ServerVerVoServiceImpl implements ServerVerVoService {
    @Autowired
    private ServerVerVoMapper ServerVerVoMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<ServerVerVo> selectServerVerVoList() {

        return ServerVerVoMapper.selectServerVerVoList();
    }

    @Override
    public String checkNameUnique(ServerVerVo version) {
        Long versionId = StringUtils.isNull(version.getVersionId()) ? -1L : version.getVersionId();
        ServerVerVo info = ServerVerVoMapper.checkRoleNameUnique(version.getVersionName());
        if (StringUtils.isNotNull(info) && info.getVersionId().longValue() != versionId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertServerVerVo(ServerVerVo version) {
        ServerVerVoMapper.insertServerVerVo(version);
        return insertRoleVerMenu(version);
    }

    /**
     * 新增服务版本-菜单信息
     * @param version 角色对象
     */
    @Transactional(readOnly = false)
    public int insertRoleVerMenu(ServerVerVo version) {
        int rows = 1;
        // 新增用户与角色管理
        List<ServiceVerMenu> list = Lists.newArrayList();
        for (Long menuId : version.getMenuIds()) {
            ServiceVerMenu rvm = new ServiceVerMenu();
            rvm.setVersionId(version.getVersionId());
            rvm.setMenuId(menuId);
            list.add(rvm);
        }
        if (!list.isEmpty()) {
            rows = ServerVerVoMapper.batchServiceVerMenu(list);
        }
        return rows;
    }

    @Override
    public ServerVerVo getServerVerVoById(Long versionId) {

        return ServerVerVoMapper.getServerVerVoById(versionId);
    }

    @Override
    public List<Long> selectMenuListByVersionId(Long versionId) {

        return ServerVerVoMapper.selectMenuListByVersionId(versionId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateServerVerVo(ServerVerVo version) {
        // 修改角色信息
        ServerVerVoMapper.updateServerVerVo(version);
        // 删除与菜单关联
        ServerVerVoMapper.deleteRoleVerMenuByVersionId(version.getVersionId());

        List<Long> roleIds = ServerVerVoMapper.selectRoleIdsByVersionId(version.getVersionId());
        if(!roleIds.isEmpty()){
            for(Long roleId:roleIds){
                // 删除角色与菜单关联
                roleMenuMapper.deleteRoleMenuByRoleId(roleId);
                insertRoleMenu(roleId,version.getMenuIds());
            }

        }
        return insertRoleVerMenu(version);
    }

    /**
     * 新增角色菜单信息
     * @param roleId
     * @param menuIds
     * @return
     */
    @Transactional(readOnly = false)
    public int insertRoleMenu(Long roleId,Long[] menuIds) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = Lists.newArrayList();
        for (Long menuId : menuIds) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    @Override
    public List<RoleVer> selectRoleVersByVersionId(Long versionId) {

        return ServerVerVoMapper.selectRoleVersByVersionId(versionId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteServerVerVoById(Long versionId) {
        // 删除与菜单关联
        ServerVerVoMapper.deleteRoleVerMenuByVersionId(versionId);
        return ServerVerVoMapper.deleteServerVerVoById(versionId);
    }
}
