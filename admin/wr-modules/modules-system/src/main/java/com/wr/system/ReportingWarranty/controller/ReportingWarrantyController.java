package com.wr.system.ReportingWarranty.controller;

import cn.hutool.core.map.MapUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.system.ReportingWarranty.service.ReportingWarrantyService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:57
 */
@RestController
@RequestMapping("/estateapi/reportingWarranty")
@Api(tags = "业主端-报事报修")
public class ReportingWarrantyController extends BaseController {

    @Autowired
    private ReportingWarrantyService reportingWarrantyService;


    //业主工单评价统计
    //展示对应不同小区报事报修工单的评价，一星，二星，三星，四星的评价数量
    @GetMapping("/getCount")
    public AjaxResult getCount(@RequestParam("communityId") Long communityId) {
        return AjaxResult.success( reportingWarrantyService.getcount( communityId ) );
    }

    /**
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Author SUNk
     * @Description 获取车流人流量统计
     * @Date 16:07 2024/4/17
     * @Param [map]
     **/
    @PostMapping("/getLogListMonthDay")
    public AjaxResult getLogListMonthDay(@RequestBody Map<String, Object> map) {
        if(MapUtil.getLong( map, "communityId" ) == null || StringUtils.isBlank( MapUtil.getStr( map, "year" ) ) || StringUtils.isBlank( MapUtil.getStr( map, "month" ) )){
            return AjaxResult.error( "参数错误" );
        }
        Map<String, List<Map<String, Object>>> maps = reportingWarrantyService.getLogList( map );
        return AjaxResult.success( maps );
    }

    /**
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Author SUNk
     * @Description 查询设备信息
     * @Date 16:23 2024/4/17
     * @Param [communityId]
     **/
    @GetMapping("/findListBusDoor")
    public TableDataInfo findListBusDoor(@RequestParam("communityId") Long communityId) {
        startPage();
        return getDataTable( reportingWarrantyService.findListBusDoor( communityId ) );
    }
}


