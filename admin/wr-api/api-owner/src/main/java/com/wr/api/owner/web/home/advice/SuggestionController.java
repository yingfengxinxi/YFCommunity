package com.wr.api.owner.web.home.advice;

import com.wr.api.owner.entity.home.advice.Suggestion;
import com.wr.api.owner.service.home.advice.SuggestionService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/v2/suggestion")
public class SuggestionController extends BaseController {

    @Autowired
    private SuggestionService suggestionService;

    /**
     * 查询意见箱列表
     * @param suggestion
     * @return
     * @throws ParseException
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Suggestion suggestion) throws ParseException {
        if (ObjectUtils.allNull(suggestion.getAccountId())){
            return new TableDataInfo();
        }
        startPage();
        List<Suggestion> list = suggestionService.selectSuggestionList(suggestion);
        return getDataTable(list);
    }

    /**
     * 查询意见箱详情
     * @param suggestId
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/{suggestId}")
    public AjaxResult getHeadlineById(@PathVariable Long suggestId) {
        if (ObjectUtils.isEmpty(suggestId)) {
            return AjaxResult.error("参数不能为空");
        }
        Suggestion suggestion = suggestionService.getSuggestionById(suggestId);
        return AjaxResult.success(suggestion);
    }

    /**
     * 新增意见箱
     * @param suggestion
     * @return
     */

    @ApiLog(title="意见箱-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@RequestBody Suggestion suggestion) {
        Integer row = suggestionService.insertSuggestion(suggestion);
        return row > 0 ? AjaxResult.success("添加成功！") : AjaxResult.error("系统有误，请稍后重试！");
    }


}
