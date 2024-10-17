package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.application.serve.MeterRecord;
import com.wr.api.estate.service.application.serve.MeterRecordService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 17:00:12
 * @Desc: 抄表信息
 */
@RestController
@RequestMapping("/v2/meterRecord")
public class MeterRecordController extends BaseController {

    @Autowired
    private MeterRecordService meterRecordService;

    /**
     * 抄表列表
     * @param meterRecord
     * @return
     */
    @AuthRequired
    @GetMapping("/selectCord")
    public TableDataInfo selectRent(MeterRecord meterRecord){
        startPage();
        return getDataTable(meterRecordService.selectMeterCord(meterRecord));
    }

    /**
     * 添加抄表
     * @param meterRecord
     * @return
     */
    @ApiLog(title="抄表信息添加",businessType= BusinessType.INSERT)
    @AuthRequired
    @PostMapping("/insertCord")
    public AjaxResult insertCord(@RequestBody MeterRecord meterRecord) {
        return toAjax(meterRecordService.insertCord(meterRecord));
    }

    /**
     * 查询抄表详情
     * @param recordId 查询id
     * @return
     */
    @AuthRequired
    @GetMapping
    public AjaxResult meterRecordDetail(Long recordId) {
        return AjaxResult.success(meterRecordService.getMeterRecordDetail(recordId));
    }

    /**
     * 获取业主信息
     * @param ownerId 业主id
     * @return
     */
    @AuthRequired
    @GetMapping("/ownerDetail")
    public AjaxResult getOwnerDetail(Long ownerId) {
        return AjaxResult.success(meterRecordService.getOwnerDetail(ownerId));
    }

    /**
     * 获取楼栋集合
     * @param communityId 业主id
     * @return
     */
    @AuthRequired
    @GetMapping("/getBuilderList")
    public AjaxResult getBuilderList(Long communityId) {
        return AjaxResult.success(meterRecordService.getBuilderList(communityId));
    }

}
