package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.dto.ComAcDTO;
import com.wr.api.owner.entity.vo.serve.ComAcVo;
import com.wr.api.owner.service.serve.ComActivityService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-16 09:48:19
 * @Desc: 社区活动
 */
@RequestMapping("/v2/activity")
@RestController
public class ComActivityController extends BaseController {

    @Autowired
    private ComActivityService comActivityService;

    /**
     * 查询我参与的活动
     * @param ownerId
     * @param communityId
     * @return
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo getComActivityList(Long ownerId, Long communityId){
        startPage();
        //查询我参与的活动
        List<ComAcDTO> list = comActivityService.searchByCommunityId(ownerId,communityId);
        return getDataTable(list);
    }

    /**
     * 查询我参与的活动详情
     * @param comAcVo
     * @return
     */
    @GetMapping("/info")
    @AuthRequired
    public AjaxResult getComActivityInfo(ComAcVo comAcVo){
       ComAcVo info =  comActivityService.getComActivityInfo(comAcVo);
       return AjaxResult.success(info);
    }

    /**
     * 签到
     * @param comAcVo
     * @return
     */
    @PostMapping("/sign")
    @AuthRequired
    @ApiLog(title = "签到",businessType = BusinessType.UPDATE)
    public AjaxResult updateSignTime(@RequestBody ComAcVo comAcVo){
        return AjaxResult.success(comActivityService.updateSignTime(comAcVo));
    }
}
