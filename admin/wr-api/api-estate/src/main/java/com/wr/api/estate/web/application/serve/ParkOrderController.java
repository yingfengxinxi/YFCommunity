package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.service.application.serve.ParkOrderService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lvzy
 * @Date 2024/1/21 11:14
 */
@RestController
@RequestMapping("v2/parkOrder")
@Api(tags = "物业移动端-物业缴费管理")
public class ParkOrderController extends BaseController {

    @Autowired
    private ParkOrderService parkOrderService;


    /**
     * @param userId
     * @return
     */
    @GetMapping("getList")
    public TableDataInfo getList(@RequestParam("userId") Long userId) {

        return getDataTable(parkOrderService.getList(userId));
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("get")
    public AjaxResult get(@RequestParam("id") String id) {

        return AjaxResult.success(parkOrderService.get(id));
    }

}
