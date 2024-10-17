package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.location.ExtractCode;
import com.wr.api.owner.entity.vo.serve.MemberActVo;
import com.wr.api.owner.service.serve.ApiMemberActService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 党员活动
 * @Author: lvzy
 * @Date: 2022-11-15 15:14:48
 * @Desc:党员活动
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/memberAct")
public class ApiOwnerMemberActController extends BaseController {

    private final ApiMemberActService apiMemberActService;

    @GetMapping("/listPage")
    @AuthRequired
    public TableDataInfo listPage(MemberActVo memberAct) {
        new ExtractCode().pageListAntiSql(memberAct.getPageNum(), memberAct.getPageSize());
        return getDataTable(apiMemberActService.listPage(memberAct.getCommunityId()));
    }

    @GetMapping("/detail")
    @AuthRequired
    public AjaxResult detail(MemberActVo memberAct) {
        return apiMemberActService.detail(memberAct);
    }

    @PostMapping("/signUp")
    @ApiLog(title = "新增党员活动", businessType = BusinessType.INSERT)
    @AuthRequired
    public AjaxResult signUp(@RequestBody MemberActVo memberAct) {
        if(memberAct == null || memberAct.getMemberId() == null || memberAct.getActivityId() == null){
            throw new ServiceException("获取相关数据参数失败，请稍后重试！");
        }

        return apiMemberActService.signUp(memberAct);
    }
}
