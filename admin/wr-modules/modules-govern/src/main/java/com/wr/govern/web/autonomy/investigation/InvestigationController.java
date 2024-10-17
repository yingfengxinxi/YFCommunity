package com.wr.govern.web.autonomy.investigation;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.autonomy.investigation.InvestigationService;
import com.wr.remote.govern.autonomy.investigation.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * 问卷调查管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
@RestController
@RequestMapping("/investigation")
public class InvestigationController extends BaseController {
    @Autowired
    private InvestigationService investigationService;

    /**
     * 获取问卷调查管理列表
     */
    @RequiresPermissions("govern:investigation:list")
    @GetMapping("/list")
    public TableDataInfo list(Questionnaire questionnaire) {
        startPage();
        List<Questionnaire> depts = investigationService.selectQuestionnaireList(questionnaire);
        return getDataTable(depts);
    }

    /**
     * 根据问卷调查详细信息
     */
    @RequiresPermissions("govern:investigation:query")
    @GetMapping(value = "/{questionnaireId}")
    public AjaxResult getInfo(@PathVariable Long questionnaireId) {
        return AjaxResult.success(investigationService.selectQuestionnaireById(questionnaireId));
    }

    /**
     * 新增问卷调查
     * */
    @RequiresPermissions("govern:investigation:add")
    @Log(title = "问卷调查管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody Questionnaire questionnaire) throws ParseException {

        return toAjax(investigationService.insertQuestionnaire(questionnaire));
    }


    /**
     * 删除投票管理
     */
    @RequiresPermissions("govern:investigation:delete")
    @Log(title = "删除问卷调查", businessType = BusinessType.DELETE)
    @DeleteMapping("/{questionnaireId}")
    public AjaxResult remove(@PathVariable Long[] questionnaireId) {
        return toAjax(investigationService.deleteQuestionnaireById(questionnaireId));
    }
}
