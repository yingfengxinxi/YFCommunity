package com.wr.api.owner.web.home.advice;

import com.wr.api.owner.entity.home.advice.Illegal;
import com.wr.api.owner.service.home.advice.IllegalService;
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
@RequestMapping("/v2/illegal")
public class IllegalController extends BaseController {

    @Autowired
    private IllegalService illegalService;

    /**
     * 查询违法举报列表
     * @param illegal
     * @return
     * @throws ParseException
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Illegal illegal) throws ParseException {
        if (ObjectUtils.allNull(illegal.getAccountId())){
            return new TableDataInfo();
        }
        startPage();
        List<Illegal> list = illegalService.selectIllegalList(illegal);
        return getDataTable(list);
    }

    /**
     * 查询违法举报详情
     * @param illegalId
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/{illegalId}")
    public AjaxResult getHeadlineById(@PathVariable Long illegalId) {
        if (ObjectUtils.isEmpty(illegalId)) {
            return AjaxResult.error("参数不能为空");
        }
        Illegal illegal = illegalService.getIllegalById(illegalId);
        return AjaxResult.success(illegal);
    }

    /**
     * 新增违法举报
     * @param illegal
     * @return
     */
    @ApiLog(title="违法举报-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @AuthRequired
    public AjaxResult add(@RequestBody Illegal illegal) {
        Integer row = illegalService.insertIllegal(illegal);
        return row > 0 ? AjaxResult.success() : AjaxResult.error();
    }


}
