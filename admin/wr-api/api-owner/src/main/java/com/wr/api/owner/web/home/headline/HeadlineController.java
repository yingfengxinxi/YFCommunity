package com.wr.api.owner.web.home.headline;

import java.text.ParseException;
import com.wr.api.owner.entity.home.headline.Headline;
import com.wr.api.owner.service.home.headline.HeadlineService;
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
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区头条控制层
 */
@RestController
@RequestMapping("/v2/headline")
public class HeadlineController extends BaseController {

    @Autowired
    private HeadlineService headlineService;


    /**
     * 获取头条列表
     * 备注：
     * 1.首页展示10条未结束最新记录，用户点击更多分页查询，降序排列未结束记录
     * 2.首页：图片标题，小区名，发布时间
     * @param headline
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo list(Headline headline) throws ParseException {
        startPage();
        //调用接口获取内容
        List<Headline> list = headlineService.getList(headline);
        return getDataTable(list);
    }



    /**
     * 头条详情（需要：小区id和头条id，头条是一对多）
     * @param headline
     * @return
     */
    @GetMapping(value = "/info")
    @AuthRequired
    public AjaxResult getHeadlineById(Headline headline) {
        Headline notice = headlineService.selectOneById(headline);
        return AjaxResult.success(notice);
    }



    /**
     * 查询根据发布时间最新的社区头条
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/newsHeadline")
    public AjaxResult getNewsHeadline(Headline headline) {
        Headline notice = headlineService.selectNews(headline);
        return AjaxResult.success(notice);
    }









}
