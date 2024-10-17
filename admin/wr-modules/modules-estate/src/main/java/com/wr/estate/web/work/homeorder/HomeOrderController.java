package com.wr.estate.web.work.homeorder;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.HomeOrder;
import com.wr.estate.entity.HomeRepair;
import com.wr.estate.entity.vo.HomeOrderVO;
import com.wr.estate.service.work.homeorder.HomeOrderService;
import com.wr.remote.domain.SysUser;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-05 17:55:52
 * @Desc: 居家维修工单 控制层
 */
@RestController
@RequestMapping("/work/homeOrder")
public class HomeOrderController extends BaseController {
    @Autowired
    private HomeOrderService homeOrderService;

    @RequiresPermissions("work:homeOrder:list")
    @GetMapping("/list")
    public TableDataInfo list(HomeOrderVO homeOrder) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        homeOrder.setEstateId(sysUser.getEstateId());
        startPage();
        List<HomeOrderVO> homeOrderAll = homeOrderService.getHomeOrderAll(homeOrder);
        return getDataTable(homeOrderAll);
    }

    /**
     * 获取居家维修类型
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/getHomeRepair")
    public AjaxResult getHomeRepair() {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return AjaxResult.error("系统错误，未获取到用户信息");
        }
        Long estateId = sysUser.getEstateId();
        List<HomeRepair> homeRepair = homeOrderService.getHomeRepair(estateId);
        return AjaxResult.success(homeRepair);
    }

    /**
     * 居家维修详情查看
     * @param orderId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("work:homeOrder:query")
    @GetMapping("/{orderId}")
    public AjaxResult getInfo(@PathVariable Long orderId) {
        HomeOrderVO homeOrderById = homeOrderService.getHomeOrderById(orderId);
        return AjaxResult.success(homeOrderById);
    }

    /**
     * 分配工单
     * @param homeOrder
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("work:homeOrder:distribution")
    @PutMapping("/distribution")
    public AjaxResult distribution(@RequestBody HomeOrder homeOrder) {
        Integer num = homeOrderService.distribution(homeOrder);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
