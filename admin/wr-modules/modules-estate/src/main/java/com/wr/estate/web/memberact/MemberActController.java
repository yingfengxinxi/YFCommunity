package com.wr.estate.web.memberact;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.CommunityService;
import com.wr.estate.service.memberact.MemberActService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.activity.ActiviteManage;
import com.wr.remote.estate.member.MemberJoinVo;
import com.wr.remote.govern.party.MemberAct;
import com.wr.remote.govern.party.vo.MemberActVO;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author: SJiang
 * @Date: 2022-10-31 13:35:03
 * @Desc: 党员活动 控制层【物管平台】
 */
@RestController
@RequestMapping("memberAct")
public class MemberActController extends BaseController {

    @Autowired
    private MemberActService memberActService;

    @Autowired
    private CommunityService communityService;

    /**
     * 列表
     *
     * @param
     * @return
     */
//    @RequiresPermissions("estate:memberAct:list")
    @GetMapping("/list")
    public TableDataInfo list(MemberActVO memberAct) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        memberAct.setEstateId(estateId);
        startPage();
        return getDataTable(getMemberAct(memberActService.getMemberActAll(memberAct)));
    }

    private List<MemberActVO> getMemberAct(List<MemberActVO> list) {
        for (MemberActVO listVo : list) {
            Date StartTime = listVo.getStartTime();
            Date EndTime = listVo.getEndTime();
            Date date = new Date(System.currentTimeMillis());
            if (date.compareTo(StartTime) < 0) {
                listVo.setState("未开始");
            } else if (date.compareTo(EndTime) > 0) {
                listVo.setState("已结束");
            } else {
                listVo.setState("进行中");
            }
        }
        return list;
    }


    //    @RequiresPermissions("estate:memberAct:add")
    @Log(title = "党员活动", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody MemberAct memberAct) {
        // 获取小区名称
        String communityName = communityService.getCommunityName(memberAct.getCommunityId());
        if (UserConstants.NOT_UNIQUE.equals(memberActService.checkNameUnique(memberAct))) {
            return AjaxResult.error("添加活动主题'" + memberAct.getActTheme() + "'失败，该项目：" + communityName + "下已建立过该主题");
        }
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        memberAct.setCreateBy(userName);
        memberAct.setUpdateBy(userName);
        Integer num = memberActService.addMeberAct(memberAct);
        return toAjax(num);
    }


    //    @RequiresPermissions("estate:memberAct:update")
    @Log(title = "党员活动", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult update(@RequestBody MemberAct memberAct) {
        // 获取小区名称
        String communityName = communityService.getCommunityName(memberAct.getCommunityId());
        if (UserConstants.NOT_UNIQUE.equals(memberActService.checkNameUnique(memberAct))) {
            return AjaxResult.error("添加活动主题'" + memberAct.getActTheme() + "'失败，该项目：" + communityName + "下已建立过该主题");
        }
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        memberAct.setCreateBy(userName);
        memberAct.setUpdateBy(userName);
        Integer num = memberActService.updateMeberAct(memberAct);
        return toAjax(num);
    }

    /**
     * 删除
     *
     * @param activityIds
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
//    @RequiresPermissions("estate:memberAct:remove")
    @Log(title = "党员活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds) {
        Integer num = memberActService.delMeberAct(activityIds);
        return toAjax(num);
    }


    /**
     * 通过主键查询单条数据
     */
    @GetMapping(value = "/getInfoById/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId) {
        //获取楼栋
        ActiviteManage activiteManage = new ActiviteManage();
        activiteManage.setActivityId(activityId);
        //查询活动信息
        MemberActVO vo = memberActService.getByIdMemberActInfo(activityId);
        Date startTime = vo.getStartTime();
        Date endTime = vo.getEndTime();
        Date date = new Date(System.currentTimeMillis());
        if (date.compareTo(startTime) < 0) {
            vo.setState("未开始");
        } else if (date.compareTo(endTime) > 0) {
            vo.setState("已结束");
        } else {
            vo.setState("进行中");
        }
        return AjaxResult.success(vo);
    }

    /**
     * 参与人员列表
     *
     * @param
     * @return
     */
    @GetMapping("/joinPeopleList")
    public TableDataInfo selectJoinPeopleList(MemberJoinVo memberJoinVo) {
        startPage();
        return getDataTable(memberActService.selectJoinPeopleList(memberJoinVo));
    }
}
