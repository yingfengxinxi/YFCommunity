package com.wr.estate.service.customer.cleaningWorkOrder.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.customer.cleaningWorkOrder.CleaningWorkOrderMapper;
import com.wr.estate.mapper.manage.StaffMapper;
import com.wr.estate.service.customer.cleaningWorkOrder.CleaningWorkOrderService;
import com.wr.remote.domain.CleaningWorkOrder;
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
public class CleaningWorkOrderServiceImpl extends ServiceImpl<CleaningWorkOrderMapper, CleaningWorkOrder> implements CleaningWorkOrderService {

    @Autowired
    private UserService userService;

    /**
     * @param id
     * @return
     */
    @Override
    public CleaningWorkOrder get(String id) {
        CleaningWorkOrder cleaningWorkOrder = baseMapper.selectById(id);
        if (cleaningWorkOrder != null) {
            SysUser user = userService.selectUserById(Long.valueOf(cleaningWorkOrder.getName()));
            if (user != null) {
                cleaningWorkOrder.setName(user.getNickName());
                cleaningWorkOrder.setPhone(user.getPhonenumber());
            }
            if (StringUtils.isNotEmpty(cleaningWorkOrder.getCleaningImage())) {
                cleaningWorkOrder.setCleaningImages(Arrays.asList(cleaningWorkOrder.getCleaningImage().split(",")));
            }

            if (StringUtils.isNotEmpty(cleaningWorkOrder.getReportImage())) {
                cleaningWorkOrder.setReportImages(Arrays.asList(cleaningWorkOrder.getReportImage().split(",")));
            }

            if (StringUtils.isNotEmpty(cleaningWorkOrder.getOrderImage())) {
                cleaningWorkOrder.setOrderImages(Arrays.asList(cleaningWorkOrder.getOrderImage().split(",")));
            }
            if (StringUtils.isNotEmpty(cleaningWorkOrder.getExecutorBy())) {
                SysUser sysUser = userService.getUserByUserName(cleaningWorkOrder.getExecutorBy());
                cleaningWorkOrder.setExecutorBy(sysUser.getNickName());
                cleaningWorkOrder.setExecutorPhone(sysUser.getPhonenumber());
            }

        }
        return cleaningWorkOrder;
    }

    /**
     * @param queryWrapper
     * @return
     */
    @Override
    public List<CleaningWorkOrder> getList(LambdaQueryWrapper<CleaningWorkOrder> queryWrapper) {
        List<CleaningWorkOrder> cleaningWorkOrderList = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(cleaningWorkOrderList)) {
            cleaningWorkOrderList.stream().forEach(cleaningWorkOrder -> {
                cleaningWorkOrder.setTime(cleaningWorkOrder.getCreateTime());
                SysUser user = userService.selectUserById(Long.valueOf(cleaningWorkOrder.getName()));
                if (user != null) {
                    cleaningWorkOrder.setName(user.getNickName());
                    cleaningWorkOrder.setPhone(user.getPhonenumber());
                }
                if (StringUtils.isNotEmpty(cleaningWorkOrder.getExecutorBy())) {
                    SysUser sysUser = userService.getUserByUserName(cleaningWorkOrder.getExecutorBy());
                    cleaningWorkOrder.setExecutorBy(sysUser.getNickName());
                    cleaningWorkOrder.setExecutorPhone(sysUser.getPhonenumber());
                }
            });
        }
        return cleaningWorkOrderList;
    }
}
