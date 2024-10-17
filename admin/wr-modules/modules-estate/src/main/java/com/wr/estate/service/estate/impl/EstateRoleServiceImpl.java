package com.wr.estate.service.estate.impl;

import com.wr.estate.mapper.estate.EstateRoleMapper;
import com.wr.estate.service.estate.EstateRoleService;
import com.wr.remote.domain.EstateRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色表
 */
@Service
public class EstateRoleServiceImpl implements EstateRoleService {

    @Autowired
    private EstateRoleMapper estateRoleMapper;


    @Override
    public List<EstateRole> getList(EstateRole estateRole) {
        return estateRoleMapper.getList(estateRole);
    }

    @Override
    public EstateRole getById(Long estateRoleId) {
        return estateRoleMapper.getById(estateRoleId);
    }

    @Override
    public Integer getByNameCount(Long estateId, String estateRoleName, Long estateRoleId) {
        return estateRoleMapper.getByNameCount(estateId,estateRoleName, estateRoleId);
    }

    @Override
    public int insert(EstateRole estateRole) {
        return estateRoleMapper.insert(estateRole);
    }

    @Override
    public int update(EstateRole estateRole) {
        return estateRoleMapper.update(estateRole);
    }

    @Override
    public void delete(Long estateRoleId) {
        estateRoleMapper.delete(estateRoleId);
    }
}
