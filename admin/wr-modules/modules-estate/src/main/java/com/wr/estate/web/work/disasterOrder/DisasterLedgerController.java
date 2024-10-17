package com.wr.estate.web.work.disasterOrder;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.work.disasterOrder.DisasterLedgerService;
import com.wr.remote.estate.base.BusDisaster;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-10-27 18:27:25
 * @Desc: 灾害报事类型
 */
@RestController
@RequestMapping("/disasterLedger")
public class DisasterLedgerController extends BaseController {
    @Autowired
    private DisasterLedgerService disasterLedgerService;

    /**
     * 分页查询灾害报事信息
     * @param busDisaster
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(BusDisaster busDisaster) {
        startPage();
        List<BusDisaster> list = disasterLedgerService.listDisasterLedger(busDisaster);
        return getDataTable(list);
    }

    /**
     * 添加灾害报事信息
     *
     * @param busDisaster
     * @return
     */
    @ApiOperation(value = "添加")
    @RequiresPermissions("work:disasterLedger:add")
    @Log(title = "添加灾害报事信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult addListAllOrder(@RequestBody BusDisaster busDisaster) {
        String username = SecurityUtils.getUsername();
        busDisaster.setCreateBy(username);
        return disasterLedgerService.insertBusDisaster(busDisaster);
    }


    /**
     * 删除灾害报事信息
     * @param disasterId
     * @return
     */
    @RequiresPermissions("work:disasterLedger:remove/{disasterId}")
    @DeleteMapping("/remove/{disasterId}")
    @Log(title = "删除灾害报事信息", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long disasterId) {
        Integer remove = disasterLedgerService.deleteDisasterId(disasterId);
        return AjaxResult.success(remove);
    }


    /**
     * 修改灾害报事信息
     *
     * @param busDisaster
     * @return 修改结果
     */
    @RequiresPermissions("work:disasterLedger:update")
    @Log(title = "修改灾害报事信息", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BusDisaster busDisaster) {

        return disasterLedgerService.updateBusDisaster(busDisaster);
    }

    /**
     * 批量导入灾害报事信息
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
//    @Log(title = "批量导入灾害报事信息", businessType = BusinessType.IMPORT)
//    @PostMapping("/importData")
//    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
//        ExcelUtil<BusDisaster> util = new ExcelUtil<BusDisaster>(BusDisaster.class);
//        List<BusDisaster> dataList = util.importExcel(file.getInputStream());
//        String operName = SecurityUtils.getUsername();
//        String message = disasterLedgerService.importData(dataList, updateSupport, operName);
//        return AjaxResult.success(message);
//    }

    @GetMapping("/getOrderType")
    public AjaxResult getOrderType(){
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        List<BusDisaster> list =disasterLedgerService.getOrderType(estateId);
        return AjaxResult.success(list);
    }
}
