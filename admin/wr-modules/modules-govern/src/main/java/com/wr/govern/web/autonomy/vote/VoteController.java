package com.wr.govern.web.autonomy.vote;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.autonomy.vote.VoteService;
import com.wr.remote.govern.autonomy.vote.Vote;
import com.wr.remote.govern.autonomy.vote.VoteRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * 投票管理信息
 *
 * @author liubei
 * @createDate 2022/10/26
 */
@RestController
@RequestMapping("/busEstate")
public class VoteController extends BaseController {
    @Autowired
    private VoteService voteService;

    /**
     * 获取投票管理列表
     */
    @RequiresPermissions("govern:busEstate:list")
    @GetMapping("/list")
    public TableDataInfo list(Vote vote) {
        startPage();
        List<Vote> depts = voteService.selectBusEstateList(vote);
        return getDataTable(depts);
    }

    /**
     * 根据投票管理详细信息
     */
    @RequiresPermissions("govern:busEstate:query")
    @GetMapping(value = "/{voteId}")
    public AjaxResult getInfo(@PathVariable Long voteId) {
        return AjaxResult.success(voteService.selectBusEstateById(voteId));
    }

    /**
     * 投票结果业主详情
     */

    @GetMapping(value = "/listRecord")
    public TableDataInfo getListRecord(VoteRecord voteRecord) {
        startPage();
        List<VoteRecord> voteRecordList = voteService.getVoteRecord(voteRecord);
        return getDataTable(voteRecordList);
    }

    /**
     * 新增投票管理
     */
    @RequiresPermissions("govern:busEstate:add")
    @Log(title = "投票管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody Vote vote) throws ParseException {

        return toAjax(voteService.insertBusEstate(vote));
    }


    /**
     * 删除投票管理
     */
    @RequiresPermissions("govern:busEstate:delete")
    @Log(title = "删除投票管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{voteId}")
    public AjaxResult remove(@PathVariable("voteId") Long[] voteId) {
        return toAjax(voteService.deleteBusEstateById(voteId));
    }

    @GetMapping(value = "/bulletin")
    @RequiresPermissions("govern:busEstate:bulletin")
    public AjaxResult bulletin(Long voteId) {
        voteService.bulletin(voteId);
        return AjaxResult.success();
    }
}
