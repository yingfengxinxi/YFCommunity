package com.wr.api.owner.web.home.memberAct;


import com.wr.api.owner.entity.home.memberAct.ApiMemberAct;
import com.wr.api.owner.entity.home.memberAct.ApiMemberJoin;
import com.wr.api.owner.service.home.memberAct.ApiMemberActService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 党建活动控制层
 */
@RestController
@RequestMapping("/v2/apiMemberAct")
public class ApiMemberActController extends BaseController {

    @Autowired
    private ApiMemberActService apiMemberActService;


    /**
     * 查列表
     * @param apiMemberAct
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo list(ApiMemberAct apiMemberAct) throws ParseException {
        startPage();
        //调用接口获取内容
        List<ApiMemberAct> list = apiMemberActService.getList(apiMemberAct);
        return getDataTable(list);
    }


    /**
     * 查党建活动 详情
     * 判断用户是否已参与活动：当前查询的党建活动如果member_id字段为空， 则说明当前党员没有参与该活动， 否则已参加！
     * @param apiMemberAct
     * @return
     */
        @GetMapping(value = "/info")
        @AuthRequired
    public AjaxResult getInfo( ApiMemberAct apiMemberAct ) {
        ApiMemberAct memberAct = apiMemberActService.getInfo(apiMemberAct);
        return AjaxResult.success(memberAct);
    }


    /**
     * 报名党建活动(当前用户是否是党员， 是否已参加该活动)
     * @param apiMemberJoin
     * @return
     */
    @PostMapping("/addMemberJoin")
    @AuthRequired
    @Log(title = "新增", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody ApiMemberJoin apiMemberJoin) {
        Integer inte = apiMemberActService.add(apiMemberJoin);
        return inte > 0 ? AjaxResult.success("报名成功") : AjaxResult.error("报名失败");
    }



    /**
     * 用户参与状态
     * @param apiMemberJoin
     * @return
     */
    @GetMapping(value = "/numberStatus")
    @AuthRequired
    public AjaxResult getNumberStatus( ApiMemberJoin apiMemberJoin) {
        AjaxResult ajaxResult = new AjaxResult();
        ApiMemberJoin ownerStatus = apiMemberActService.getOwnerStatus(apiMemberJoin);
        if(ownerStatus!=null){
           ajaxResult.put("joinStatus",1); //已参与
       }else{
           ajaxResult.put("joinStatus",0); //未参与
       }
        return AjaxResult.success(ajaxResult);
    }



}
