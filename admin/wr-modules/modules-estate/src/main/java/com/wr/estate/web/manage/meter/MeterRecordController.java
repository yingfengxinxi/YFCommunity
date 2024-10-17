package com.wr.estate.web.manage.meter;

import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.DictUtils;
import com.wr.estate.service.manage.meter.MeterRecordService;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.estate.manage.meter.RecordVo;
import com.wr.remote.estate.manage.meter.TreeSelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-25 15:20:29
 * @Desc: 抄表统计/能耗统计 控制层
 */
@RestController
@RequestMapping("/record")
public class MeterRecordController extends BaseController {

    @Autowired
    private MeterRecordService meterRecordService;


    /**
     * 水表列表
     *
     * @param recordVo
     * @return
     */
    @RequiresPermissions("business:record:list")
    @GetMapping("/list")
    public TableDataInfo getRoomInfo(RecordVo recordVo) {
        startPage();
        List<RecordVo> list = meterRecordService.getRoomList(recordVo, "0");
        return getDataTable(list);

    }

    /**
     * 电表列表
     *
     * @param recordVo
     * @return
     */
    @GetMapping("/list2")
    public TableDataInfo getRoomInfo2(RecordVo recordVo) {
        startPage();
        List<RecordVo> list = meterRecordService.getRoomList(recordVo, "1");
        return getDataTable(list);
    }

    /**
     * 燃气表列表
     *
     * @param recordVo
     * @return
     */
    @RequiresPermissions("business:record:list")
    @GetMapping("/list3")
    public TableDataInfo getRoomInfo3(RecordVo recordVo) {
        startPage();
        List<RecordVo> list = meterRecordService.getRoomList(recordVo, "2");
        return getDataTable(list);
    }

    /**
     * 水表导出
     *
     * @param response
     * @param recordVo
     * @throws IOException
     */
    @Log(title = "水表导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("business:record:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecordVo recordVo) throws IOException {
        List<RecordVo> list = meterRecordService.getRoomList(recordVo, "0");
        ExcelUtil<RecordVo> util = new ExcelUtil<RecordVo>(RecordVo.class);
        util.exportExcel(response, list, "数据");
    }

    /**
     * 电表导出
     *
     * @param response
     * @param recordVo
     * @throws IOException
     */
    @Log(title = "电表导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("business:record:export2")
    @PostMapping("/export2")
    public void export2(HttpServletResponse response, RecordVo recordVo) throws IOException {
        List<RecordVo> list = meterRecordService.getRoomList(recordVo, "1");
        ExcelUtil<RecordVo> util = new ExcelUtil<RecordVo>(RecordVo.class);
        util.exportExcel(response, list, "数据");
    }

    /**
     * 燃气表导出
     *
     * @param response
     * @param recordVo
     * @throws IOException
     */
    @Log(title = "天然气表导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("business:record:export3")
    @PostMapping("/export3")
    public void export3(HttpServletResponse response, RecordVo recordVo) throws IOException {
        List<RecordVo> list = meterRecordService.getRoomList(recordVo, "2");
        ExcelUtil<RecordVo> util = new ExcelUtil<RecordVo>(RecordVo.class);
        util.exportExcel(response, list, "数据");
    }

    /**
     * 房间树
     *
     * @param communityId
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getTreeSelects(Long communityId) {
        List<TreeSelectVo> tree = meterRecordService.getTreeSelects(communityId);
        return AjaxResult.success(tree);
    }

    /**
     * 能耗统计
     *
     * @param communityId
     * @return
     */
    @RequiresPermissions("business:record:sum")
    @GetMapping("/getRecordSum")
    public AjaxResult getRecordSum(Long communityId) {
        return meterRecordService.getRecordSum(communityId);
    }

}
