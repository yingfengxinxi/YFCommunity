package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.dto.VenueDTO;
import com.wr.api.owner.entity.serve.StadiumPoint;
import com.wr.api.owner.entity.vo.serve.StadiumVo;
import com.wr.api.owner.service.serve.StadiumService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 11:20:08
 * @Desc: 场馆
 */
@RequestMapping("/v2/stadium")
@RestController
public class StadiumController extends BaseController {
    @Autowired
    private StadiumService stadiumService;

    /**
     * 查询本小区的活动场馆
     * @param stadiumVo
     * @return
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo getStadiumList(StadiumVo stadiumVo){
        if (ObjectUtils.isEmpty(stadiumVo)) {
            return new TableDataInfo();
        }
        startPage();
        List<VenueDTO> stadiumVos =  stadiumService.getStadiumList(stadiumVo);
        return getDataTable(stadiumVos);
    }

    /**
     * 查询本小区的活动场馆详情
     * @param stadiumVo
     * @return
     */
    @GetMapping("/info")
    @AuthRequired
    public AjaxResult getStadiumInfo(StadiumVo stadiumVo){
        List<StadiumVo> stadiumVos =  stadiumService.getStadiumInfo(stadiumVo);
        return AjaxResult.success(stadiumVos);
    }


    /**
     * 预约场馆
     * @param stadiumPoint
     * @return
     */
    @PostMapping("/insert")
    @AuthRequired
    @ApiLog(title = "新增预约",businessType = BusinessType.INSERT)
    public AjaxResult insertAppoint(@RequestBody StadiumPoint stadiumPoint)
    {
        return AjaxResult.success(stadiumService.insertAppoint(stadiumPoint));
    }

    /**
     * 查询我得预约
     * @param stadiumVo
     * @return
     */
    @GetMapping("/myAppointInfo")
    @AuthRequired
    public TableDataInfo myAppointInfo(StadiumVo stadiumVo){
        if (ObjectUtils.isEmpty(stadiumVo)) {
            return new TableDataInfo();
        }
        startPage();
        List<VenueDTO> stadiumVos =  stadiumService.getMyAppointInfo(stadiumVo);
        return getDataTable(stadiumVos);
    }

    /**
     * 对于预约申请进行修改
     * @param stadiumPoint
     * @return
     */
    @PostMapping("/update")
    @AuthRequired
    @ApiLog(title = "修改预约",businessType = BusinessType.UPDATE)
    public AjaxResult updateStatus(@RequestBody StadiumPoint stadiumPoint){
        return AjaxResult.success(stadiumService.updateStatus(stadiumPoint));
    }

    /**
     * 查询本小区的活动类型
     * @param communityId
     * @return
     */
    @AuthRequired
    @GetMapping("/getTypeName")
    public AjaxResult getTypeName(Long communityId){
        return AjaxResult.success(stadiumService.getTypeName(communityId));
    }

    /**
     * 根据小区查询物业id并查询场馆规则
     * @param communityId
     * @return
     */
    @AuthRequired
    @GetMapping("/getRule")
    public AjaxResult getRule(Long communityId){
        return AjaxResult.success(stadiumService.getRule(communityId));
    }


    /**
     * 查询我得预约详细信息
     * @param communityId
     * @param venueId
     * @param cardNo
     * @return
     */
    @AuthRequired
    @GetMapping("/getMyInfo")
    public AjaxResult getMyInfo(Long communityId,Long venueId,String cardNo, Long appointId){
        return AjaxResult.success(stadiumService.getMyInfo(communityId,venueId,cardNo,appointId));
    }


}
