package com.wr.estate.web.manage.contract;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.manage.contract.BuyerService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.manage.contract.Estate;
import com.wr.remote.estate.manage.vo.contract.BuyerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
 * @Author: lusu
 * @Date: 2022/10/17 14:46
 * @Desc: 采购合同
 */
@RestController
@RequestMapping("/buyer")
public class BuyerController extends BaseController {

    @Autowired
    private BuyerService buyerService;


    /**
     * 采购合同列表获取
     * @param buyerVo
     * @return
     * @throws
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:buyer:list")
    public TableDataInfo list(BuyerVo buyerVo) throws ParseException {
        startPage();
        //调用接口获取内容
        List<BuyerVo> list = buyerService.getBuyerList(buyerVo);
        return getDataTable(list);
    }


    /**
     * 新增采购
     * @param buyerVo
     * @return
     */
    @PostMapping
    @RequiresPermissions("manage:buyer:add")
    @Log(title = "新增采购", businessType = BusinessType.INSERT)
    public AjaxResult add(BuyerVo buyerVo) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = buyerService.add(buyerVo);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }


    /**
     * 获取物业列表
     * @return
     */
    @GetMapping("/estateList")
    public AjaxResult getEstateList() {
        List<Estate> list = buyerService.getEstateList();
        return AjaxResult.success(list);
    }

    /**
     * 获取员工列表
     *
     * @return
     */
    @GetMapping("/staffList/{estateId}")
    public AjaxResult getStaffList(@PathVariable("estateId") Long estateId) {

        List<Staff> staffList = buyerService.getStaffList(estateId);
        return AjaxResult.success(staffList);
    }

    /**
     * 删除采购合同
     */
    @DeleteMapping("/{procurementId}")
    @RequiresPermissions("manage:buyer:remove")
    @Log(title = "删除采购", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] procurementId) {
        return toAjax(buyerService.deleteBuyerByIds(procurementId));
    }


    /**
     * 查询详情
     * @param procurementId
     * @return
     */
    @GetMapping(value = "/{procurementId}")
    @RequiresPermissions("manage:buyer:query")
    public AjaxResult getBuyerById(@PathVariable Long procurementId) {
        BuyerVo leaseVo = buyerService.getBuyerById(procurementId);
        return AjaxResult.success(leaseVo);
    }


}
