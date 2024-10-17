package com.wr.api.estate.web.application.info;

import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.service.application.info.OwnerService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:07:17
 * @Desc:
 */
@RestController
@RequestMapping("/v2/owner")
public class OwnerController extends BaseController {
    @Autowired
    private OwnerService ownerService;

    /**
     * 列表
     * @param ownerVo
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(OwnerVo ownerVo) {
        startPage();
        List<OwnerVo> list = ownerService.getList(ownerVo);
        return getDataTable(list);
    }

    /**
     * 详情
     * @param ownerVo
     * @return
     */
    @GetMapping("/detail")
    public AjaxResult getInfo(OwnerVo ownerVo) {
        return AjaxResult.success(ownerService.getDetail(ownerVo));
    }

    /**
     * 业主总数
     * @param communityId
     * @return
     */
    @GetMapping("/count/{communityId}")
    public AjaxResult getCount(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(ownerService.getOwnerCount(communityId));
    }
}
