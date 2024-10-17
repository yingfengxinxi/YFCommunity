package com.wr.estate.web.work.homeorder;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.HomeRepair;
import com.wr.estate.entity.vo.HomeRepairVO;
import com.wr.estate.service.work.homeorder.HomeRepairService;
import com.wr.remote.domain.SysUser;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-05 09:16:19
 * @Desc: 居家维修类型 控制层
 */
@RestController
@RequestMapping("/work/homeRepair")
public class HomeRepairController extends BaseController {
    @Autowired
    private HomeRepairService homeRepairService;

    /**
     * 获取居家维修类型列表
     *
     * @param homeRepair
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("work:homeRepair:list")
    @GetMapping("/list")
    public TableDataInfo list(HomeRepair homeRepair) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        homeRepair.setEstateId(sysUser.getEstateId());
        startPage();
        List<HomeRepairVO> homeRepairAll = homeRepairService.getHomeRepairAll(homeRepair);
        return getDataTable(homeRepairAll);
    }

    /**
     * 获取居家维修类型详情信息
     * @param repairId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable Long repairId) {
        HomeRepairVO homeRepairById = homeRepairService.getHomeRepairById(repairId);
        return AjaxResult.success(homeRepairById);
    }

    /**
     * 添加类型
     * @param homeRepair
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("work:homeRepair:add")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody HomeRepair homeRepair) {
        if (UserConstants.NOT_UNIQUE.equals(homeRepairService.checkNameUnique(homeRepair))) {
            return AjaxResult.error("添加居家维修类型名称：'" + homeRepair.getRepairName() + "'失败，该类型名称已建立");
        }
        Integer num = homeRepairService.addHomeRepair(homeRepair);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 修改类型
     * @param homeRepair
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("work:homeRepair:edit")
    @PutMapping(value = "/edit")
    public AjaxResult edit(@RequestBody HomeRepair homeRepair) {
        if (UserConstants.NOT_UNIQUE.equals(homeRepairService.checkNameUnique(homeRepair))) {
            return AjaxResult.error("修改居家维修类型名称：'" + homeRepair.getRepairName() + "'失败，该类型名称已建立");
        }
        Integer num = homeRepairService.upHomeRepair(homeRepair);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 删除
     * @param repairIds
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("work:homeRepair:remove")
    @DeleteMapping(value = "/remove/{repairIds}")
    public AjaxResult remove(@PathVariable Long [] repairIds) {
        Integer num = homeRepairService.delHomeRepair(repairIds);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
