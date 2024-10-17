package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.PatrolMapper;
import com.wr.api.estate.mapper.application.ReportingWarrantyMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.serve.PatrolService;
import com.wr.remote.domain.Patrol;
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
public class PatrolServiceImpl extends ServiceImpl<PatrolMapper, Patrol> implements PatrolService {


    @Autowired
    private ReportingWarrantyMapper reportingWarrantyMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public void add(Patrol patrol) {
        patrol.setCreateTime(new Date());
        patrol.setDelFlag("0");
        patrol.setPatrolStatus("1");

        if (patrol.getStateRepair().equals("0")) {
            //维修状况无
            patrol.setPatrolStatus("3");
        }
        if (StringUtils.isNotEmpty(patrol.getId())) {
            patrol.setNo(super.getById(patrol.getId()).getNo());
            super.updateById(patrol);
        } else {
            patrol.setNo("XG" + IdWorker.getIdStr());
            super.save(patrol);
        }

        //有维修
        if (StringUtils.equals(patrol.getStateRepair(), "1")) {
            //向报事报修工单插入数据
            ReportingWarranty reportingWarranty = new ReportingWarranty();
            reportingWarranty.setPatrolWorkOrderId(patrol.getId());
            reportingWarranty.setNo(patrol.getNo());
            reportingWarranty.setCreateBy(patrol.getCreateBy());
            reportingWarranty.setCreateTime(patrol.getCreateTime());
            reportingWarranty.setCommunityId(patrol.getCommunityId());
            SysUser user = loginService.getByUserIdUser(patrol.getName());
            reportingWarranty.setName(user.getNickName());
            reportingWarranty.setPhone(user.getPhonenumber());
            reportingWarranty.setReportName(patrol.getReportName());
            reportingWarranty.setReportType(patrol.getReportType());
            reportingWarranty.setReportStatus("0");
            reportingWarranty.setReportAddress(patrol.getReportAddress());
            reportingWarranty.setReportImage(patrol.getReportImage());
            reportingWarranty.setReportRemark(patrol.getReportRemark());
            reportingWarrantyMapper.insert(reportingWarranty);
        }
    }

    @Override
    public Patrol get(String id) {
        Patrol patrol = baseMapper.selectById(id);
        if (patrol != null) {
            SysUser user = loginService.getByUserIdUser(patrol.getName());
            if (user != null) {
                patrol.setName(user.getNickName());
                patrol.setPhone(user.getPhonenumber());
            }
            if (StringUtils.isNotEmpty(patrol.getPatrolImage())) {
                patrol.setPatrolImages(Arrays.asList(patrol.getPatrolImage().split(",")));
            }

            if (StringUtils.isNotEmpty(patrol.getReportImage())) {
                patrol.setReportImages(Arrays.asList(patrol.getReportImage().split(",")));
            }

            if (StringUtils.isNotEmpty(patrol.getOrderImage())) {
                patrol.setOrderImages(Arrays.asList(patrol.getOrderImage().split(",")));
            }
            if (StringUtils.isNotEmpty(patrol.getExecutorBy())) {
                SysUser sysUser = loginService.getUserNameNikeName(patrol.getExecutorBy());
                patrol.setExecutorBy(sysUser.getNickName());
                patrol.setExecutorPhone(sysUser.getPhonenumber());
            }

        }
        return patrol;
    }
}
