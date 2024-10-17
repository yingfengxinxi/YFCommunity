package com.wr.estate.web;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.IllegalService;
import com.wr.remote.estate.complaint.report.BusIllegal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 违法举报Controller
 * @author 荀彧
 * @date 2022-10-13
 */
@RestController
@RequestMapping("/Illegal")
public class IllegalComtroller extends BaseController {


    @Resource
    private IllegalService illegalService;

    /**
     * 违法举报列表
     * @param busIllegal 查询参数
     * @return 分页数据
     */
    @GetMapping("/getList")
    @PreAuthorize("@ss.hasPermi('bussiness:report:list')")
    public TableDataInfo getList(BusIllegal busIllegal){
        startPage();
        List<BusIllegal> list = illegalService.pageList(busIllegal);
        return getDataTable(list);
    }

    /**
     * 根据ID查询违法举报详情
     * @return
     */
    @GetMapping("/get/{illegalId}")
    @PreAuthorize("@ss.hasPermi('bussiness:report:list')")
    public AjaxResult getList(@PathVariable("illegalId") Integer illegalId){
        if (null == illegalId){
            return AjaxResult.error("查询无此详情");
        }
        BusIllegal res = illegalService.load(illegalId.intValue());
        return AjaxResult.success(res);
    }
}
