package com.wr.govern.web.memberact;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.memberact.MemberActService;
import com.wr.govern.service.party.PartyService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.party.MemberAct;
import com.wr.remote.govern.party.vo.MemberActVO;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @Author: SJiang
 * @Date: 2022-10-31 13:35:03
 * @Desc: 党员活动 控制层
 */
@RestController
@RequestMapping("/memberAct")
public class MemberActController extends BaseController {
    @Autowired
    private MemberActService memberActService;
    @Autowired
    private PartyService partyService;

    @RequiresPermissions("govern:memberAct:list")
    @GetMapping("/list")
    public TableDataInfo list(MemberActVO memberAct) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if(ObjectUtils.isEmpty(sysUser)){
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        memberAct.setEstateId(estateId);
        startPage();
        List<MemberActVO> memberActAll = memberActService.getMemberActAll(memberAct);
        return getDataTable(memberActAll);
    }

    @RequiresPermissions("govern:memberAct:add")
    @Log(title = "党员活动", businessType = BusinessType.UPDATE)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody MemberAct memberAct) {
        // 获取小区名称
        String communityName = partyService.getCommunityName(memberAct.getCommunityId());
        if (UserConstants.NOT_UNIQUE.equals(memberActService.checkNameUnique(memberAct))) {
            return AjaxResult.error("添加活动主题'" + memberAct.getActTheme() + "'失败，该项目：" + communityName + "下已建立过该主题");
        }

        // 获取登录名称
        memberAct.setCreateBy(memberAct.getCreateBy());
        memberAct.setUpdateBy(memberAct.getCreateBy());
        Integer num = memberActService.addMeberAct(memberAct);
        return toAjax(num);
    }

    /**
     * 删除
     *
     * @param activityIds
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:memberAct:remove")
    @Log(title = "党员活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{activityIds}")
    public AjaxResult remove(@PathVariable Long [] activityIds) {
        Integer num = memberActService.delMeberAct(activityIds);
        return toAjax(num);
    }

    /**
     * 查询党员活动参与人员详情
     *
     * @param map
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "查询党员活动参与人员详情", businessType = BusinessType.OTHER)
    @PostMapping("/findListMemberPeople")
    public TableDataInfo findListMemberPeople(@RequestBody Map<String,Object> map) {
        if(CollUtil.isEmpty( map ) || MapUtil.getLong( map,"activityId" ) == null){
            throw new ServiceException("获取活动ID失败，请稍后重试！");
        }
        PageUtils.startPage(MapUtils.getInteger(map,"pageNum"),MapUtils.getInteger(map,"pageSize"));
        return getDataTable(memberActService.findListMemberPeople( map ));
    }
}
