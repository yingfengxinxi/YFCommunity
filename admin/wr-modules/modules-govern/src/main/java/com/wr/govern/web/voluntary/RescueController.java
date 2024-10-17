package com.wr.govern.web.voluntary;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.govern.service.voluntary.RescueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-26 14:55:06
 * @Desc:
 */
@RequestMapping("rescue")
@RestController
public class RescueController extends BaseController {

    @Resource
    private RescueService rescueService;

    /**
     * 查询
     * @param params
     * @return
     */
    @GetMapping("searchList")
    public TableDataInfo searchList(@RequestParam Map<String,Object> params){

        if (StringUtils.isNotNull(params.get("dateTime"))){

            String time = String.valueOf(params.get("dateTime"));
            params.put("time",time.split(","));
        }
        startPage();
        return getDataTable(rescueService.searchList(params));
    }

}
