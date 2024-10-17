package com.wr.estate.service.impl.work.homeorder;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.HomeRepair;
import com.wr.estate.entity.vo.HomeRepairVO;
import com.wr.estate.mapper.work.homeorder.HomeRepairMapper;
import com.wr.estate.service.work.homeorder.HomeRepairService;
import com.wr.remote.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-04 20:52:09
 * @Desc: 居家维修类型 业务实现
 */
@Service
@Slf4j
public class HomeRepairServiceImpl implements HomeRepairService {
    @Autowired
    private HomeRepairMapper homeRepairMapper;

    @Override
    public List<HomeRepairVO> getHomeRepairAll(HomeRepair homeRepair) {
        return homeRepairMapper.getHomeRepairAll(homeRepair);
    }

    @Override
    public HomeRepairVO getHomeRepairById(Long repairId) {
        return homeRepairMapper.getHomeRepairById(repairId);
    }

    @Override
    public String checkNameUnique(HomeRepair homeRepair) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        homeRepair.setEstateId(sysUser.getEstateId());
        //查看是否已存在
        HomeRepair nameUnique = homeRepairMapper.checkNameUnique(homeRepair);
        if (ObjectUtils.isNotEmpty(nameUnique)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Integer addHomeRepair(HomeRepair homeRepair) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        homeRepair.setEstateId(sysUser.getEstateId());
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        homeRepair.setCreateBy(userName);
        homeRepair.setUpdateBy(userName);
        return homeRepairMapper.addHomeRepair(homeRepair);
    }

    @Override
    public Integer upHomeRepair(HomeRepair homeRepair) {
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        homeRepair.setUpdateBy(userName);
        return homeRepairMapper.upHomeRepair(homeRepair);
    }

    @Override
    public Integer delHomeRepair(Long[] repairIds) {
        return homeRepairMapper.delHomeRepair(repairIds);
    }
}
