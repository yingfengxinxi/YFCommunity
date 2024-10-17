package com.wr.estate.web.manage.contract;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.manage.contract.LeaseService;
import com.wr.remote.estate.manage.vo.contract.LeaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 租赁合同管理的controller
 */
@RestController
@RequestMapping("/lease")
public class LeaseController extends BaseController {

    @Autowired
    private LeaseService leaseService;

    /**
     * 查询租赁合同列表
     *
     * @param leaseVo
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:lease:list")
    public TableDataInfo list(LeaseVo leaseVo) throws ParseException {
        startPage();
        //调用接口获取内容
        List<LeaseVo> list = leaseService.getLeaseList(leaseVo);
        return getDataTable(list);
    }

    /**
     * 新增租赁合同
     */
    @PostMapping
    @RequiresPermissions("manage:lease:add")
    @Log(title = "新增租房", businessType = BusinessType.INSERT)
    public AjaxResult add(LeaseVo leaseVo) {
        //校验身份证是否存在
        if (leaseService.checkLeaseExist(leaseVo.getCardNo())) {
            return AjaxResult.error("身份证已存在!");
        }
        //校验手机号是否存在
        if (leaseService.checkLeaseExistByPhone(leaseVo.getTenantPhone())) {
            return AjaxResult.error("手机号已存在!");
        }
        //校验房屋是否已出租
        Integer ckeckRoom = leaseService.getCkeckRoom(leaseVo.getCommunityId(), leaseVo.getRoomId());
        if (ckeckRoom >= 1) {
            return AjaxResult.error("该房屋已出租!");
        }

        return toAjax(leaseService.leaseAdd(leaseVo));
    }


    /**
     * 删除租房合同
     */
    @DeleteMapping("/{leaseId}")
    @RequiresPermissions("manage:lease:remove")
    @Log(title = "删除租房", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] leaseId) {
        return toAjax(leaseService.deleteLeaseByIds(leaseId));
    }


    /**
     * 查询详情
     *
     * @param contractId
     * @return
     */
    @GetMapping(value = "/{contractId}")
    @RequiresPermissions("manage:lease:query")
    public AjaxResult getLeaseById(@PathVariable Long contractId) {
        LeaseVo leaseVo = leaseService.selectOneLeaseById(contractId);
        return AjaxResult.success(leaseVo);
    }

}
