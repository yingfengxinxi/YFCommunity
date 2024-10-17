package com.wr.api.owner.web.home.activity;


import com.wr.api.owner.entity.home.activity.ApiActivity;
import com.wr.api.owner.entity.home.activity.ApiActivityOwner;
import com.wr.api.owner.entity.home.activity.vo.apiActivityVo;
import com.wr.api.owner.entity.home.notice.Invest;
import com.wr.api.owner.entity.home.notice.Notice;
import com.wr.api.owner.entity.home.rescue.ApiRescueUser;
import com.wr.api.owner.service.home.activity.ApiActivityService;
import com.wr.api.owner.service.home.notice.InvestService;
import com.wr.api.owner.service.home.notice.NoticeService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.remote.aiot.CloudAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区活动控制层
 */
@RestController
@RequestMapping("/v2/apiActivity")
public class ApiActivityController extends BaseController {

    @Autowired
    private ApiActivityService apiActivityService;


    /**
     * 查列表
     * @param apiActivity
     * @return
     */
    @GetMapping("/list")
//    @AuthRequired
    public TableDataInfo list(ApiActivity apiActivity) {
        startPage();
        //调用接口获取内容
        List<ApiActivity> list = apiActivityService.getList(apiActivity);
        return getDataTable(list);
    }


    /**
     * 查社区活动 详情
     * @param apiActivity
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/info")
    public AjaxResult getInfo( ApiActivity apiActivity ) {
        AjaxResult ajaxResult = new AjaxResult();

        apiActivityVo activity = apiActivityService.getInfo(apiActivity);
        // 物资列表
        List<ApiActivity> supplyList = apiActivityService.selectJoinSupplyList(apiActivity.getActivityId());

        //判断是否是志愿者活动
       if(activity!=null && "2".equals(apiActivity.getTableStatus())){
           //检测志愿者活动状态
               if( activity.getEndTime().compareTo(new Date())>0){  //截止时间>当前时间  可报名
                   ajaxResult.put("ActivityJoinStatus",0); //可参与
                   ajaxResult.put("data",activity);
                   //查询有用户是否参与活动
                 int i =  apiActivityService.checkVoluntaryIsJoin(apiActivity);
                 if( i>0 ){
                     ajaxResult.put("joinStatus",1);
                 }else{
                     ajaxResult.put("joinStatus",0);
                 }
                   return AjaxResult.success(ajaxResult);
               }else{
                   ajaxResult.put("ActivityJoinStatus",1); //不可参与
                   ajaxResult.put("data",activity);
                   return AjaxResult.success(ajaxResult);
               }
       }
       //否则，就是社区活动
        ajaxResult.put("data",activity);
        // 物资列表
        ajaxResult.put("supplyList",supplyList);
        return AjaxResult.success(ajaxResult);
    }


    /**
     * 报名
     * @param apiActivityOwner
     * @return
     */
    @PostMapping("/JoinActivity")
    @AuthRequired
    @Log(title = "新增", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody ApiActivityOwner apiActivityOwner) {
        Integer inte = apiActivityService.add(apiActivityOwner);
        return inte > 0 ? AjaxResult.success("报名成功") : AjaxResult.error("报名失败");
    }


    /**
     * 签到
     * @param apiActivityOwner
     * @return
     */
    @AuthRequired
    @PostMapping("/update")
    @Log(title = "修改", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody ApiActivityOwner apiActivityOwner) {
        Integer update = apiActivityService.update(apiActivityOwner);
        return update > 0 ? AjaxResult.success("签到成功") : AjaxResult.error("签到失败");
    }


    /**
     * 查询业主参与社区活动的状态：0-未参加   1-已参加
     * 传值：活动id 当前业主id
     * joinStatus =0  未参加
     *joinStatus =1  已参加
     *joinStatus =2  已签到（已参加）
     * signInStatus
     * @param apiActivityOwner
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/OwnerStatus")
    public AjaxResult getOwnerStatus( ApiActivityOwner apiActivityOwner) {
        AjaxResult ajaxResult = new AjaxResult();
        ApiActivityOwner activity = apiActivityService.getOwnerStatus(apiActivityOwner);
        //判断当前用户的信息
       if(activity!=null){
           if(activity.getSignUpTime()!=null)   {
               ajaxResult.put("joinStatus",2); //已签到
           }else{
               ajaxResult.put("joinStatus",1); //已参加
           }
       }else{
           ajaxResult.put("joinStatus",0); //未参加
       }
        return AjaxResult.success(ajaxResult);
    }





    /**
     * 判断当前活动的状态
     * 参数： activityId
     * 功能： 可否参与=ActivityJoinStatus   可否签到ActivitySignUpStatus
     *
     * ActivitySignUpStatus=0 可签到
     * ActivitySignUpStatus=1 不可签到
     *
     * ActivityJoinStatus=0  可参与
     * ActivityJoinStatus=1  不可参与
     * @param apiActivity
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/activityStatus")
    public AjaxResult activityStatus( ApiActivity apiActivity) throws ParseException {
        AjaxResult ajaxResult = new AjaxResult();
        ApiActivity status = apiActivityService.activityStatus(apiActivity);
        //检测是否可参与
        if(status!=null){
            if( status.getCloseTime().compareTo(new Date())>0){  //截止时间>当前时间  可报名
                ajaxResult.put("ActivityJoinStatus",0); //可参与
            }else{
                ajaxResult.put("ActivityJoinStatus",1); //不可参与
            }
        }

        //检测是否可签到
        if(status!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(status.getStartTime());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(format);
//            stringBuffer.append(" 00:00:00");
            //开始时间（时间装换为Date）
            Date startTime = sdf.parse(stringBuffer.toString());
            //当前时间
            Date nowDate = new Date();
            //结束时间
            Date endTime = status.getEndTime();
            //开始判断
            if (startTime.getTime() < nowDate.getTime() && nowDate.getTime() < endTime.getTime()) {  //  开始时间<当前时间<结束时间
                ajaxResult.put("ActivitySignUpStatus", 0); //活动可签到
            } else if (nowDate.getTime() > endTime.getTime()) {
                ajaxResult.put("ActivitySignUpStatus", 1); //活动不可签到
            } else if (nowDate.getTime() < startTime.getTime()) {
                ajaxResult.put("ActivitySignUpStatus", 2); //活动待签到
            }
        }
        return AjaxResult.success(ajaxResult);
    }


}
