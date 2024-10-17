package com.wr.govern.web.autonomy.research;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.autonomy.research.ResearchService;
import com.wr.remote.govern.autonomy.Research.Research;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 党建调研管理信息
 *
 * @author liubei
 * @createDate 2022/10/26
 */
@RestController
@RequestMapping("/research")
public class ResearchController extends BaseController {
    @Autowired
    private ResearchService researchService;

    /**
     * 党建调研列表
     */
    @RequiresPermissions("govern:research:list")
    @GetMapping("/list")
    public TableDataInfo list(Research research) {
        startPage();
        List<Research> depts = researchService.selectResearchList(research);
        return getDataTable(depts);
    }

    /**
     * 根据党建调研查询信息
     */
    @RequiresPermissions("govern:research:query")
    @GetMapping(value = "/{researchId}")
    public AjaxResult getInfo(@PathVariable Long researchId) {
        return AjaxResult.success(researchService.selectResearchById(researchId));
    }

    /**
     * 新增党建调研
     */
    @RequiresPermissions("govern:research:add")
    @Log(title = "新增党建调研", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody Research research) {

        return toAjax(researchService.insertResearch(research));
    }


    /**
     * 删除党建调研管理
     */
    @RequiresPermissions("govern:research:delete")
    @Log(title = "删除党建管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{researchId}")
    public AjaxResult remove(@PathVariable Long[] researchId) {
        return toAjax(researchService.deleteResearchById(researchId));
    }


    /**
     * 查询党建活动参与人员详细信息
     */
    @Log(title = "查询党建活动参与人员详细信息", businessType = BusinessType.OTHER)
    @PostMapping("/findListPartyMemberResearch")
    public AjaxResult findListPartyMemberResearch(@RequestBody Research research) {
        return researchService.findListPartyMemberResearch(research);
    }

    /**
     * 根据问题ID查询答题人列表
     */
    @Log(title = "根据问题ID查询答题人列表", businessType = BusinessType.OTHER)
    @GetMapping("/findListAccoutByQuestionId")
    public TableDataInfo findListAccoutByQuestionId(@RequestParam Long tableId) {
        startPage();
        List<Map<String, Object>> depts = researchService.findListAccoutByQuestionId(tableId);
        return getDataTable(depts);
    }
}
