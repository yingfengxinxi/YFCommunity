package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.vo.application.serve.OlderDetailVO;
import com.wr.api.estate.entity.vo.application.serve.OlderVO;
import com.wr.api.estate.service.application.serve.OlderService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 13:21:03
 * @Desc: 老人档案控制层
 */
@RestController
@RequestMapping("/v2/older")
public class OlderController extends BaseController {

    @Autowired
    private OlderService olderService;

    /**
     * 查询老人档案列表
     * @param olderVO
     * @return
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo selectOlderList(OlderVO olderVO) {
        startPage();
        List<OlderVO> olderVOList = olderService.selectOlderList(olderVO);
        return getDataTable(olderVOList);
    }

    /**
     * 查询老人详情信息
     * @param olderId
     * @return
     */
    @AuthRequired
    @GetMapping("/{olderId}")
    public AjaxResult getOlderDetail(@PathVariable Long olderId) {
        OlderDetailVO olderDetail = olderService.getOlderDetail(olderId);
        return AjaxResult.success(olderDetail);
    }

    /**
     * 养老审核
     * @param olderVO
     * @return
     */
    @ApiLog(title="养老审核更改",businessType= BusinessType.UPDATE)
    @AuthRequired
    @GetMapping("/audit")
    public AjaxResult olderAudit(OlderVO olderVO) {
        return toAjax(olderService.olderAudit(olderVO));
    }
}
