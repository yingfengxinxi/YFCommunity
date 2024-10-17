package com.wr.estate.web.financial;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.financial.EstateCashService;
import com.wr.remote.estate.financial.EstateCash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-12 14:46:07
 * @Desc:
 */

@RestController
@RequestMapping("/estateCash")
public class EstateCashContorller extends BaseController {

    @Autowired
    private EstateCashService estateCashService;

    /**
     * 列表
     * @param
     * @return
     */
    @GetMapping("/list")
//    @RequiresPermissions("business:estateCash:list")
    public TableDataInfo list(EstateCash estateCash){
        startPage();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String nowyear = sdf.format(date);
        if(null == estateCash.getPayYear()){
            estateCash.setPayYear(Long.valueOf(nowyear));
        }

        List<EstateCash> estateCashes = estateCashService.selectbuildingList(estateCash);
        return getDataTable(estateCashes);
    }
    /**
     * 缴费业主列表
     * @param
     * @return
     */
    @GetMapping("/ownerList")
//    @RequiresPermissions("business:activitetype:list")
    public TableDataInfo ownerList(EstateCash estateCash){
        startPage();
        List<EstateCash> list = estateCashService.selectbuildingOwnerList(estateCash);
        return getDataTable(list);
    }
    /**
     * 缴费业主列表
     * @param
     * @return
     */
    @GetMapping("/payList")
//    @RequiresPermissions("business:activitetype:list")
    public TableDataInfo payList(EstateCash estateCash){
        startPage();
        List<EstateCash> list = estateCashService.selectOwnerPayList(estateCash);
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     */
    @PostMapping(value = "/getInfoById")
    public AjaxResult getInfoById(@RequestBody EstateCash estateCash) {
        return AjaxResult.success(estateCashService.selectPayInfoById(estateCash));
    }

    /**
     * 导出
     */
    @PreAuthorize("@ss.hasPermi('system:attendanceGroup:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, EstateCash estateCash) {
        List<EstateCash> list = estateCashService.selectbuildingOwnerList(estateCash);
        ExcelUtil<EstateCash> util = new ExcelUtil<EstateCash>(EstateCash.class);
        util.exportExcel(response, list, "投票记录数据");
    }
}
