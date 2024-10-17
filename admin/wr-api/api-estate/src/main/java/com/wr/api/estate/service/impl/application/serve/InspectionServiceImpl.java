package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.InspectionMapper;
import com.wr.api.estate.mapper.application.ReportingWarrantyMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.serve.InspectionService;
import com.wr.remote.domain.Inspection;
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
public class InspectionServiceImpl extends ServiceImpl<InspectionMapper, Inspection> implements InspectionService {


    @Autowired
    private ReportingWarrantyMapper reportingWarrantyMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public void add(Inspection inspection) {
        inspection.setCreateTime(new Date());
        inspection.setDelFlag("0");
        inspection.setInspectionStatus("1");

        if (inspection.getStateRepair().equals("0")) {
            //维修状况无
            inspection.setInspectionStatus("3");
        }
        if (StringUtils.isNotEmpty(inspection.getId())) {
            inspection.setNo(super.getById(inspection.getId()).getNo());
            super.updateById(inspection);
        } else {
            inspection.setNo("XC" + IdWorker.getIdStr());
            super.save(inspection);
        }

        //有维修
        if (StringUtils.equals(inspection.getStateRepair(), "1")) {
            //向报事报修工单插入数据
            ReportingWarranty reportingWarranty = new ReportingWarranty();
            reportingWarranty.setInspectionId(inspection.getId());
            reportingWarranty.setNo(inspection.getNo());
            reportingWarranty.setCreateBy(inspection.getCreateBy());
            reportingWarranty.setCreateTime(inspection.getCreateTime());
            reportingWarranty.setCommunityId(inspection.getCommunityId());
            SysUser user = loginService.getByUserIdUser(inspection.getName());
            reportingWarranty.setName(user.getNickName());
            reportingWarranty.setPhone(user.getPhonenumber());
            reportingWarranty.setReportName(inspection.getReportName());
            reportingWarranty.setReportType(inspection.getReportType());
            reportingWarranty.setReportStatus("0");
            reportingWarranty.setReportAddress(inspection.getReportAddress());
            reportingWarranty.setReportImage(inspection.getReportImage());
            reportingWarranty.setReportRemark(inspection.getReportRemark());
            reportingWarrantyMapper.insert(reportingWarranty);
        }
    }

    @Override
    public Inspection get(String id) {
        Inspection inspection = baseMapper.selectById(id);
        if (inspection != null) {
            SysUser user = loginService.getByUserIdUser(inspection.getName());
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
                SysUser sysUser = loginService.getUserNameNikeName(inspection.getExecutorBy());
                inspection.setExecutorBy(sysUser.getNickName());
                inspection.setExecutorPhone(sysUser.getPhonenumber());
            }

        }
        return inspection;
    }
}
