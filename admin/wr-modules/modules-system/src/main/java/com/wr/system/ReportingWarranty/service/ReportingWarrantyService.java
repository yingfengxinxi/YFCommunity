package com.wr.system.ReportingWarranty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.system.ReportingWarranty.domain.ReportingWarranty;
import com.wr.system.business.domain.HomeScoreVO;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:56
 */
public interface ReportingWarrantyService extends IService<ReportingWarranty> {

    /**
     * @param reportingWarranty
     * @return
     */
    List<ReportingWarranty> getList(ReportingWarranty reportingWarranty);

    /**
     * @param id
     * @return
     */
    ReportingWarranty get(String id);

    HomeScoreVO getcount(Long communityId);

    /**
     * 每日人行/车行
     *
     * @param map 小区id
     * @return 每日人行/车行
     */
    public Map<String, List<Map<String, Object>>> getLogList(Map<String, Object> map);

    /**
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Author SUNk
     * @Description 查询设备信息
     * @Date 16:23 2024/4/17
     * @Param [communityId]
     **/
    public List<Map<String, Object>> findListBusDoor(Long communityId);

}
