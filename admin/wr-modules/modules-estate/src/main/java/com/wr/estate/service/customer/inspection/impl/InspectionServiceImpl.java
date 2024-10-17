package com.wr.estate.service.customer.inspection.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.customer.inspection.InspectionMapper;
import com.wr.estate.mapper.manage.StaffMapper;
import com.wr.estate.service.customer.inspection.InspectionService;
import com.wr.remote.domain.Inspection;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.Staff;
import com.wr.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:43
 */
@Service
public class InspectionServiceImpl extends ServiceImpl<InspectionMapper, Inspection> implements InspectionService {

    @Autowired
    private UserService userService;

    @Override
    public Inspection get(String id) {
        Inspection inspection = baseMapper.selectById(id);
        if (inspection != null) {
            SysUser user = userService.selectUserById(Long.valueOf(inspection.getName()));
            if (user != null) {
                inspection.setName(user.getNickName());
                inspection.setPhone(user.getPhonenumber());
            }

            if (StringUtils.isNotEmpty(inspection.getInspectionImage())) {
                inspection.setInspectionImages(Arrays.asList(inspection.getInspectionImage().split(",")));
            }

            if (StringUtils.isNotEmpty(inspection.getReportImage())) {
                inspection.setReportImages(Arrays.asList(inspection.getReportImage().split(",")));
            }

            if (StringUtils.isNotEmpty(inspection.getOrderImage())) {
                inspection.setOrderImages(Arrays.asList(inspection.getOrderImage().split(",")));
            }
            if (StringUtils.isNotEmpty(inspection.getExecutorBy())) {
                SysUser sysUser = userService.getUserByUserName(inspection.getExecutorBy());
                inspection.setExecutorBy(sysUser.getNickName());
                inspection.setExecutorPhone(sysUser.getPhonenumber());
            }

        }
        return inspection;
    }

    @Override
    public List<Inspection> getList(LambdaQueryWrapper<Inspection> queryWrapper) {
        List<Inspection> inspectionList = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(inspectionList)) {
            inspectionList.stream().forEach(inspection -> {
                inspection.setTime(inspection.getCreateTime());
                SysUser user = userService.selectUserById(Long.valueOf(inspection.getName()));
                if (user != null) {
                    inspection.setName(user.getNickName());
                    inspection.setPhone(user.getPhonenumber());
                }


                if (StringUtils.isNotEmpty(inspection.getExecutorBy())) {
                    SysUser sysUser = userService.getUserByUserName(inspection.getExecutorBy());
                    inspection.setExecutorBy(sysUser.getNickName());
                    inspection.setExecutorPhone(sysUser.getPhonenumber());
                }
            });
        }
        return inspectionList;
    }
}
