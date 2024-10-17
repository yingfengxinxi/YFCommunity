package com.wr.estate.mapper.reportingWarranty;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.domain.ReportingWarranty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/31 21:06
 */
public interface ReportingWarrantyMapper extends BaseMapper<ReportingWarranty> {

    /**
     * @param reportingWarranty
     * @return
     */
    List<ReportingWarranty> getList(ReportingWarranty reportingWarranty);


    /**
     * @param id
     * @return
     */
    ReportingWarranty get(@Param("id") String id);
}
