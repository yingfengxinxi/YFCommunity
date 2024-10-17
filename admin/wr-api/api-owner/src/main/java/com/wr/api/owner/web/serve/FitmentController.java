package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.dto.FitmentDTO;
import com.wr.api.owner.entity.vo.serve.FitmentVo;
import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.api.owner.service.serve.FitmentService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 14:21:15
 * @Desc: 装修
 */
@RestController
@RequestMapping("/v2/fitment")
public class FitmentController extends BaseController {

    @Autowired
    private FitmentService fitmentService;

    /**
     * 查询装修列表
     * @param fitmentVo
     * @return
     */
    @EncryptResponse
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo getList(FitmentVo fitmentVo){
        startPage();
        List<FitmentDTO> list=fitmentService.getFitment(fitmentVo);
        return getDataTable(list);
    }

    /**
     * 新增装修
     * @param fitmentVo
     * @return
     */
    @PostMapping("/insert")
    @AuthRequired
    @ApiLog(title = "新增装修",businessType = BusinessType.INSERT)
    public AjaxResult insertFitment(@RequestBody FitmentVo fitmentVo){
        return AjaxResult.success(fitmentService.insertFitment(fitmentVo));
    }

    /**
     * 查询装修人员信息
     */
    @GetMapping("/getFitmentEr")
    @AuthRequired
    public AjaxResult getFitmentEr(FitmentVo fitmentVo){
        List<FitmentVo> list=fitmentService.getFitmentEr(fitmentVo);
        return AjaxResult.success(list);
    }

    /**
     * 查询装修详情
     * @param fitmentVo
     * @return
     */
    @GetMapping("/info")
    @AuthRequired
    public AjaxResult getListInfo(FitmentVo fitmentVo){
        List<FitmentVo> list=fitmentService.getFitmentInfo(fitmentVo);
        return AjaxResult.success(list);
    }

    /**
     * 我要延期
     * @param fitmentVo
     * @return
     */
    @PostMapping("/updatePostpone")
    @AuthRequired
    @ApiLog(title = "我要延期",businessType = BusinessType.UPDATE)
    public AjaxResult updatePostpone(@RequestBody FitmentVo fitmentVo){
        int data = fitmentService.updatePostpone(fitmentVo);
        return data > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 装修状态修改
     * @param fitmentVo
     * @return
     */
    @PostMapping("/updateState")
    @AuthRequired
    @ApiLog(title = "装修状态修改",businessType = BusinessType.UPDATE)
    public AjaxResult updateState(@RequestBody FitmentVo fitmentVo){
        int data = fitmentService.updateState(fitmentVo);
        return data > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 人员申请添加
     * @param fitmentVo
     * @return
     */
    @PostMapping("/insertHuman")
    @AuthRequired
    @ApiLog(title = "人员申请添加",businessType = BusinessType.INSERT)
    public AjaxResult insertHuman(@RequestBody FitmentVo fitmentVo){

        return AjaxResult.success(fitmentService.insertHuman(fitmentVo));
    }

    /**
     * 修改装修
     * @param fitmentVo
     * @return
     */
    @PostMapping("/updateAll")
    @AuthRequired
    @ApiLog(title = "修改装修",businessType = BusinessType.UPDATE)
    public AjaxResult updateAll(@Validated @RequestBody FitmentVo fitmentVo){
      int row =  fitmentService.updateAll(fitmentVo);
      return row > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 查询小区
     * @param roomsVo
     * @return
     */
    @GetMapping("/getMyRoomS")
    @AuthRequired
    public AjaxResult getMyRoomS(MyRoomsVo roomsVo){
        return AjaxResult.success(fitmentService.getMyRoomS(roomsVo.getOwnerId(),roomsVo.getCommunityId()));
    }

    /**
     * 获取业主手机号
     * @param communityId
     * @return
     */
    @GetMapping("/getEstatePhone")
    @AuthRequired
    public AjaxResult getEstatePhone(Long communityId){
        return AjaxResult.success(fitmentService.getEstatePhone(communityId)) ;
    }



}
