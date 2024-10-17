package com.wr.estate.web;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.SuggestService;
import com.wr.remote.estate.custom.vo.SuggestListVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 荀彧
 * @Date: 2022-10-12 14:10:01
 * @Desc: 意见箱controller
 */
@RestController
@RequestMapping("/suggest")
public class SuggestController extends BaseController {


    @Resource
    private SuggestService suggestService;

    /**
     * 意见列表
     * @param suggestListVO 查询参数
     * @return 分页数据
     */
    @GetMapping("/getList")
    @PreAuthorize("@ss.hasPermi('bussiness:suggestion:list')")
    public TableDataInfo getList(SuggestListVO suggestListVO){
        startPage();
        List<SuggestListVO> list = suggestService.getSuggestList(suggestListVO);
        return getDataTable(list);
    }

    /**
     * 根据主键查询
     * @return  查询的数据
     */
    @GetMapping("/getDetail/{suggestId}")
    @PreAuthorize("@ss.hasPermi('bussiness:suggestion:list')")
    public AjaxResult getComplanintById(@PathVariable("suggestId") Long suggestId){
        if (null == suggestId){
            return AjaxResult.error("系统错误，请联系管理员。");
        }
        return AjaxResult.success(suggestService.getSuggestDeatilById(suggestId));
    }
}
