package com.wr.api.estate.web.application.info;

import com.wr.api.estate.entity.vo.LiaisonVo;
import com.wr.api.estate.entity.vo.application.info.VisitLogVo;
import com.wr.api.estate.mapper.application.TableMapper;
import com.wr.api.estate.service.application.info.VisitVoService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:07:17
 * @Desc:
 */
@RestController
@RequestMapping("/v2/visitVo")
@Api(tags = "物业移动端-访客信息【弃用】")
public class VisitVoController extends BaseController {
    @Autowired
    private VisitVoService visitVoService;


    /**
     * 访客列表
     *
     * @param liaisonVo
     * @return
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo getVisitPageList(LiaisonVo liaisonVo) {
        startPage();
        //调用接口获取内容
        List<LiaisonVo> list = visitVoService.getVisitPageList(liaisonVo);
        return getDataTable(list);
    }

    /**
     * 访客详情
     *
     * @param liaisonId
     * @return
     */
    @GetMapping("/getVisitorById")
    @AuthRequired
    @ApiOperation("详情")
    public AjaxResult getVisitorById(@RequestParam("liaisonId") Long liaisonId) {
        //调用接口获取内容
        return AjaxResult.success(visitVoService.getVisitorById(liaisonId));
    }


    /**
     * 拜访列表
     *
     * @param visitLogVo
     * @return
     */
    @AuthRequired
    @GetMapping("/listDetail")
    public AjaxResult getInfo(VisitLogVo visitLogVo) {
        return AjaxResult.success(visitVoService.getVisitList(visitLogVo));
    }

    /**
     * 今日访客总数
     *
     * @param visitLogVo
     * @return
     */
    @AuthRequired
    @GetMapping("/count")
    public AjaxResult getCount(LiaisonVo liaisonVo) {
        int size = visitVoService.getVisitPageList(liaisonVo).size();
        return AjaxResult.success(size);
    }
}
