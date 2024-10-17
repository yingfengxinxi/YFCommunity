package com.wr.govern.web.manage;

import com.google.common.collect.Lists;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.manage.PathService;
import com.wr.remote.govern.manage.VehicleLog;
import com.wr.remote.govern.manage.vo.UserDetailLogVO;
import com.wr.remote.govern.manage.vo.UserLogVO;
import com.wr.remote.govern.manage.vo.VehicleLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 16:39:56
 * @Desc: 轨迹管理控制层
 */
@RequestMapping("/path")
@RestController
public class PathController extends BaseController {

    @Autowired
    private PathService pathService;

    /**
     * 获取人员轨迹集合
     * @param userLogVO
     * @return
     */
    @RequiresPermissions("govern:path:list")
    @GetMapping("/list")
    public TableDataInfo getList(UserLogVO userLogVO) {
        Map<String, Object> result = pathService.selectUserLogList(userLogVO);
        if (result == null) {
            return getDataTable(Lists.newArrayList());
        }
        List<Map<String, Object>> listResult=new ArrayList<>();
        listResult.add(result);
        return getDataTable(listResult);
    }

    /**
     * 获取人员轨迹详情
     * @param idCard
     * @param currentTableName
     * @return
     */
    @RequiresPermissions("govern:path:list")
    @GetMapping
    public AjaxResult getDetail(@RequestParam(name = "idCard") String idCard,
                                @RequestParam(name = "currentTableName") String currentTableName,
                                @RequestParam(name = "logId") String logId) {
        UserDetailLogVO userLogVO = pathService.getUserLogDetail(idCard, currentTableName,logId);
        if(userLogVO==null){
            return AjaxResult.success(Lists.newArrayList());
        }
        return AjaxResult.success(userLogVO);
    }

    /**
     * 获取车辆轨迹列表
     * @param vehicleLog
     * @return
     */
    @RequiresPermissions("govern:path:list")
    @GetMapping("/vehicleList")
    public TableDataInfo vehicleList(VehicleLogVO vehicleLog) {
        Map<String,Object> result = pathService.selectVehicleLogList(vehicleLog);
        if (result == null) {
            return getDataTable(Lists.newArrayList());
        }
        List<Map<String, Object>> listResult=new ArrayList<>();
        listResult.add(result);
        return getDataTable(listResult);
    }

    /**
     * 获取车辆进出详情
     * @param plateNo
     * @param currentTableName
     * @return
     */
    @RequiresPermissions("govern:path:list")
    @GetMapping("/getVehicleDetail")
    public AjaxResult getVehicleDetail(@RequestParam(name = "plateNo") String plateNo,
                                       @RequestParam(name = "currentTableName") String currentTableName) {
        VehicleLogVO vehicleLogVO = pathService.getVehicleLogDetail(plateNo, currentTableName);
        return AjaxResult.success(vehicleLogVO);
    }

}
