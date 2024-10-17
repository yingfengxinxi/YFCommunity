package com.wr.estate.service.reportingWarranty;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.ReportingWarranty;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:56
 */
public interface ReportingWarrantyService extends IService<ReportingWarranty> {

    /**
     *
     * @param reportingWarranty
     * @return
     */
    List<ReportingWarranty> getList(ReportingWarranty reportingWarranty);

    /**
     *
     * @param id
     * @return
     */
    ReportingWarranty get(String id);
}
