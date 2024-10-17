package com.wr.govern.service.home;


import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.govern.home.Census;


/**
 * 首页
 *
 * @Author: luSu
 * @Date: 2022-12-1 10:25:05
 * @Desc:首页服务层
 */
public interface HomePageService {

    /**
     * 获取首页数据
     *
     * @param census
     * @return
     */
    AjaxResult getParams(Census census);
}
