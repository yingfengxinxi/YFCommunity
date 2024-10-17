package com.wr.estate.web.financial;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.financial.CashApplyService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.financial.EstateCash;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-12 14:46:07
 * @Desc:
 */

@RestController
@RequestMapping("/cashapply")
public class CashApplyContorller extends BaseController {

    @Autowired
    private CashApplyService cashApplyService;

    /**
     * 列表
     *
     * @param
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("business:cashApply:check")
    public TableDataInfo list(EstateCash estateCash) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        estateCash.setEstateId(estateId);
        startPage();
        List<EstateCash> list = cashApplyService.selectList(estateCash);
        return getDataTable(list);
    }

    /**
     * 通过年份查询金额
     */
    @PostMapping(value = "/getInfoById")
    public AjaxResult getInfoById(@RequestBody EstateCash estateCash) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String nowyear = sdf.format(date);
        if (null == estateCash.getYearVo()) {
            estateCash.setYearVo(Long.valueOf(nowyear));
        }
        // 今年累计
        BigDecimal nowcount = cashApplyService.cashCountByTime(estateCash.getYearVo().toString(), estateCash.getCommunityId());
        if (null == nowcount) {
            estateCash.setCashCountNow(BigDecimal.valueOf(0));
        } else {
            estateCash.setCashCountNow(nowcount);
        }
        // 去年累计
        Long lastyear = Long.valueOf(nowyear) - 1;
        BigDecimal lastyearcount = cashApplyService.cashCountByTime(lastyear.toString(), estateCash.getCommunityId());
        if (null == lastyearcount) {
            estateCash.setCashCountLast(BigDecimal.valueOf(0));
        } else {
            estateCash.setCashCountLast(lastyearcount);
        }

        // 总的累计
        BigDecimal all = cashApplyService.allCashCount(estateCash);
        if (null == all) {
            estateCash.setCashCount(BigDecimal.valueOf(0));
        } else {
            estateCash.setCashCount(all);
        }
        return AjaxResult.success(estateCash);
    }

    /**
     * 列表
     *
     * @param
     * @return
     */
    @GetMapping("/ownerVoteList")
//    @RequiresPermissions("business:activitetype:list")
    public AjaxResult ownerVoteList(EstateCash estateCash) {
        AjaxResult ajaxResult = new AjaxResult();
        List<EstateCash> list = cashApplyService.ownerVoteList(estateCash);
        ajaxResult.put("list", list);
        return ajaxResult;
    }

    /**
     * 查询投票详情
     */
    @GetMapping(value = "/voteInfoById/{voteId}")
    public AjaxResult voteInfoById(@PathVariable("voteId") Long voteId) {
        return AjaxResult.success(cashApplyService.voteInfoById(voteId));
    }

    /**
     * 修改数据
     *
     * @param estateCash 实体
     * @return 修改结果
     */
    @Log(title = "申请修改数据", businessType = BusinessType.UPDATE)
    @RequiresPermissions("business:cashApply:pass")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody EstateCash estateCash) {
        return AjaxResult.success(cashApplyService.updateApply(estateCash));
    }

    /**
     * 导出
     */
    @PreAuthorize("@ss.hasPermi('business:cashApply:upload')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, EstateCash estateCash) {
        List<EstateCash> list = cashApplyService.ownerVoteList(estateCash);
        ExcelUtil<EstateCash> util = new ExcelUtil<EstateCash>(EstateCash.class);
        util.exportExcel(response, list, "投票记录数据");
    }

    /**
     * 发布公告
     *
     * @param voteId 基金id
     * @return
     */
    @GetMapping("/bulletin")
    @PreAuthorize("@ss.hasPermi('business:cashApply:bulletin')")
    public AjaxResult export(Long voteId) {
        cashApplyService.bulletin(voteId);
        return AjaxResult.success();
    }

}
