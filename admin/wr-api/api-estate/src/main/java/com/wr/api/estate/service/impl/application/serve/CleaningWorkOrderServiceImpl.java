package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.CleaningWorkOrderMapper;
import com.wr.api.estate.mapper.application.ReportingWarrantyMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.serve.CleaningWorkOrderService;
import com.wr.remote.domain.CleaningWorkOrder;
import com.wr.remote.domain.ReportingWarranty;
import com.wr.remote.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:50
 */
@Service
public class CleaningWorkOrderServiceImpl extends ServiceImpl<CleaningWorkOrderMapper, CleaningWorkOrder> implements CleaningWorkOrderService {


    @Autowired
    private ReportingWarrantyMapper reportingWarrantyMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public void add(CleaningWorkOrder cleaningWorkOrder) {
        cleaningWorkOrder.setCreateTime(new Date());
        cleaningWorkOrder.setDelFlag("0");
        cleaningWorkOrder.setCleaningStatus("1");

        if (cleaningWorkOrder.getStateRepair().equals("0")) {
            //维修状况无
            cleaningWorkOrder.setCleaningStatus("3");
        }
        if (StringUtils.isNotEmpty(cleaningWorkOrder.getId())) {
            cleaningWorkOrder.setNo(super.getById(cleaningWorkOrder.getId()).getNo());
            super.updateById(cleaningWorkOrder);
        } else {
            cleaningWorkOrder.setNo("QJ" + IdWorker.getIdStr());
            super.save(cleaningWorkOrder);
        }

        //有维修
        if (StringUtils.equals(cleaningWorkOrder.getStateRepair(), "1")) {
            //向报事报修工单插入数据
            ReportingWarranty reportingWarranty = new ReportingWarranty();
            reportingWarranty.setCleaningWorkOrderId(cleaningWorkOrder.getId());
            reportingWarranty.setNo(cleaningWorkOrder.getNo());
            reportingWarranty.setCreateBy(cleaningWorkOrder.getCreateBy());
            reportingWarranty.setCreateTime(cleaningWorkOrder.getCreateTime());
            reportingWarranty.setCommunityId(cleaningWorkOrder.getCommunityId());
            SysUser user = loginService.getByUserIdUser(cleaningWorkOrder.getName());
            reportingWarranty.setName(user.getNickName());
            reportingWarranty.setPhone(user.getPhonenumber());
            reportingWarranty.setReportName(cleaningWorkOrder.getReportName());
            reportingWarranty.setReportType(cleaningWorkOrder.getReportType());
            reportingWarranty.setReportStatus("0");
            reportingWarranty.setReportAddress(cleaningWorkOrder.getReportAddress());
            reportingWarranty.setReportImage(cleaningWorkOrder.getReportImage());
            reportingWarranty.setReportRemark(cleaningWorkOrder.getReportRemark());
            reportingWarrantyMapper.insert(reportingWarranty);
        }
    }

    @Override
    public CleaningWorkOrder get(String id) {
        CleaningWorkOrder cleaningWorkOrder = baseMapper.selectById(id);
        if (cleaningWorkOrder != null) {
            SysUser user = loginService.getByUserIdUser(cleaningWorkOrder.getName());
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
                SysUser sysUser = loginService.getUserNameNikeName(cleaningWorkOrder.getExecutorBy());
                cleaningWorkOrder.setExecutorBy(sysUser.getNickName());
                cleaningWorkOrder.setExecutorPhone(sysUser.getPhonenumber());
            }

        }
        return cleaningWorkOrder;
    }
}
