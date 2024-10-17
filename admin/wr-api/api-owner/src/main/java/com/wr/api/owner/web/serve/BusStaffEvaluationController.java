package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.serve.BusStaffEvaluation;
import com.wr.api.owner.service.serve.BusStaffEvaluationService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.manage.Staff;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
* @Author SUNk
* @Description 员工评价表
* @Date 14:42 2024/3/17
**/
@RestController
@RequestMapping("/busstaffevaluation")
public class BusStaffEvaluationController extends BaseController {
    @Resource
    private BusStaffEvaluationService busStaffEvaluationService;

   /**
   * @Author SUNk
   * @Description 保存员工评价表
   * @Date 14:43 2024/3/17
   * @Param [communityId, buildingId, unitId, ownerId]
   * @return com.wr.common.core.web.domain.AjaxResult
   **/
    @PostMapping("/saveBusStaffEvaluation")
    @Transactional
    public AjaxResult saveBusStaffEvaluation(@RequestBody BusStaffEvaluation busStaffEvaluation) {
        if(busStaffEvaluation == null){
            throw new ServiceException("获取评价信息失败，请稍后重试！");
        }

        busStaffEvaluation.setSatisfactionTime( new Date() );
        boolean save = busStaffEvaluationService.save( busStaffEvaluation );
        return AjaxResult.success(save?"评价成功":"评价失败，请稍后重试！");
    }

    /**
    * @Author SUNk
    * @Description 查询员工信息
    * @Date 15:05 2024/3/17
    * @Param [staffId]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping("/findOneBusStaff")
    public AjaxResult findOneBusStaff(@RequestParam Long staffId) {
        if(staffId == null){
            throw new ServiceException("获取员工ID失败，请稍后重试！");
        }

        Staff oneBusStaff = busStaffEvaluationService.findOneBusStaff( staffId );
        return AjaxResult.success(oneBusStaff);
    }

}
