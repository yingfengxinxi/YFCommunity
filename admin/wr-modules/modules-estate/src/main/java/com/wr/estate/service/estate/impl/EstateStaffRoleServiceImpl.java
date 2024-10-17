package com.wr.estate.service.estate.impl;

import com.wr.estate.mapper.estate.EstateStaffRoleMapper;
import com.wr.estate.service.estate.EstateStaffRoleService;
import com.wr.remote.domain.EstateStaffRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色中间表
 */
@Service
public class EstateStaffRoleServiceImpl implements EstateStaffRoleService {

    @Autowired
    private EstateStaffRoleMapper estateStaffRoleMapper;

    @Override
    public List<EstateStaffRole> getByStaffIdList(Long staffId) {
        return estateStaffRoleMapper.getByStaffIdList(staffId);
    }

    @Override
    public void insert(List<EstateStaffRole> estateStaffRoleList) {
        Long staffId = estateStaffRoleList.get(0).getStaffId();
        this.deleteByStaffId(staffId);
        for (EstateStaffRole estateStaffRole : estateStaffRoleList) {
            estateStaffRoleMapper.insert(estateStaffRole);
        }

    }

    @Override
    public void deleteByStaffId(Long staffId) {
        estateStaffRoleMapper.deleteByStaffId(staffId);
    }

    @Override
    public Integer getByEstateRoleIdCount(Long estateRoleId) {
        return estateStaffRoleMapper.getByEstateRoleIdCount(estateRoleId);
    }
}
