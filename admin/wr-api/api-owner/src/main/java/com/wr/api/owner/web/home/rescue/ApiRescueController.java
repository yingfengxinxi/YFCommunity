package com.wr.api.owner.web.home.rescue;


import com.wr.api.owner.entity.home.rescue.ApiRescueUser;
import com.wr.api.owner.entity.home.rescue.vo.ApiRescueVo;
import com.wr.api.owner.service.home.rescue.ApiRescueService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
 * @Author: lusu
 * @Date: 2022/10/15 11:46
 * @Desc: 社区救助控制层
 */
@RestController
@RequestMapping("/v2/apiRescue")
public class ApiRescueController extends BaseController {

    @Autowired
    private ApiRescueService rescueService;


    /**
     * 获取社区救助列表-别人发布，我未参加
     * @param rescueVo
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo list(ApiRescueVo rescueVo) {
        if (ObjectUtils.allNull(rescueVo.getCommunityId()) || ObjectUtils.allNull(rescueVo.getUserPhone())){
            return new TableDataInfo();
        }
        startPage();
        //调用接口获取内容
        List<ApiRescueVo> list = rescueService.getList(rescueVo);
        return getDataTable(list);
    }







    /**
     * 获取社区救助详情
     * 【注意：为救助和已参与救助的信息显示的内容不一样，已参与救助信息详细写！该接口将所有信息查出，铺数据需留意】
     * @param rescueId
     * @return
     */
    @GetMapping(value = "/{rescueId}")
    @AuthRequired
    public AjaxResult getHeadlineById(@PathVariable Long rescueId) {
        ApiRescueVo notice = rescueService.selectOneById(rescueId);
        return AjaxResult.success(notice);
    }


    /**
     * 根据RescueId查志愿者列表
     * @param rescueId
     * @return
     */
    @GetMapping(value = "/getRescueUser/{rescueId}")
    @AuthRequired
    public AjaxResult getRescueUserById(@PathVariable Long rescueId) {
        List<ApiRescueUser> user = rescueService.getRescueUserById(rescueId);
        return AjaxResult.success(user);
    }


    /**
     * 新增志愿者——报名参与救助后新增
     * @param apiRescueUser
     * @return
     */
    @PostMapping("/add")
    @AuthRequired
    @Log(title = "新增", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody ApiRescueUser apiRescueUser) {
        Integer inte = rescueService.add(apiRescueUser);
        return inte > 0 ? AjaxResult.success("参与成功") : AjaxResult.error("参与失败");
    }





    /**
     * 获取社区救助列表-别人发布，我已参加
     * （根据关联表中的手机号， 获取当前用户参与的所有已参与救助的列表！）
     * @param rescueVo
     * @return
     * @throws ParseException
     */
    @GetMapping("/joinRescueList")
    @AuthRequired
    public TableDataInfo joinRescueList(ApiRescueVo rescueVo) {
        if (ObjectUtils.allNull(rescueVo.getCommunityId()) || ObjectUtils.allNull(rescueVo.getUserPhone())){
            return new TableDataInfo();
        }
        startPage();
        //调用接口获取内容
        List<ApiRescueVo> list = rescueService.getJoinRescueList(rescueVo);
        return getDataTable(list);
    }
}
