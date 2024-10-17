package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.dto.QuesAnswerDTO;
import com.wr.api.owner.entity.vo.serve.ApiResearchVo;
import com.wr.api.owner.service.serve.ResearchService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 党建调研
 * @Author: lvzy
 * @Date: 2022-11-15 18:01:00
 * @Desc:党建调研
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/research")
public class ResearchController extends BaseController {

    private final ResearchService researchService;

    @GetMapping("/newList")
//    @AuthRequired
//    @EncryptResponse
    public TableDataInfo newList(ApiResearchVo researchVo) {
        startPage();
        return getDataTable(researchService.newList(researchVo));
    }

    @GetMapping("/oldList")
//    @AuthRequired
//    @EncryptResponse
    public TableDataInfo oldList(ApiResearchVo researchVo) {
        startPage();
        return getDataTable(researchService.oldList(researchVo));
    }

    @GetMapping("/detail")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult detail(ApiResearchVo researchVo) {
        return researchService.detail(researchVo.getResearchId());
    }

    @GetMapping("/item")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult selectItem(ApiResearchVo researchVo) {
        return researchService.selectItem(researchVo.getResearchId());
    }

    @PostMapping("/submit")
    @ApiLog(title = "提交党建调研", businessType = BusinessType.INSERT)
    @AuthRequired
//    @EncryptResponse
    public AjaxResult submit(@RequestBody QuesAnswerDTO answerList) {
        return researchService.submit(answerList);
    }

    @GetMapping("/answer")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult answerDetail(ApiResearchVo researchVo) {
        return researchService.answerDetail(researchVo.getResearchId(),Long.valueOf( researchVo.getAccountId() ));
    }

    /**
     * @Author SUNk
     * @Description 查询党建调研列表
     * @Date 18:35 2024/3/13
     * @Param [apiResearchVo]
     * @return java.util.List<com.wr.api.owner.entity.vo.serve.ApiResearchVo>
     **/
    @PostMapping("/findListResearch")
    public TableDataInfo findListResearch(@RequestBody ApiResearchVo researchVo) {
        PageUtils.startPage( researchVo.getPageNum(), researchVo.getPageSize() );
        return getDataTable(researchService.findListResearch(researchVo));
    }
}
