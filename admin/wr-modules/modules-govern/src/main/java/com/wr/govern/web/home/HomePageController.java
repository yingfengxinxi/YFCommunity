package com.wr.govern.web.home;


import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.govern.service.home.HomePageService;
import com.wr.remote.govern.home.Census;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @Author: luSu
 * @Date: 2022-12-1 10:25:05
 * @Desc:首页控制层
 */
@RestController
@RequestMapping("/homePage")
public class HomePageController extends BaseController {

    @Autowired
    private HomePageService homePageService;

    /**
     * 获取首页数据
     *
     * @param census
     * @return
     */
    @PostMapping("/getParams")
    public AjaxResult getParams(@RequestBody Census census) {
        return homePageService.getParams(census);
    }
}
