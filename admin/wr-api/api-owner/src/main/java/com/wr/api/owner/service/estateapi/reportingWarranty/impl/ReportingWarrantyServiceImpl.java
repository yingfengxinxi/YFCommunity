package com.wr.api.owner.service.estateapi.reportingWarranty.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.reportingWarranty.ReportingWarrantyMapper;
import com.wr.api.owner.service.estateapi.reportingWarranty.ReportingWarrantyService;
import com.wr.remote.domain.ReportingWarranty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:57
 */
@Service
public class ReportingWarrantyServiceImpl extends ServiceImpl<ReportingWarrantyMapper, ReportingWarranty> implements ReportingWarrantyService {
    @Override
    public List<ReportingWarranty> getList(ReportingWarranty reportingWarranty) {
        return baseMapper.getList(reportingWarranty);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ReportingWarranty get(String id) {
        return baseMapper.get(id);
    }
}
